import threading
import AF_database
import AF_wifi
import AF_serial
import AF_camera

isRunning = False

def login(msg, ip):
    sent_data = msg.split('/')
    user_info = AF_database.query(f"SELECT * FROM account WHERE id = '{sent_data[1]}' AND password = '{sent_data[2]}'")
    if len(user_info) == 1:
        print("Login Successful\n")
        AF_wifi.send_data(f"login/yes/{user_info[0][3]}", ip)
    else:
        print("Login failed\n")
        AF_wifi.send_data("login/no", ip)

def register(msg, ip):
    sent_data = msg.split('/')
    user_info = AF_database.query(f"SELECT * FROM account WHERE id = '{sent_data[1]}'")

    idSame = False
    if len(user_info) != 0:
        for data in user_info:
            if sent_data[1] == data[1]:
                idSame = True
    if idSame:
        print('Register failed\n')
        AF_wifi.send_data('register/no', ip)
    else:
        print('Register successful\n')
        AF_database.query(f"INSERT INTO account VALUE (NULL, '{sent_data[1]}', '{sent_data[2]}', '{sent_data[3]}', '{sent_data[4]}')")
        AF_wifi.send_data('register/yes', ip)

def control(msg, ip):
    global isRunning
    sent_data = msg.split('/')
    if sent_data[1] == "switching" or "isRunning":
        if sent_data[1] == "isRunning":
            if isRunning:
                AF_wifi.send_data("control/yes", ip)
            else:
                AF_wifi.send_data("control/no", ip)
        if sent_data[1] == "switching":
            isRunning = not isRunning
            if isRunning:
                AF_serial.send_data("belton")
                print("belton")
            else:
                AF_serial.send_data("beltof")
        print("Now running is :", isRunning, "\n")

def bottle(msg, ip):
    sent_data = msg.split('/')
    if sent_data[1] == "getCount":
        bottle_count = AF_database.query("SELECT * FROM bottle")
        print("good / defect :", str(bottle_count[0][0]) + " / " + str(bottle_count[0][1]) + "\n")
        AF_wifi.send_data(str(bottle_count[0][0]) + "/" + str(bottle_count[0][1]), ip)

def belt():
    isRunning = not isRunning
    AF_wifi.send_data("control/belt")

def main():
    receive_thread = threading.Thread(target=AF_wifi.receive_data)
    receive_thread.start()

    camera_thread = threading.Thread(target=AF_camera.start_camera)
    camera_thread.start()

if __name__ == "__main__":
    main()
