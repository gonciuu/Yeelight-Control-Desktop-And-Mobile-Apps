package com.example.yeebum.screens.components

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yeebum.R
import com.example.yeebum.control_bulb.ActionsListener
import com.example.yeebum.control_bulb.ChooseValue
import com.example.yeebum.models.Action
import com.example.yeebum.models.Bulb
import com.example.yeebum.screens.adapters.recycler_views.DevicesAdapter
import com.example.yeebum.screens.bulbs.BulbsInterface
import com.example.yeebum.screens.bulbs.SearchedBulbsInterface
import com.example.yeebum.screens.other.SettingsInterface
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.choose_option_dialog.view.*
import kotlinx.android.synthetic.main.color_picker_dialog.view.*
import kotlinx.android.synthetic.main.devices_dialog.view.*
import kotlinx.android.synthetic.main.duration_dialog.view.*
import kotlinx.android.synthetic.main.seekbar_dialog.view.*

class Helpers {

    fun showSnackBar(view: View, message: String, actionText: String?, action: (() -> Unit)?) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        if (actionText != null && action != null) snackBar.setAction(actionText) { action() }
        snackBar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorPrimaryLight))
        snackBar.show()
    }


    fun onSubmitKeyboard(editText: EditText, action: () -> Unit) {
        editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                action()
                true
            } else
                false
        }
    }

    fun closeKeyboard(fragmentActivity: FragmentActivity) {
        val view = fragmentActivity.currentFocus
        view?.let { v ->
            val imm =
                fragmentActivity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }


    //---------------------------------------------------------| Show Dialog with seekbar to control color temperature of bulb |-------------------------------------------------------
    fun getSeekBarColorTempDialog(
        activity: FragmentActivity,
        context: Context,
        title: String,
        description: String,
        actualTemp: Int,
        chooseValue: ChooseValue
    ): AlertDialog {
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
    fun getChooseColorDialog(
        activity: FragmentActivity,
        context: Context,
        title: String,
        currentColor: Int,
        chooseValue: ChooseValue
    ): AlertDialog {
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
    fun getDurationPickerDialog(
        activity: FragmentActivity,
        context: Context,
        title: String,
        chooseValue: ChooseValue
    ): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.duration_dialog, null, false)

        arrayListOf<NumberPicker>(view.hoursPickerDialog, view.minutesPickerDialog)
            .forEach {
                it.apply {
                    minValue = 0
                    maxValue = 60
                    setOnLongPressUpdateInterval(100)
                }
            }

        return activity.let {
            AlertDialog.Builder(context, R.style.DialogTheme).setTitle(title)
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Apply") { dialog, _ ->
                    chooseValue.onSetDuration(view.hoursPickerDialog.value * 60 + view.minutesPickerDialog.value)
                    dialog.dismiss()
                }.setNeutralButton("Infinite") { dialog, _ ->
                    chooseValue.onSetDuration(-1)
                    dialog.dismiss()
                }.setView(view).create()
        } ?: throw Exception("Activity must not be null!")

    }
    //============================================================================================================================================================================


    //-----------------------------------------------| Edit Or Delete Action Dialog |----------------------------------------
    fun getChooseOptionDialog(
        activity: FragmentActivity,
        context: Context,
        title: String,
        listener: ActionsListener,
        action: Action
    ): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.choose_option_dialog, null, false)

        return activity.let {
            val dialog =
                AlertDialog.Builder(context, R.style.DialogTheme).setTitle(title).setView(view)
                    .create()
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
    //===========================================================================================================================


    //------------------------------------------------| Get list Of Devices Dialog |-----------------------------------------------
    fun getDevicesDialog(
        activity: FragmentActivity,
        context: Context,
        title: String,
        devicesList: ArrayList<HashMap<String, String>>,
        listener: SearchedBulbsInterface
    ) {
        val view = LayoutInflater.from(context).inflate(R.layout.devices_dialog, null, false)
        val dialog =
            AlertDialog.Builder(context, R.style.DialogTheme).setTitle(title).setView(view).create()

        //show refresh button animation
        ObjectAnimator.ofFloat(view.refreshButton, View.ROTATION, 0f, 360f).apply {
            duration = 2000
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
            start()
        }

        if (devicesList.isEmpty()) {
            view.emptyListInfo.visibility = View.VISIBLE
            view.devicesRecyclerView.visibility = View.GONE
        } else {
            view.devicesRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = DevicesAdapter(devicesList, listener, dialog)
            }
        }

        view.refreshButton.setOnClickListener {
            dialog.dismiss()
            listener.onRefresh()
        }

            activity.let {
                dialog.show()
            }

    }
    //===============================================================================================================================


    //----------------------------------| Show options Dialog about bulb |----------------------------------------------
    fun getChooseOptionDialogBulb(
        activity: FragmentActivity,
        context: Context,
        title: String,
        listener: BulbsInterface,
        bulb: Bulb
    ): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.choose_option_dialog, null, false)

        return activity.let {
            val dialog =
                AlertDialog.Builder(context, R.style.DialogTheme).setTitle(title).setView(view)
                    .create()
            view.editAction.setOnClickListener {
                listener.onEditBulb(bulb)
                dialog.dismiss()
            }
            view.deleteAction.setOnClickListener {
                listener.onDeleteBulb(bulb)
                dialog.dismiss()
            }
            dialog
        } ?: throw Exception("Activity must not be null!")
    }
    //======================================================================================================================

    //-------------------------------| Show conform factory Settings Dialog |---------------------------------------
    fun getConfirmFactorySettingsDialog(
        activity: FragmentActivity,
        context: Context,
        listener: SettingsInterface
    ): AlertDialog {
        return activity.let {
            AlertDialog.Builder(context, R.style.DialogTheme).setTitle("Are you sure?")
                .setMessage("Are you sure to back to factory settings?")
                .setPositiveButton("Confirm") { dialog, _ ->
                    listener.setFactorySettings()
                    dialog.dismiss()
                }.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.create()
        } ?: throw Exception("Activity must not be null!")
    }
    //===============================================================================================================

}