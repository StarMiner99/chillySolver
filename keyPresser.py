from time import sleep

from pynput.keyboard import Key, Controller, Listener

keyList = ["LEFT", "DOWN", "RIGHT", "DOWN", "LEFT", "RIGHT", "DOWN", "LEFT", "DOWN", "RIGHT", "DOWN", "RIGHT", "DOWN",
           "LEFT", "DOWN", "RIGHT", "DOWN", "RIGHT", "UP", "RIGHT", "DOWN", "RIGHT", "DOWN", "LEFT", "DOWN", "RIGHT",
           "DOWN", "LEFT", "DOWN", "RIGHT", "UP", "DOWN", "RIGHT", "UP", "DOWN", "RIGHT", "UP", "LEFT", "DOWN", "RIGHT",
           "DOWN", "RIGHT", "UP", "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "DOWN",
           "LEFT", "RIGHT", "UP", "RIGHT", "LEFT", "DOWN", "LEFT", "DOWN", "LEFT", "DOWN", "LEFT", "DOWN", "RIGHT",
           "DOWN", "LEFT", "DOWN", "LEFT", "DOWN", "LEFT", "UP", "RIGHT", "DOWN", "RIGHT", "UP", "RIGHT", "DOWN",
           "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "UP", "RIGHT", "UP", "LEFT", "DOWN", "RIGHT", "DOWN", "RIGHT",
           "LEFT", "DOWN", "RIGHT", "LEFT", "RIGHT", "DOWN", "UP", "DOWN", "UP", "RIGHT", "LEFT", "RIGHT", "DOWN",
           "LEFT", "DOWN", "LEFT", "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "UP", "RIGHT", "UP",
           "LEFT", "RIGHT", "DOWN", "RIGHT", "UP", "RIGHT", "UP", "LEFT", "DOWN", "LEFT", "UP", "LEFT", "DOWN", "LEFT",
           "DOWN", "LEFT", "RIGHT", "UP", "LEFT", "UP", "LEFT", "UP", "DOWN", "RIGHT", "DOWN", "LEFT", "DOWN", "LEFT",
           "DOWN", "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "DOWN", "RIGHT", "UP", "LEFT", "DOWN", "LEFT", "UP",
           "LEFT", "UP", "LEFT", "DOWN"]

keyboard = Controller()



print("starting in 5 seconds")

sleep(5)

for nextKey in keyList:
    sleep(0.2)

    if nextKey == "RIGHT":
        print("r")
        keyboard.press(Key.right)
        sleep(0.2)
        keyboard.release(Key.right)
    elif nextKey == "DOWN":
        print("d")
        keyboard.press(Key.down)
        sleep(0.2)
        keyboard.release(Key.down)
    elif nextKey == "LEFT":
        print("l")
        keyboard.press(Key.left)
        sleep(0.2)
        keyboard.release(Key.left)
    elif nextKey == "UP":
        print("u")
        keyboard.press(Key.up)
        sleep(0.2)
        keyboard.release(Key.up)


    sleep(5)
