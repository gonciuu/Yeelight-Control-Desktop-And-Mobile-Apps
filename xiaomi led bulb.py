from yeelight import Bulb
import time
from tkinter import Tk, Label, Button

bulb = Bulb("192.168.0.108")
bulb.turn_on()
bulb.set_rgb(255, 255, 255)
time.sleep(1)
bulb.turn_off()


def greet():
    print("Greetings!")


class MyFirstGUI:
    def __init__(self, master):
        self.master = master
        master.title("A simple GUI")

        self.label = Label(master, text="This is our first GUI!")
        self.label.pack()

        self.greet_button = Button(master, text="Greet", command=greet)
        self.greet_button.pack()

        self.close_button = Button(master, text="Close", command=master.quit)
        self.close_button.pack()


root = Tk()
my_gui = MyFirstGUI(root)
root.mainloop()
