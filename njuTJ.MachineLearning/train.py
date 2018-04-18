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

# 数据获取
classification = ["cbzw", "mbzw", "tbzw", "qgzw", "sgzw", "lkzw", "sszw", "drzw"]
user_agents = ['Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1',
               'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50',
               'Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11']
classification_len = classification.__len__()

url_has_more = []
url_page_index = []
url_now_index = []

for i in range(classification_len):
    url_has_more.append(True)
    url_page_index.append(1)
    url_now_index.append(0)


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


class AlexNet(object):
    def __init__(self, input_x, keep_prob, num_classes, weights_path='alexnet.npy'):
        # Initialization the parameters
        self.input_x = input_x
        self.keep_prob = keep_prob
        self.weights_path = weights_path
        self.num_classes = num_classes
        # Create the AlexNet Network Define
        self.create()

    def conv(self, x, kernel_height, num_kernels, stride, padding='SAME', padding_num=0, groups=1):
        input_channels = int(np.shape(x)[-1])
        if not padding_num == 0:
            x = tf.pad(x, [[0, 0], [padding_num, padding_num], [padding_num, padding_num], [0, 0]])
        convolve = lambda i, k: tf.nn.conv2d(i, k, strides=[1, stride, stride, 1], padding=padding)
        weights = tf.get_variable('weights',
                                  shape=[kernel_height, kernel_height, input_channels / groups, num_kernels])
        biases = tf.get_variable('biases', shape=[num_kernels])
        if groups == 1:
            conv = convolve(x, weights)
        else:
            input_groups = tf.split(axis=3, num_or_size_splits=groups, value=x)
            weights_groups = tf.split(axis=3, num_or_size_splits=groups, value=weights)
            output_groups = [convolve(i, k) for i, k in zip(input_groups, weights_groups)]

            conv = tf.concat(axis=3, values=output_groups)

        # add biases and avtive function
        withBias = tf.reshape(tf.nn.bias_add(conv, biases), conv.get_shape().as_list())
        relu = tf.nn.relu(withBias)
        return relu

    def maxPooling(self, input, filter_size, stride, padding='SAME'):
        return tf.nn.max_pool(input, ksize=[1, filter_size, filter_size, 1], strides=[1, stride, stride, 1],
                              padding=padding)

    def lrn(self, input, radius, alpha, beta, bias=1.0):
        return tf.nn.local_response_normalization(input, depth_radius=radius, alpha=alpha, beta=beta, bias=bias)

    def fc(self, input, num_in, num_out, drop_ratio=0, relu=True):
        weights = tf.get_variable('weights', shape=[num_in, num_out], trainable=True)
        biases = tf.get_variable('biases', [num_out], trainable=True)
        # Linear
        act = tf.nn.xw_plus_b(input, weights, biases)

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

    def create(self):
        # layer 1
        conv1 = self.conv(self.input_x, 11, 96, 4, padding='SAME')
        pool1 = self.maxPooling(conv1, filter_size=3, stride=2, padding='SAME')
        norm1 = self.lrn(pool1, 2, 2e-05, 0.75, name='norm1')
        # layer 2
        conv2 = self.conv(norm1, 5, 256, 1, padding_num=0, groups=2)
        pool2 = self.maxPooling(conv2, filter_size=3, stride=2, padding='VALID')
        norm2 = self.lrn(pool2, 2, 2e-05, 0.75, name='norm2')
        # layer 3
        conv3 = self.conv(norm2, 3, 384, 1)
        # layer 4
        conv4 = self.conv(conv3, 3, 384, 1, groups=2)
        # layer 5
        conv5 = self.conv(conv4, 3, 256, 1, groups=2)
        pool5 = self.maxPooling(conv5, filter_size=3, stride=2, padding='VALID')
        # layer 6
        flattened = tf.reshape(pool5, [-1, 6 * 6 * 256])
        fc6 = self.fc(input=flattened, num_in=6 * 6 * 256, num_out=4096, drop_ratio=1.0 - self.keep_prob,
                      relu=True)
        # layer 7
        fc7 = self.fc(input=fc6, num_in=4096, num_out=4096, drop_ratio=1.0 - self.keep_prob, relu=True)
        # layer 8
        self.fc8 = self.fc(input=fc7, num_in=4096, num_out=self.num_classes, drop_ratio=0, relu=False)


while True:
    index = random.randint(0, classification_len - 1)
    agents_index = random.randint(0, user_agents.__len__() - 1)
    headers = {
        'User-Agent': user_agents[agents_index]
    }
    if not url_has_more[index]:
        isNotStopTime = False
        for i in range(classification_len):
            isNotStopTime = isNotStopTime | url_has_more[i]
        if not isNotStopTime:
            break
        continue
    url = "https://tuku.huabaike.com/default.php/Pics/ajaxupload/" + str(math.floor(time.time())) + "?page=" + str(
        url_page_index[index]) + "&url=" + str(classification[index])
    res = requests.get(url=url, headers=headers)
    content = json.loads(res.content.decode('utf-8'))
    length = content['length']
    if length == 0:
        url_has_more[index] = False
        continue
    soupAll = BeautifulSoup(content['htmlstr'], "html.parser")
    imageUrl = soupAll.find_all('a')[url_now_index[index]]['href']
    url_now_index[index] += 1
    if length == url_now_index[index]:
        url_page_index[index] += 1
        url_now_index[index] = 0

    w_box = 224
    h_box = 224
    image_bytes = urlopen(imageUrl).read()
    data_stream = io.BytesIO(image_bytes)
    x = cv2.imread(data_stream)
    x = cv2.resize(x, (227, 227))
    x = x.astype(np.float32)
    x = np.reshape(x, [1, 227, 227, 3])
    pred_y = tf.placeholder(tf.float32, [None, 8])
    y = get_realY(index)
    model = AlexNet(input, 0.5, 1000)
    score = model.fc8
    max = tf.arg_max(score, 1)
    with tf.Session() as sess:
        sess.run(tf.global_variables_initializer())
        model.load_weights(sess)
        label_id = sess.run(max)[0]
