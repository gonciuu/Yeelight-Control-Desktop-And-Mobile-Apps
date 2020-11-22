package com.example.yeebum.control_bulb

object Constants {
    const val ID = "0"
    const val CMD_ON = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"on\",\"smooth\",500]}\r\n"
    const val CMD_OFF = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"off\",\"smooth\",500]}\r\n"
    const val CMD_CT = "{\"id\":%id,\"method\":\"set_ct_abx\",\"params\":[%value, \"smooth\", 500]}\r\n"
    const val CMD_HSV = "{\"id\":%id,\"method\":\"set_hsv\",\"params\":[%value, 100, \"smooth\", 200]}\r\n"
    const val CMD_RGB = "{\"id\":%id,\"method\":\"set_rgb\",\"params\":[#44DD44, 100, \"smooth\", 200]}\r\n"
    const val CMD_BRIGHTNESS = "{\"id\":%id,\"method\":\"set_bright\",\"params\":[%value, \"smooth\", 200]}\r\n"
    const val CMD_BRIGHTNESS_SCENE = "{\"id\":%id,\"method\":\"set_bright\",\"params\":[%value, \"smooth\", 500]}\r\n"
    const val CMD_COLOR_SCENE = "{\"id\":%id,\"method\":\"set_scene\",\"params\":[\"cf\",1,0,\"100,1,%color,1\"]}\r\n"
    const val CMD_CRON_ADD = "{\"id\":%id,\"method\":\"cron_add\",\"params\":[0, %value]}\r\n"

}