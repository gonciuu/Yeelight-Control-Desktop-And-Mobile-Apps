from yeelight import Bulb
import tkinter as tk

bulb = Bulb("192.168.0.108")
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

        self.frameWithText("Choose Color", 18)
        self.blackFrame()

        self.frameWithText("Red", 13)
        self.redSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        self.redSlider.pack(fill=tk.X)

        self.blackFrame()

        self.frameWithText("Green", 13)
        self.greenSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        self.greenSlider.pack(fill=tk.X)

        self.blackFrame()

        self.frameWithText("Blue", 13)
        self.blueSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        self.blueSlider.pack(fill=tk.X)

        self.blackFrame()

        self.applyButton = tk.Button(master=self.window, command=self.setColors, text="Apply Colors")
        self.applyButton.config(font=("SegoeUI", 10))
        self.applyButton.pack()

        self.window.mainloop()

    def setColors(self):
        bulb.set_rgb(self.redSlider.get(), self.greenSlider.get(), self.blueSlider.get())

    def blackFrame(self):
        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=0, height=20, bg='black')
        self.frame.pack(fill=tk.X)

    def frameWithText(self, text, textSize):
        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=2)
        self.frame.pack(fill=tk.X)
        self.label = tk.Label(master=self.frame, text=text)
        self.label.config(font=("SegoeUI", textSize))
        self.label.pack(fill=tk.X)


app = App()
