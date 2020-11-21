package com.example.yeebum.screens.components

import android.app.AlertDialog
import android.content.Context
import com.example.yeebum.R
import dmax.dialog.SpotsDialog

object LoadingDialog {

    fun getDialog(context: Context, message: String) : AlertDialog = SpotsDialog.Builder()
        .setContext(context).setMessage(message).setTheme(R.style.LoadingDialogTheme).build()
}