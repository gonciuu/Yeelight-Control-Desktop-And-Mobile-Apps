from yeelight import Bulb
import tkinter as tk


bulb = Bulb("192.168.0.108")
bulb.turn_on()


class App:

    def __init__(self):
        self.window = tk.Tk()
        self.window.configure(bg='black')
        self.window.title("Xiaomi led Bulb control")
        self.window.geometry("500x500")

        self.redLabel = tk.Label(master=self.window, text="Red", bg="red", fg='white')
        self.greenLabel = tk.Label(master=self.window, text="Green", bg="green", fg='white')
        self.blueLabel = tk.Label(master=self.window, text="Blue", bg="blue", fg='white')
        self.redLabel.grid(row=0, column=0, sticky=tk.S, pady=(12, 2))
        self.greenLabel.grid(row=0, column=1, sticky=tk.S, pady=(12, 2))
        self.blueLabel.grid(row=0, column=2, sticky=tk.S, pady=(12, 2))

        self.redSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL, bg="red",
                                  fg='white', command=self.setRedText)
        self.greenSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL,
                                    bg="green", fg='white', command=self.setGreenText)
        self.blueSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL, bg="blue",
                                   fg='white', command=self.setBlueText)

        self.redSlider.grid(row=1, column=0, sticky=tk.W, padx=(10, 10), pady=10)
        self.greenSlider.grid(row=1, column=1, sticky=tk.W, padx=(10, 10))
        self.blueSlider.grid(row=1, column=2, sticky=tk.W, padx=(10, 10))

        self.redInput = tk.Entry(master=self.window, width=5, bg='red', fg='white')
        self.greenInput = tk.Entry(master=self.window, width=5, bg='green', fg='white')
        self.blueInput = tk.Entry(master=self.window, width=5, bg='blue', fg='white')
        self.redInput.insert(tk.END, 1)
        self.greenInput.insert(tk.END, 1)
        self.blueInput.insert(tk.END, 1)
        self.redInput.grid(row=2, column=0, sticky=tk.N, padx=(10, 10))
        self.greenInput.grid(row=2, column=1, sticky=tk.N, padx=(10, 10))
        self.blueInput.grid(row=2, column=2, sticky=tk.N, padx=(10, 10))

        self.colorFrame = tk.Frame(master=self.window, bg="black", width=40, height=40, relief=tk.RIDGE, borderwidth=2)
        self.colorFrame.grid(row=3, column=1, sticky=tk.S, padx=(10, 10), pady=(10, 15))

        self.applyButton = tk.Button(master=self.window, command=self.setColors, text="Apply Color")
        self.applyButton.grid(row=4, column=1, sticky=tk.S)
        # self.frameWithText("Choose Color", 18)
        # self.blackFrame()
        #
        # self.frameWithText("Red", 13)
        # self.redSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        # self.redSlider.pack(fill=tk.X)
        #
        # self.blackFrame()
        #
        # self.frameWithText("Green", 13)
        # self.greenSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        # self.greenSlider.pack(fill=tk.X)
        #
        # self.blackFrame()
        #
        # self.frameWithText("Blue", 13)
        # self.blueSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=50, orient=tk.HORIZONTAL)
        # self.blueSlider.pack(fill=tk.X)
        #
        # self.blackFrame()
        #
        # self.applyButton = tk.Button(master=self.window, command=self.setColors, text="Apply Colors")
        # self.applyButton.config(font=("SegoeUI", 10))
        # self.applyButton.pack()
        #
        # self.switch_frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=3)
        # self.switch_frame.pack(side="left", fill=tk.X, expand=True)
        #
        # self.switch_variable = tk.StringVar(value="On")
        # self.turnOnButton = tk.Radiobutton(self.switch_frame, text="On", variable=self.switch_variable,
        #                                   indicatoron=False, value="On", width=8, command=self.turnOnBulb)
        # self.turnOffButton = tk.Radiobutton(self.switch_frame, text="Off", variable=self.switch_variable,
        #                                    indicatoron=False, value="Off", width=8, command=self.turnOffBulb)
        # self.turnOnButton.config(font=("SegoeUI", 12))
        # self.turnOffButton.config(font=("SegoeUI", 12))
        #
        # self.turnOnButton.pack(side="left", fill=tk.X, expand=True)
        # self.turnOffButton.pack(side="left", fill=tk.X, expand=True)

        self.window.mainloop()

    @staticmethod
    def turnOnBulb():
        bulb.turn_on()

    @staticmethod
    def turnOffBulb():
        bulb.turn_off()

    def setColors(self):
        bulb.set_rgb(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))

    def blackFrame(self):
        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=0, height=20, bg='black')
        self.frame.pack(fill=tk.X)

    def frameWithText(self, text, textSize):
        self.frame = tk.Frame(master=self.window, relief=tk.GROOVE, borderwidth=2)
        self.frame.pack(fill=tk.X)
        self.label = tk.Label(master=self.frame, text=text)
        self.label.config(font=("SegoeUI", textSize))
        self.label.pack(fill=tk.X)

    def setRedText(self, val):
        self.redInput.delete(0, tk.END)
        self.redInput.insert(0, val)
        self.setFrameColor(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))

    def setGreenText(self, val):
        self.greenInput.delete(0, tk.END)
        self.greenInput.insert(0, val)
        self.setFrameColor(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))

    def setBlueText(self, val):
        self.blueInput.delete(0, tk.END)
        self.blueInput.insert(0, val)
        self.setFrameColor(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))

    def setFrameColor(self, r, g, b):
        rgb_color = "#%02x%02x%02x" % (r, g, b)
        self.colorFrame.configure(background=rgb_color)


app = App()
