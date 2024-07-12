import socket
from datetime import datetime
import AF_main

HOST = ''
PORT = 5000

def classify(msg, ip):
    actions = {
        "login": AF_main.login,
        "register": AF_main.register,
        "control": AF_main.control,
        "bottle": AF_main.bottle
    }
    
    for key in actions:
        if key in msg:
            actions[key](msg, ip)
            break

def send_data(message, target_ip):
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((target_ip, PORT))
            s.sendall(message.encode())
    except:
        print("Data send failed")

def receive_data():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
        while True:
            try:
                conn, addr = s.accept()
                with conn:
                    print('Now time :', datetime.now().strftime('%Y.%m.%d - %H:%M:%S'))
                    print('Connected by :', addr)
                    while True:
                        data = conn.recv(1024)
                        if not data:
                            break
                        msg = str(data.decode())
                        print('Received from :', msg)
                        classify(msg, addr[0])
            except:
                print("Data reception failed")