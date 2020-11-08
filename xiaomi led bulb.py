from yeelight import Bulb
import tkinter as tk

bulb = Bulb("192.168.0.108")
bulb.turn_on()


class App:

    def __init__(self):
        self.window = tk.Tk()
        self.window.configure(bg='black')
        self.window.title("Xiaomi led Bulb control")
        self.window.geometry("900x500")
        self.rgb_handler = tk.Frame(master=self.window, bg='black')
        self.rgb_handler.grid(row=0, column=0, sticky=tk.S)

        self.redLabel = tk.Label(master=self.rgb_handler, text="Red", bg="red", fg='white')
        self.greenLabel = tk.Label(master=self.rgb_handler, text="Green", bg="green", fg='white')
        self.blueLabel = tk.Label(master=self.rgb_handler, text="Blue", bg="blue", fg='white')
        self.redLabel.grid(row=0, column=0, sticky=tk.S, pady=(12, 2))
        self.greenLabel.grid(row=0, column=1, sticky=tk.S, pady=(12, 2))
        self.blueLabel.grid(row=0, column=2, sticky=tk.S, pady=(12, 2))

        self.redSlider = tk.Scale(master=self.rgb_handler, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL,
                                  bg="red", fg='white', command=self.setRedText)
        self.greenSlider = tk.Scale(master=self.rgb_handler, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL,
                                    bg="green", fg='white', command=self.setGreenText)
        self.blueSlider = tk.Scale(master=self.rgb_handler, from_=1, to=255, tickinterval=60, orient=tk.VERTICAL,
                                   bg="blue", fg='white', command=self.setBlueText)

        self.redSlider.grid(row=1, column=0, sticky=tk.W, padx=(10, 10), pady=10)
        self.greenSlider.grid(row=1, column=1, sticky=tk.W, padx=(10, 10))
        self.blueSlider.grid(row=1, column=2, sticky=tk.W, padx=(10, 10))

        self.redInput = tk.Entry(master=self.rgb_handler, width=5, bg='red', fg='white')
        self.greenInput = tk.Entry(master=self.rgb_handler, width=5, bg='green', fg='white')
        self.blueInput = tk.Entry(master=self.rgb_handler, width=5, bg='blue', fg='white')
        self.redInput.insert(tk.END, 1)
        self.greenInput.insert(tk.END, 1)
        self.blueInput.insert(tk.END, 1)
        self.redInput.grid(row=2, column=0, sticky=tk.N, padx=(10, 10))
        self.greenInput.grid(row=2, column=1, sticky=tk.N, padx=(10, 10))
        self.blueInput.grid(row=2, column=2, sticky=tk.N, padx=(10, 10))

        self.colorFrame = tk.Frame(master=self.rgb_handler, bg="black", width=40, height=40, relief=tk.RIDGE,
                                   borderwidth=2)
        self.colorFrame.grid(row=3, column=1, sticky=tk.S, padx=(10, 10), pady=(10, 15))

        self.applyButton = tk.Button(master=self.rgb_handler, command=self.setColors, text="Apply Color")
        self.applyButton.grid(row=4, column=1, sticky=tk.S)

        self.power_handler = tk.Frame(master=self.window, bg='black')
        self.power_handler.grid(row=0, column=1, sticky=tk.N, padx=70, pady=20)

        self.title = tk.Label(master=self.power_handler, text='Xiaomi led Bulb remote control', fg='white', bg='black', font=('Helvetica', 14, 'bold'))
        self.title.grid(row=0, column=1, sticky=tk.N)

        self.buttonsHandler = tk.Frame(master=self.power_handler, bg='black')
        self.buttonsHandler.grid(row=1, column=1, sticky=tk.N, pady=10)

        self.turnOnButton = tk.Button(master=self.buttonsHandler, text="ON", width=15, command=self.turnOnBulb, padx=5)
        self.turnOnButton.grid(row=0, column=0, sticky=tk.N, padx=5)

        self.turnOffButton = tk.Button(master=self.buttonsHandler, text="OFF", width=15, command=self.turnOffBulb)
        self.turnOffButton.grid(row=0, column=1, sticky=tk.N, padx=5)

        self.window.mainloop()

    @staticmethod
    def turnOnBulb():
        bulb.turn_on()

    @staticmethod
    def turnOffBulb():
        bulb.turn_off()

    def setColors(self):
        bulb.set_rgb(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))

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
