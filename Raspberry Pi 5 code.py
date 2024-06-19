import socket
import threading
import mysql.connector

HOST = ''
PORT = 5000

def query(query):
    conn = mysql.connector.connect(user='root', password='root', host='localhost', database='aifactory')
    cursor = conn.cursor()

    cursor.execute(query)
    rows = cursor.fetchall()
    conn.commit()

    cursor.close()
    conn.close()
    
    return rows

def login(msg, ip):
    sent_data = msg.split('/')
    user_info = query(f"SELECT * FROM account WHERE id = '{sent_data[1]}' AND password = '{sent_data[2]}'")
    if len(user_info) == 1:
        send_data("login/yes", ip, PORT)
    else:
        send_data("login/no", ip, PORT)

def register(msg, ip):
    sent_data = msg.split('/')
    user_info = query(f"SELECT * FROM account WHERE id = '{sent_data[1]}'")

    idSame = False
    if len(user_info) != 0:
        for data in user_info:
            if sent_data[1] == data[1]:
                idSame = True
    if idSame:
        send_data('register/no', ip, PORT)
    else:
        query(f"INSERT INTO account VALUE (NULL, '{sent_data[1]}', '{sent_data[2]}', '{sent_data[3]}', '{sent_data[4]}')")
        send_data('register/yes', ip, PORT)

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
