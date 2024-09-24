import cv2
from ultralytics import YOLO

model = YOLO('best.pt')

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

            results = model(frame)

            for result in results:
                boxes = result.boxes
                for box in boxes:
                    x1, y1, x2, y2 = map(int, box.xyxy[0])

                    cv2.rectangle(frame, (x1, y1), (x2, y2), (255, 0, 0), 2)

                    label = f"{model.names[int(box.cls[0])]} {box.conf[0]:.2f}"
                    cv2.putText(frame, label, (x1, y1 - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)

            cv2.imshow(f'camera{cap_num}', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    for cap in caps:
        cap.release()
    cv2.destroyAllWindows()

start_camera()
