# -*- coding: UTF-8 -*-
import json
import random
import io
from urllib.request import urlopen
import math

import time

import requests
from PIL import Image
from bs4 import BeautifulSoup
import ssl

if hasattr(ssl, '_create_unverified_context'):
    ssl._create_default_https_context = ssl._create_unverified_context


def resize(w, h, w_box, h_box, pil_image):
    f1 = 1.0 * w_box / w  # 1.0 forces float division in Python2
    f2 = 1.0 * h_box / h
    factor = min([f1, f2])
    width = int(w * factor)
    height = int(h * factor)
    return pil_image.resize((width, height), Image.LANCZOS)


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
        print(index)
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
    pil_image = Image.open(data_stream)
    w, h = pil_image.size
    pil_image_resized = resize(w, h, w_box, h_box, pil_image)
    print(pil_image_resized)
