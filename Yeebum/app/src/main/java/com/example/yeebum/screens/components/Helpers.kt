package com.example.yeebum.screens.components

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.yeebum.R
import com.example.yeebum.control_bulb.ChooseValue
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.seekbar_dialog.view.*

class Helpers {

    fun showSnackBar(view: View, message: String, actionText: String?, action: (() -> Unit)?) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        if (actionText != null && action != null) snackBar.setAction(actionText) { action() }
        snackBar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorPrimaryLight))
        snackBar.show()
    }


    fun getSeekBarDialog(activity: FragmentActivity, context: Context, title: String, description: String, chooseValue: ChooseValue): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.seekbar_dialog,null,false)
        view.dialogSeekBar.apply {
            max = 4800
            progress = 3000
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    view.colorTempValue.text = "${p1+1700}k"
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
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
        }?: throw Exception("Activity must not be null!")
    }

}