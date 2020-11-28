package com.example.yeebum.screens.components

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.yeebum.R
import com.example.yeebum.control_bulb.ActionsListener
import com.example.yeebum.control_bulb.ChooseValue
import com.example.yeebum.models.Action
import com.example.yeebum.screens.flows_control.FlowsInterface
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.choose_option_dialog.view.*
import kotlinx.android.synthetic.main.color_picker_dialog.view.*
import kotlinx.android.synthetic.main.duration_dialog.view.*
import kotlinx.android.synthetic.main.fragment_action_color_details.*
import kotlinx.android.synthetic.main.seekbar_dialog.view.*

class Helpers {

    fun showSnackBar(view: View, message: String, actionText: String?, action: (() -> Unit)?) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        if (actionText != null && action != null) snackBar.setAction(actionText) { action() }
        snackBar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorPrimaryLight))
        snackBar.show()
    }


    //---------------------------------------------------------| Show Dialog with seekbar to control color temperature of bulb |-------------------------------------------------------
    fun getSeekBarColorTempDialog(activity: FragmentActivity, context: Context, title: String, description: String, actualTemp:Int, chooseValue: ChooseValue): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.seekbar_dialog, null, false)
        view.dialogSeekBar.apply {
            max = 4800
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    view.colorTempValue.text = "${p1 + 1700}k"
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
            progress = actualTemp - 1700
        }

        return activity.let {
            AlertDialog.Builder(context, R.style.DialogTheme)
                .setCancelable(true).setTitle(title).setMessage(description)
                .setView(view).setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton("Apply") { dialog, _ ->
                    chooseValue.onSetColorTemp(view.dialogSeekBar.progress + 1700)
                    dialog.dismiss()
                }.create()
        } ?: throw Exception("Activity must not be null!")
    }
    //=================================================================================================================================================================================

    //---------------------------------------------------------| Show Dialog with color picker to control color of bulb |-------------------------------------------------------
    fun getChooseColorDialog(activity: FragmentActivity, context: Context, title: String,currentColor:Int, chooseValue: ChooseValue): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.color_picker_dialog, null, false)
        view.dialogColorPicker.oldCenterColor = currentColor
        return activity.let {
            AlertDialog.Builder(context, R.style.DialogTheme).setTitle(title)
                .setPositiveButton("Apply") { dialog, _ ->
                    chooseValue.onSetColor(view.dialogColorPicker.color)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.setView(view)
                .create()
        } ?: throw Exception("Activity must not be null!")
    }
    //==========================================================================================================================================================================

    //---------------------------------------------------------| Show Dialog with duration picker to control color of bulb |-------------------------------------------------------
    fun getDurationPickerDialog(activity: FragmentActivity, context: Context, title: String, chooseValue: ChooseValue): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.duration_dialog, null, false)

        arrayListOf<NumberPicker>(view.hoursPickerDialog,view.minutesPickerDialog)
            .forEach {
                it.apply {
                    minValue=0
                    maxValue=60
                    setOnLongPressUpdateInterval(100)
                }
            }

        return activity.let {
            AlertDialog.Builder(context, R.style.DialogTheme).setTitle(title)
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Apply") { dialog, _ ->
                    chooseValue.onSetDuration(view.hoursPickerDialog.value*60 + view.minutesPickerDialog.value)
                    dialog.dismiss()
                }.setNeutralButton("Infinite") { dialog, _ ->
                    chooseValue.onSetDuration(-1)
                    dialog.dismiss() }.setView(view).create()
        } ?: throw Exception("Activity must not be null!")

    }
    //============================================================================================================================================================================


    fun getChooseOptionDialog(activity: FragmentActivity, context: Context, title: String, listener:ActionsListener,action:Action):AlertDialog{
        val view = LayoutInflater.from(context).inflate(R.layout.choose_option_dialog, null, false)

        return activity.let {
            val dialog = AlertDialog.Builder(context, R.style.DialogTheme).setTitle(title).setView(view).create()
            view.editAction.setOnClickListener {
                listener.onActionEdit(action)
                dialog.dismiss()
            }
            view.deleteAction.setOnClickListener {
                listener.onActionDelete(action)
                dialog.dismiss()
            }
            dialog
        } ?: throw Exception("Activity must not be null!")

    }


}