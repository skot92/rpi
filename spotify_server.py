import time
import argparse
import datetime
import socket

from luma.led_matrix.device import max7219
from luma.core.interface.serial import spi, noop
from luma.core.render import canvas
from luma.core.legacy import text
from luma.core.legacy.font import proportional,  CP437_FONT, LCD_FONT
from luma.core.virtual import viewport, sevensegment



TCP_IP = '169.254.71.227'
TCP_PORT = 5005
BUFFER_SIZE = 1024 
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((TCP_IP, TCP_PORT))
s.listen(1)
conn, addr = s.accept()
s.settimeout(1)
conn.settimeout(1)

serial = spi(port=0, device=0, gpio=noop())
device = max7219(serial, width=8, height=8, rotate=0, block_orientation=-90, cascaded=4)
seg = sevensegment(device)
seg.device.contrast(0)
virtual = viewport(device, width=750, height=100)


def demo():
    stringdata = ""

    while(True):
        try:
            data = conn.recv(BUFFER_SIZE)
            conn.send(data)  # echo
            stringdata = data.decode('utf-8',"ignore")
            
        except socket.timeout:
            with canvas(virtual) as draw:
                draw.text((0, 0), "         " + stringdata, fill="white")
            count = len("         " + stringdata)*8
            for offset in range(count):
                #print(offset)
                virtual.set_position((offset, 2))
                time.sleep(0.05)
        

if __name__ == "__main__":
    try:
        while(True):
            demo()
    except KeyboardInterrupt:
        pass
