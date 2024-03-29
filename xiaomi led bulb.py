from yeelight import Bulb, TemperatureTransition, SleepTransition, Flow, RGBTransition, discover_bulbs
import tkinter as tk
import tkinter.messagebox

from yeelight.transitions import disco, strobe, pulse, alarm, police, lsd, christmas, random_loop


class App:

    def __init__(self):
        self.window = tk.Tk()
        self.window.configure(bg='black')
        self.window.title("Xiaomi led Bulb control")
        self.window.geometry("1000x580")

        self.insertIp()
        self.window.mainloop()

    def insertIp(self):

        self.ipText = tk.Label(master=self.window, text='Enter your xiaomi yeelight ip', fg='white', bg='black',
                               font=('Helvetica', 12, 'bold'))
        self.ipText.grid(row=0, column=0, sticky=tk.N, pady=(15, 0), padx=(10, 0))
        self.ipEntry = tk.Entry(master=self.window, width=30, bg='black', fg='white', font=('Helvetica', 12, 'bold'))
        self.ipEntry.grid(row=0, column=1, sticky=tk.N, pady=(15, 0), padx=(10, 0))
        self.confirmIp = tk.Button(master=self.window, width=30, bg='white', fg='black', text="Confirm",
                                   command=self.showPanel)
        self.confirmIp.grid(row=0, column=2, sticky=tk.N, pady=(15, 0), padx=(10, 0))
        bulbs_list = discover_bulbs()
        if len(bulbs_list) > 0:
            self.ipEntry.delete(0, tk.END)
            self.ipEntry.insert(0, bulbs_list[0]['ip'])

    def showPanel(self):
        self.bulb = Bulb(self.ipEntry.get(), effect="smooth", duration=1000)
        self.confirmIp.destroy()
        self.ipEntry.destroy()
        self.ipText.destroy()

        # -------------------------------------| RGB FRAME |---------------------------------------------
        self.rgb_handler = tk.Frame(master=self.window, bg='black')
        self.rgb_handler.grid(row=0, column=0, sticky=tk.N, pady=(15, 0), padx=(50, 0))

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

        # ---------------------------------| POWER FRAME |----------------------------------------

        self.power_handler = tk.Frame(master=self.window, bg='black')
        self.power_handler.grid(row=0, column=1, sticky=tk.N, padx=(60, 60), pady=20)

        self.title = tk.Label(master=self.power_handler, text='Xiaomi led Bulb remote control', fg='white', bg='black',
                              font=('Helvetica', 14, 'bold'))
        self.title.grid(row=0, column=1, sticky=tk.N)

        self.buttonsHandler = tk.Frame(master=self.power_handler, bg='black')
        self.buttonsHandler.grid(row=1, column=1, sticky=tk.N, pady=10)

        self.turnOnButton = tk.Button(master=self.buttonsHandler, text="ON", width=15, command=self.turnOnBulb, padx=5)
        self.turnOnButton.grid(row=0, column=0, sticky=tk.N, padx=5)

        self.turnOffButton = tk.Button(master=self.buttonsHandler, text="OFF", width=15, command=self.turnOffBulb)
        self.turnOffButton.grid(row=0, column=1, sticky=tk.N, padx=5)

        self.title = tk.Label(master=self.power_handler, text='Brightness', fg='white', bg='black',
                              font=('Helvetica', 12, 'bold'))
        self.title.grid(row=2, column=1, sticky=tk.N, pady=(20, 5))

        self.brightnessSlider = tk.Scale(master=self.power_handler, from_=0, to=100, tickinterval=20,
                                         orient=tk.HORIZONTAL, bg="#FFFFFF", fg='#000000', length=200)
        self.brightnessSlider.set(80)
        self.brightnessSlider.grid(row=3, column=1, sticky=tk.N, pady=10)

        self.applyBrightnessButton = tk.Button(master=self.power_handler, text="Apply Brightness", width=15,
                                               command=self.setBrightness)
        self.applyBrightnessButton.grid(row=4, column=1, sticky=tk.N, pady=10)

        # ---------------------------| MODES FRAME |----------------------------------

        self.modes_handler = tk.Frame(master=self.window, bg='black')
        self.modes_handler.grid(row=0, column=2, sticky=tk.N, padx=30, pady=50)

        self.mode_1 = tk.Button(master=self.modes_handler, text="Flow 1 (smooth)", width=15,
                                command=lambda: self.showFlow([
                                    RGBTransition(20, 255, 20, 2000),
                                    RGBTransition(255, 20, 20, 2000),
                                    RGBTransition(20, 20, 255, 2000),
                                    RGBTransition(255, 255, 20, 2000),
                                    RGBTransition(255, 20, 255, 2000),
                                    RGBTransition(20, 255, 255, 2000)
                                ], 10))
        self.mode_1.grid(column=0, row=0, pady=(0, 15))

        self.mode_2 = tk.Button(master=self.modes_handler, text="Flow 2 (speed)", width=15,
                                command=lambda: self.showFlow([
                                    RGBTransition(20, 255, 20, 100),
                                    RGBTransition(255, 20, 20, 100),
                                    RGBTransition(20, 20, 255, 100),
                                    RGBTransition(255, 255, 20, 100),
                                    RGBTransition(255, 20, 255, 100),
                                    RGBTransition(20, 255, 255, 100)
                                ], 30))
        self.mode_2.grid(column=0, row=1, pady=(0, 15))

        self.mode_3 = tk.Button(master=self.modes_handler, text="Flow 3 (temp)", width=15,
                                command=lambda: self.showFlow([
                                    TemperatureTransition(6500, 1000),
                                    TemperatureTransition(5200, 1000),
                                    TemperatureTransition(4000, 1000),
                                    TemperatureTransition(2800, 1000),
                                    TemperatureTransition(1700, 1000)
                                ], 5))
        self.mode_3.grid(column=0, row=2, pady=(0, 15))

        self.mode_4 = tk.Button(master=self.modes_handler, text="Flow 4 (disco)", width=15,
                                command=lambda: self.showFlow(disco(), 5))

        self.mode_4.grid(column=0, row=3, pady=(0, 15))

        self.mode_5 = tk.Button(master=self.modes_handler, text="Flow 5 (strobe)", width=15,
                                command=lambda: self.showFlow(strobe(), 20))

        self.mode_5.grid(column=0, row=4, pady=(0, 15))

        self.mode_6 = tk.Button(master=self.modes_handler, text="Flow 6 (alarm)", width=15,
                                command=lambda: self.showFlow(alarm(), 5))

        self.mode_6.grid(column=0, row=5, pady=(0, 15))

        self.mode_7 = tk.Button(master=self.modes_handler, text="Flow 7 (police)", width=15,
                                command=lambda: self.showFlow(police(), 10))

        self.mode_7.grid(column=0, row=6, pady=(0, 15))

        self.mode_8 = tk.Button(master=self.modes_handler, text="Flow 8 (smooth)", width=15,
                                command=lambda: self.showFlow(lsd(), 5))

        self.mode_8.grid(column=0, row=7, pady=(0, 15))

        self.mode_9 = tk.Button(master=self.modes_handler, text="Flow 9 (christmas)", width=15,
                                command=lambda: self.showFlow(christmas(), 5))

        self.mode_9.grid(column=0, row=8, pady=(0, 15))

        self.mode_10 = tk.Button(master=self.modes_handler, text="Flow 10 (random)", width=15,
                                 command=lambda: self.showFlow(random_loop(), 5))

        self.mode_10.grid(column=0, row=9, pady=(0, 15))

        self.quitBT = tk.Button(master=self.window, text="Quit", font=('Helvetica', 12, 'bold'), command=self.quit)
        self.quitBT.grid(column=1, row=1, pady=(0, 15))

    def turnOnBulb(self):
        try:
            self.bulb.turn_on()
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def turnOffBulb(self):
        try:
            self.bulb.turn_off()
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def setBrightness(self):
        try:
            self.bulb.set_brightness(int(self.brightnessSlider.get()))
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def setColors(self):
        try:
            self.bulb.set_rgb(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def setRedText(self, val):
        try:
            self.redInput.delete(0, tk.END)
            self.redInput.insert(0, val)
            self.setFrameColor(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def setGreenText(self, val):
        try:
            self.greenInput.delete(0, tk.END)
            self.greenInput.insert(0, val)
            self.setFrameColor(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def setBlueText(self, val):
        try:
            self.blueInput.delete(0, tk.END)
            self.blueInput.insert(0, val)
            self.setFrameColor(int(self.redInput.get()), int(self.greenInput.get()), int(self.blueInput.get()))
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def setFrameColor(self, r, g, b):
        try:
            rgb_color = "#%02x%02x%02x" % (r, g, b)
            self.colorFrame.configure(background=rgb_color)
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def showFlow(self, transactions, count):
        try:
            flow = Flow(
                count=count,
                action=Flow.actions.recover,
                transitions=transactions
            )
            self.bulb.start_flow(flow)
        except:
            self.showAlert("Warning", "Your bulb is probably turn off from internet or your ip is badly formatted")

    def quit(self):
        self.window.destroy()

    @staticmethod
    def showAlert(title, message):
        tk.messagebox.showwarning(title, message)


app = App()
