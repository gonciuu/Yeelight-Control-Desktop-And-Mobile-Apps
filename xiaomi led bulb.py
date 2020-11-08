from yeelight import Bulb
import tkinter as tk


# bulb = Bulb("192.168.0.108")
# bulb.turn_on()
# bulb.set_rgb(255, 255, 255)


class App:

    def __init__(self):
        self.window = tk.Tk()
        self.window.configure(background='black')
        self.window.title("Xiaomi led Bulb control")
        self.window.geometry("500x500")

        self.redLabel = tk.Label(master=self.window, text="Red", bg="red", fg='white')
        self.greenLabel = tk.Label(master=self.window, text="Green", bg="green", fg='white')
        self.blueLabel = tk.Label(master=self.window, text="Blue", bg="blue", fg='white')
        self.redLabel.grid(row=0, column=0, sticky=tk.S, pady=2)
        self.greenLabel.grid(row=0, column=1, sticky=tk.S, pady=2)
        self.blueLabel.grid(row=0, column=2, sticky=tk.S, pady=2)

        self.redSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL, bg="red", fg='white', command=self.xd)
        self.greenSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL, bg="green", fg='white')
        self.blueSlider = tk.Scale(master=self.window, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL, bg="blue", fg='white')

        self.redSlider.grid(row=1, column=0, sticky=tk.W, padx=(10, 10), pady=10)
        self.greenSlider.grid(row=1, column=1, sticky=tk.W, padx=(10, 10))
        self.blueSlider.grid(row=1, column=2, sticky=tk.W, padx=(10, 10))

        self.redInput = tk.Entry(master=self.window, width=5, bg='red',fg='white')
        self.redInput.grid(row=2, column=0, sticky=tk.W, padx=(10, 10), pady=10)

        self.applyButton = tk.Button(master=self.window, command=self.setColors, text="Apply Colors")
        self.applyButton.grid(row=3, column=1, sticky=tk.W, pady=10)
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

    def xd(self, val):
        print("XD")


app = App()
