# -*- coding: UTF-8 -*-
import json
import random
import io
from urllib.request import urlopen
import math
import os
import time

import requests
import cv2
from bs4 import BeautifulSoup
import ssl
import tensorflow as tf
import numpy as np

if hasattr(ssl, '_create_unverified_context'):
    ssl._create_default_https_context = ssl._create_unverified_context

iterator = 6000

# 数据获取
classification = os.listdir("resources")
for i in range(classification.__len__()):
    if classification[i][0] == ".":
        classification.pop(i)
        break
classification_len = classification.__len__()


def get_next_batch():
    index = random.randint(0, classification_len - 1)
    pictures = os.listdir("resources/" + classification[index])
    picture_path = pictures[random.randint(0, pictures.__len__() - 1)]

    input_x = cv2.imread("resources/" + classification[index] + "/" + picture_path)
    input_x = cv2.resize(input_x, (227, 227))
    input_x = input_x.astype(np.float32)
    input_x = np.reshape(input_x, [1, 227, 227, 3])
    input_x = input_x / 255
    y = get_realY(index)
    return [input_x, y]


def get_batch(batch_size):
    result_x = []
    result_y = []
    for i in range(batch_size):
        X, Y = get_next_batch()
        result_x.append(X)
        result_y.append(Y)
    result_x = np.reshape(result_x, [batch_size, 227, 227, 3])
    return [result_x, result_y]


def resize(w, h, w_box, h_box, pil_image):
    f1 = 1.0 * w_box / w
    f2 = 1.0 * h_box / h
    factor = min([f1, f2])
    width = int(w * factor)
    height = int(h * factor)
    return pil_image.resize((width, height))


def get_realY(index):
    result = []
    for i in range(classification_len):
        if i == index:
            result.append(1)
        else:
            result.append(0)
    return result


def conv2d(x, w, stride):
    return tf.nn.conv2d(x, w, strides=[1, stride, stride, 1], padding="SAME")


# def conv(x, kernel_height, num_kernels, stride, padding='SAME'):
#     input_channels = int(np.shape(x)[-1])
#     weights_initial = tf.truncated_normal(
#         shape=[kernel_height, kernel_height, input_channels, num_kernels], stddev=0.1)
#     weights = tf.Variable(weights_initial)
#     biases_initial = tf.constant(0.1, shape=[num_kernels])
#     biases = tf.Variable(biases_initial)
#
#     convolve = lambda i, k: tf.nn.conv2d(i, k, strides=[1, stride, stride, 1], padding=padding)
#     conv = convolve(x, weights)
#
#     # add biases and active function
#     withBias = tf.add(conv, biases)
#     relu = tf.nn.relu(withBias)
#     return relu


def maxPooling(input, filter_size, stride, padding='SAME'):
    return tf.nn.max_pool(input, ksize=[1, filter_size, filter_size, 1], strides=[1, stride, stride, 1],
                          padding=padding)


def lrn(input, radius, alpha, beta, bias=1.0):
    return tf.nn.local_response_normalization(input, depth_radius=radius, alpha=alpha, beta=beta, bias=bias)


def fc(input, num_in, num_out, drop_ratio=0, relu=True):
    weights = weight_variable([num_in, num_out])
    biases = bias_variable([num_out])
    # Linear
    act = tf.matmul(input, weights) + biases

    if relu == True:
        relu = tf.nn.relu(act)
        if drop_ratio == 0:
            return relu
        else:
            return tf.nn.dropout(relu, 1.0 - drop_ratio)
    else:
        if drop_ratio == 0:
            return act
        else:
            return tf.nn.dropout(act, 1.0 - drop_ratio)


def weight_variable(shape):
    initial = tf.truncated_normal(shape, stddev=0.1)
    return tf.Variable(initial)


def bias_variable(shape):
    initial = tf.constant(0.1, shape=shape)
    return tf.Variable(initial)


def save_models():
    saver = tf.train.Saver()
    saver.save(sess, "CNN_classification.ckpt")


def load_models():
    saver = tf.train.Saver()
    saver.restore(sess, "CNN_classification.ckpt")


input_x = tf.placeholder(tf.float32, [None, 227 * 227 * 3])
input_x = tf.reshape(input_x, [-1, 227, 227, 3])
real_y = tf.placeholder(tf.float32, [None, classification_len])
keep_prob = tf.placeholder(tf.float32)
# layer 1
w_conv1 = weight_variable([11, 11, 3, 96])
b_conv1 = bias_variable([96])
h_conv1 = tf.nn.relu(conv2d(input_x, w_conv1, 4) + b_conv1)
pool1 = maxPooling(h_conv1, filter_size=3, stride=2, padding='VALID')
norm1 = lrn(pool1, 2, 2e-05, 0.75)
# layer 2
w_conv2 = weight_variable([5, 5, 96, 256])
b_conv2 = bias_variable([256])
h_conv2 = tf.nn.relu(conv2d(norm1, w_conv2, 1) + b_conv2)
pool2 = maxPooling(h_conv2, filter_size=3, stride=2, padding='VALID')
norm2 = lrn(pool2, 2, 2e-05, 0.75)
# layer 3
w_conv3 = weight_variable([3, 3, 256, 384])
b_conv3 = bias_variable([384])
h_conv3 = tf.nn.relu(conv2d(norm2, w_conv3, 1) + b_conv3)
# layer 4
w_conv4 = weight_variable([3, 3, 384, 384])
b_conv4 = bias_variable([384])
h_conv4 = tf.nn.relu(conv2d(h_conv3, w_conv4, 1) + b_conv4)
# layer 5
w_conv5 = weight_variable([3, 3, 384, 256])
b_conv5 = bias_variable([256])
h_conv5 = tf.nn.relu(conv2d(h_conv4, w_conv5, 1) + b_conv5)
pool5 = maxPooling(h_conv5, filter_size=3, stride=2, padding='VALID')
# layer 6
flattened = tf.reshape(pool5, [-1, 6 * 6 * 256])

fc6 = fc(input=flattened, num_in=6 * 6 * 256, num_out=4096, drop_ratio=1.0 - keep_prob,
         relu=True)
# layer 7
fc7 = fc(input=fc6, num_in=4096, num_out=4096, drop_ratio=1.0 - keep_prob, relu=True)
# layer 8
fc8 = fc(input=fc7, num_in=4096, num_out=classification_len, drop_ratio=0, relu=False)

cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits_v2(labels=real_y, logits=fc8))
optimizer = tf.train.AdamOptimizer(learning_rate=0.0005).minimize(cost)

correct_pred = tf.equal(tf.argmax(fc8, 1), tf.argmax(real_y, 1))
accuracy = tf.reduce_mean(tf.cast(correct_pred, tf.float32))
with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    accuracy_time = 0
    for step in range(iterator):
        next_batch = get_batch(100)
        sess.run(optimizer, feed_dict={input_x: next_batch[0], real_y: next_batch[1], keep_prob: 0.5})
        if step % 10 == 0:
            correct = sess.run(accuracy, feed_dict={input_x: next_batch[0], real_y: next_batch[1], keep_prob: 0.5})
            print(sess.run(cost, feed_dict={input_x: next_batch[0], real_y: next_batch[1], keep_prob: 0.5}))
            print(correct)
            if correct == 1:
                accuracy_time += 1
            else:
                accuracy_time = 0
            if accuracy_time >= 10:
                save_models()
