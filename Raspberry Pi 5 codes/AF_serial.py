import serial
import AF_main

ser = serial.Serial(
    port="/dev/ttyAMA0",
    baudrate=9600
)

def send_data(data: str):
    ser.write(data.encode())

def read_serial_data():
    while True:
        received_data = ser.readline().decode().strip()
        if received_data == "belt":
            AF_main.belt()