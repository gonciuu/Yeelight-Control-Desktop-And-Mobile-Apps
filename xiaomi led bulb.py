from random import Random, randrange

from yeelight import Bulb
import time
import tkinter as tk

bulb = Bulb("myIp")
bulb.turn_on()
bulb.set_rgb(255, 255, 255)


# frame1 = tk.Frame(master=window, width=200, height=100, bg="red")
# frame1.pack(fill=tk.BOTH, side=tk.LEFT, expand=True)

# frame2 = tk.Frame(master=window, width=100, bg="yellow")
# rame2.pack(fill=tk.BOTH, side=tk.LEFT, expand=True)

# frame3 = tk.Frame(master=window, width=50, bg="blue")
# frame3.pack(fill=tk.BOTH, side=tk.LEFT, expand=True)
class App:

    def __init__(self):
        self.window = tk.Tk()
        self.window.configure(background='black')
        self.window.title("Xiaomi led Bulb control")
        self.window.geometry("500x500")

        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=5)
        self.frame.pack(fill=tk.X)
        self.label = tk.Label(master=self.frame, text="RED")
        self.label.pack(fill=tk.X)

        self.redSlider = tk.Scale(master=self.window, from_=0, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        self.redSlider.pack(fill=tk.X)

        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=5)
        self.frame.pack(fill=tk.X)
        self.label = tk.Label(master=self.frame, text="GREEN")
        self.label.pack(fill=tk.X)

        self.greenSlider = tk.Scale(master=self.window, from_=0, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        self.greenSlider.pack(fill=tk.X)

        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=5)
        self.frame.pack(fill=tk.X)
        self.label = tk.Label(master=self.frame, text="BLUE")
        self.label.pack(fill=tk.X)

        self.blueSlider = tk.Scale(master=self.window, from_=0, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        self.blueSlider.pack(fill=tk.X)

        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=0, height=40,bg='black')
        self.frame.pack(fill=tk.X)

        self.applyButton = tk.Button(master=self.window, command=self.setColors, text="Apply Colors")
        self.applyButton.pack()
        self.window.mainloop()

    def setColors(self):
        bulb.set_rgb(self.redSlider.get(), self.greenSlider.get(), self.blueSlider.get())


app = App()
