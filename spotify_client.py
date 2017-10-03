import spotilib
import socket
import time

if __name__ == '__main__':
    s = socket.socket()  # Create a socket object
    host = "169.254.71.227"  # Get local machine name
    port = 5005  # Reserve a port for your service
    s.connect((host, port))
    last_artist = spotilib.artist()
    last_song = spotilib.song()
    while (True):
        current_song = spotilib.song()
        current_artist = spotilib.artist()

        if (last_song != current_song  or last_artist != current_artist):

            s.send( (current_artist + " "  + current_song).encode() )
            data = s.recv(1024)
            last_artist =current_artist
            last_song = current_song
            print("if")

        else:
            time.sleep(3)
