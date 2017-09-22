import RPi.GPIO as GPIO
from time import sleep
import socket


from datetime import datetime

import socketserver

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(19, GPIO.OUT)




class Handler_TCPServer(socketserver.BaseRequestHandler):
    """
    The TCP Server class for demonstration.
 
    Note: We need to implement the Handle method to exchange data
    with TCP client.
 
    """
 
    def handle(self):
        # self.request - TCP socket connected to the client

        #self.request.send("ACK from TCP Server".encode())

        if  GPIO.input(19) == GPIO.HIGH:
            self.request.send("on".encode())
        elif GPIO.input(19) == GPIO.LOW:
            self.request.send("off".encode())


        self.data = self.request.recv(1024).strip()
        print("{} sent:".format(self.client_address[0]))
        print(self.data.decode('utf-8'))

  
        if (self.data.decode('utf-8') == "on"):
            GPIO.output(19, GPIO.LOW)
            print ("lamp on")
        elif (self.data.decode('utf-8') == "off"):
            GPIO.output(19, GPIO.HIGH)
            print ("lamp off")

        # just send back ACK for data arrival confirmation
        #self.request.sendall("ACK from TCP Server".encode())
 
if __name__ == "__main__":
    HOST, PORT = "192.168.1.3", 9999
 
    # Init the TCP server object, bind it to the localhost on 9999 port
    tcp_server = socketserver.TCPServer((HOST, PORT), Handler_TCPServer)
         
 
    # Activate the TCP server.
    # To abort the TCP server, press Ctrl-C.
    tcp_server.serve_forever()



  