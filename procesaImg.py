
import urllib.request
import requests
import time
from detectModel import *

def downloader(image_url):
    full_file_name = 'raw.jpg'
    urllib.request.urlretrieve(image_url,full_file_name)

url = "http://planz.omiustech.com/test_upload.php"
r = requests.get(url)
i = r.text
print(str(i))

while True:
    r = requests.get(url)
    if (i != (r.text)):
        print("iniciando...")
        downloader("http://planz.omiustech.com/raw/img1.jpg")
        i = (r.text)
        print("Se descargo imagen")

        procesaImagen()
        time.sleep(1)
        
        files = {'file': open('procesada.jpg', 'rb')}
        r = requests.post(url, files=files)
        print (i, r.text)
    time.sleep(1)
