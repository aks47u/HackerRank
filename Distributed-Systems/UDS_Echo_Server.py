import socket
from threading import Thread

def write_string_to_socket(connection, message):
    connection.send(message)
    
def read_string_from_socket(connection):
    while True:
        data = connection.recv(1024)
        if data: break
    return data

def process_client_connection(connection):
    while True:
        message = read_string_from_socket(connection)
        print "Message received = ", message
        sys.stdout.flush()
        write_string_to_socket(connection, message)
        if message == "END":
            break
