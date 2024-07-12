import cv2
from picamera2 import Picamera2
import os

def setup_camera(camera_id):
    cam = Picamera2(camera_id)
    cam.preview_configuration.main.size = (640, 480)
    cam.preview_configuration.main.format = "RGB888"
    cam.preview_configuration.controls.FrameRate = 5
    cam.configure("preview")
    cam.start()
    return cam

cams = [setup_camera(0)]

save_dir = "captured_images"
os.makedirs(save_dir, exist_ok=True)
frame_count = 0

try:
    while True:
        for i, cam in enumerate(cams):
            image = cam.capture_array()
            save_path = os.path.join(save_dir, f"front_frame{frame_count}.jpg")
            cv2.imwrite(save_path, image)
            print(f"front_frame{frame_count}.jpg")
            cv2.imshow(f"cam{i}", image)

        frame_count += 1

        if cv2.waitKey(1) == ord('q'):
            break
finally:
    for cam in cams:
        cam.stop()
    cv2.destroyAllWindows()