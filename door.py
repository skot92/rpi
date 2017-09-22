import RPi.GPIO as GPIO
import time
import socket

sensor = 21
led = 16

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(sensor, GPIO.IN, GPIO.PUD_DOWN)
previous_state = False
current_state = False
new_state = False

GPIO.setup(led,GPIO.OUT)



while True:
    time.sleep(1)
    previous_state = current_state
    current_state = GPIO.input(sensor)
    if current_state != previous_state:
        new_state = "Kinyilt az ajto" if current_state else "LOW"

        if (new_state == "Kinyilt az ajto"):
            print("Kinyilt az ajto asdasdas")
            GPIO.output(led, GPIO.HIGH)
        else:
            print("Low")
            GPIO.output(led, GPIO.LOW)
