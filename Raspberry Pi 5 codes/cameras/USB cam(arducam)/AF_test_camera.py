import cv2

def is_opened_camera():
    for i in range(0, 11):
        cap = cv2.VideoCapture(i)

        if not cap.isOpened():
            print(f"can't open camera{i}")
        else:
            print(f"opened camera {i}")

def start_camera():
    caps = []
    cap_count = 0

    for i in range(0, 11):
        cap = cv2.VideoCapture(i)
        if cap.isOpened():
            cap_count += 1
            caps.append(cap)
            print(f"opened camera {i}")
        if cap_count == 2:
            break

    for cap_num, cap in enumerate(caps):
        cap.set(cv2.CAP_PROP_FRAME_WIDTH, 320)
        cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 320)

    while True:
        for cap_num, cap in enumerate(caps):
            ret, frame = cap.read()
            if not ret:
                print(f"can't read frame camera{cap_num}")
                break
            cv2.imshow(f'camera{cap_num}', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()

start_camera()