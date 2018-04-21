# -*- coding: UTF-8 -*-
import json
import random
import io
from urllib.request import urlopen
import math

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
num_classes = 8

# 数据获取
classification = ["cbzw", "mbzw", "tbzw", "qgzw", "sgzw", "lkzw", "sszw", "drzw"]
page_num = []
user_agents = ['Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1',
               'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50',
               'Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11']
classification_len = classification.__len__()

url_has_more = []
url_page_index = []
url_now_index = []

for i in range(classification_len):
    page_num.append(0)
    url_has_more.append(True)
    url_page_index.append(1)
    url_now_index.append(0)


def get_next_batch():
    index = random.randint(0, classification_len - 1)
    agents_index = random.randint(0, user_agents.__len__() - 1)
    headers = {
        'User-Agent': user_agents[agents_index]
    }
    searchPage = random.randint(1, 100)
    url = "https://tuku.huabaike.com/default.php/Pics/ajaxupload/" + str(math.floor(time.time())) + "?page=" + str(
        searchPage) + "&url=" + str(classification[index])
    res = requests.get(url=url, headers=headers)
    content = json.loads(res.content.decode('utf-8'))
    length = content['length']
    if length != 0:
        index_num = random.randint(0, length - 1)
        soupAll = BeautifulSoup(content['htmlstr'], "html.parser")
        imageUrl = soupAll.find_all('a')[index_num]['href']

        image_bytes = urlopen(imageUrl).read()
        data_stream = io.BytesIO(image_bytes)
        with open("temp.jpg", "wb") as file:
            file.write(data_stream.read())
        input_x = cv2.imread("temp.jpg")
        input_x = cv2.resize(input_x, (227, 227))
        input_x = input_x.astype(np.float32)
        input_x = np.reshape(input_x, [1, 227, 227, 3])
        input_x = input_x / 255
        y = get_realY([index], 1)
        return [input_x, y]
    else:
        return get_next_batch()


def get_batch(batch_size):
    result_x = []
    result_y = []
    for i in range(batch_size):
        X, Y = get_next_batch()
        result_x.append(X)
        result_y.append(Y)
    return [result_x, result_y]


def resize(w, h, w_box, h_box, pil_image):
    f1 = 1.0 * w_box / w
    f2 = 1.0 * h_box / h
    factor = min([f1, f2])
    width = int(w * factor)
    height = int(h * factor)
    return pil_image.resize((width, height))


def get_realY(index, batch_size):
    result = []
    for j in range(batch_size):
        result.append([])
        for i in range(classification_len):
            if i == index[j]:
                result[j].append(1)
            else:
                result[j].append(0)
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
real_y = tf.placeholder(tf.float32, [None, 8])
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
fc8 = fc(input=fc7, num_in=4096, num_out=num_classes, drop_ratio=0, relu=False)

cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits_v2(labels=real_y, logits=fc8))
optimizer = tf.train.AdamOptimizer(learning_rate=0.001).minimize(cost)

correct_pred = tf.equal(tf.argmax(fc8, 1), tf.argmax(real_y, 1))
accuracy = tf.reduce_mean(tf.cast(correct_pred, tf.float32))
with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    accuracy_time = 0
    for step in range(iterator):
        next_batch = get_batch(5)
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
