import cv2
import numpy as np
from picamera2 import Picamera2, Preview
from threading import Thread
from ultralytics import YOLO

# YOLOv8 모델 로드
# model = YOLO('yolov10n.pt')

def setup_camera(camera_id):
    cam = Picamera2(camera_id)
    cam.preview_configuration.main.size = (640, 480)
    cam.preview_configuration.main.format = "RGB888"
    cam.preview_configuration.controls.FrameRate = 10
    cam.configure("preview")
    cam.start()
    return cam

# 두 개의 카메라 초기화
cams = [setup_camera(0), setup_camera(1)]

def capture_and_detect():
    try:
        while True:
            for i, cam in enumerate(cams):
                image = cam.capture_array()

                # YOLOv8로 객체 감지
                # results = model(image)

                # # 결과 그리기
                # for result in results:
                #     boxes = result.boxes
                #     for box in boxes:
                #         x1, y1, x2, y2 = map(int, box.xyxy)
                #         label = box.cls
                #         confidence = box.conf

                #         # 박스 그리기
                #         cv2.rectangle(image, (x1, y1), (x2, y2), (255, 0, 0), 2)
                #         cv2.putText(image, f'{label} {confidence:.2f}', (x1, y1 - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 2)

                # 출력 이미지 보여주기
                cv2.imshow(f"cam{i}", image)

            if cv2.waitKey(1) == ord('q'):
                break
    finally:
        for cam in cams:
            cam.stop()
        cv2.destroyAllWindows()

capture_and_detect()