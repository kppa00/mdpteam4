import cv2
from picamera2 import Picamera2

def setup_camera(camera_id):
    cam = Picamera2(camera_id)
    cam.preview_configuration.main.size = (200, 300)
    cam.preview_configuration.main.format = "RGB888"
    cam.preview_configuration.controls.FrameRate = 5
    cam.configure("preview")
    cam.start()
    return cam

cams = [setup_camera(0), setup_camera(1)]

try:
    while True:
        for i, cam in enumerate(cams):
            image = cam.capture_array()
            cv2.imshow(f"cam{i}", image)

        if cv2.waitKey(1) == ord('q'):
            break
finally:
    for cam in cams:
        cam.stop()
    cv2.destroyAllWindows()
