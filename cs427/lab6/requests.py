import urequests as requests
import ujson
import network
import gc
from time import sleep_ms

import random


gc.collect()

wlan = network.WLAN(network.STA_IF)
wlan.active(True)
wlan.connect("ssid", "password")

while not wlan.isconnected():
    sleep_ms(100)

print('Network config:', wlan.ifconfig())


url = "http://172.20.10.12:1313"

for i in range(10):
    sleep_ms(4000)  # 4 seconds

    x = random.randint(0, 100)
    y = random.randint(0, 100)

    post_data = ujson.dumps({"x": x, "y": y})

    res = requests.post(url, headers={'content-type': 'application/json'}, data=post_data)
    print(res.status_code)
