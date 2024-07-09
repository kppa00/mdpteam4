import serial
import time

def serial_receive_thread():
    
    # create serial port
    ser = serial.Serial(
        port="/dev/ttyAMA0",
        baudrate=9600
    )

    # check for serial port is opened
    if ser.isOpen():
        print("Serial port is opened")
    else:
        print("Failed to open serial port")

    try:
        while True:
            # send data
            ser.write(b'Hello, UART!\n')
            print("Sent: Hello, UART!")

            # receive data
            if ser.in_waiting > 0:
                data = ser.readline().decode().strip()
                print(f"Received: {data}")

            time.sleep(1)

    except KeyboardInterrupt:
        print("Keyboard interrupt received, exiting...")

    finally:
        # close serial port
        ser.close()
        print("Serial port closed")
