import cv2
import os

cap = cv2.VideoCapture(0)

save_dir = "captured_images"
os.makedirs(save_dir, exist_ok=True)
frame_count = 0

cap.set(cv2.CAP_PROP_FRAME_WIDTH, 320)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 320)
if not cap.isOpened():
    print(f"can't open camera")
    exit()
else:
    print(f"opened camera")

while True:
        
    ret, frame = cap.read()

    if not ret:
        print(f"can't read frame camera")
        break

    cv2.imshow(f'camera', frame)
    
    key = cv2.waitKey(1)
    if key & 0xFF == ord('q'):
        break
    elif key & 0xFF == ord(' '):
        img_name = f"frame_{frame_count}.png"
        save_path = os.path.join(save_dir, img_name)
        cv2.imwrite(save_path, frame)
        print(f"Saved : frame_{frame_count}.png")
        frame_count += 1

cap.release()
cv2.destroyAllWindows()