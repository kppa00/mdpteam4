import socket
import threading
import mysql.connector

HOST = ''
PORT = 5000

def query(query):
    conn = mysql.connector.connect(user='pi', password='aifactory', host='localhost', database='aifactory')
    cursor = conn.cursor()

    cursor.execute(query)
    rows = cursor.fetchall()

    cursor.close()
    conn.close()
    
    return rows

def login(msg, ip):
    print('login')
    msg = msg.split('/')
    id = list(query("SELECT * FROM account WHERE id = 'aifactory' AND password = 'aifactory!'"))

def register(msg, ip):
    print('register')
    msg = msg.split('/')

def send_data(message, target_ip, target_port):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((target_ip, target_port))
        s.sendall(message.encode())

def receive_data():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
        while True:
            conn, addr = s.accept()
            with conn:
                print('Connected by', addr)
                while True:
                    data = conn.recv(1024)
                    if not data:
                        break
                    msg = str(data.decode())
                    print('Received from', addr[0], ':', msg)
                    if "login" in msg:
                        login(msg, addr[0])
                    if "register" in msg:
                        register(msg, addr[0])

def main():
    receive_thread = threading.Thread(target=receive_data)
    receive_thread.start()

if __name__ == "__main__":
    main()
  
