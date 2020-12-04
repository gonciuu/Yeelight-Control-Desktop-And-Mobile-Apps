package com.example.yeebum.control_bulb

import android.util.Log
import android.graphics.Color
import com.example.yeebum.models.Action
import com.example.yeebum.models.ActionType

object Constants {
    const val ID = "0"
    const val CMD_ON = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"on\",\"smooth\",500]}\r\n"
    const val CMD_OFF = "{\"id\":%id,\"method\":\"set_power\",\"params\":[\"off\",\"smooth\",500]}\r\n"
    const val CMD_CT = "{\"id\":%id,\"method\":\"set_ct_abx\",\"params\":[%value, \"smooth\", 500]}\r\n"
    const val CMD_HSV = "{\"id\":%id,\"method\":\"set_hsv\",\"params\":[%value, 100, \"smooth\", 200]}\r\n"
    const val CMD_BRIGHTNESS = "{\"id\":%id,\"method\":\"set_bright\",\"params\":[%value, \"smooth\", 200]}\r\n"
    const val CMD_CRON_ADD = "{\"id\":%id,\"method\":\"cron_add\",\"params\":[0, %value]}\r\n"
    const val CMD_CRON_DELETE = "{\"id\":%id,\"method\":\"cron_del\",\"params\":[0]}\r\n"

    fun getFlowCommand(listOfActions: ArrayList<Action>):String{
        var params = ""
        listOfActions.forEach { action->

            //get mode int type
            val mode:Int = when(action.type){
                ActionType.Color -> 1
                ActionType.ColorTemp -> 2
                ActionType.Sleep -> 7
            }

            //get rgb color from int or if the type in color temp don't change anything
            val colorToSet: Int = if(mode ==1) ( Color.red(action.color) * 65536 + Color.green(action.color)*256 +  Color.blue(action.color)) else action.color

            Log.d("XD",action.duration.toString())
            //add params to respond string
            params+="${if(action.duration>0) action.duration else 50}, $mode, ${colorToSet}, ${action.brightness}"
            if(listOfActions.indexOf(action) != listOfActions.size-1)
                params+= ", "
        }


        var flowCommand = "{\"id\":%id,\"method\":\"start_cf\",\"params\":[ ${listOfActions.size}, 0, \"$params\"]}\r\n"
        flowCommand = flowCommand.replace("%id", ID)
        Log.d("TAG", flowCommand)
        return flowCommand
    }

}