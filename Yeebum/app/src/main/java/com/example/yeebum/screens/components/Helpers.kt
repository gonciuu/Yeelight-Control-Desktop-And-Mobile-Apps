package com.example.yeebum.screens.components

import android.view.View
import androidx.core.content.ContextCompat
import com.example.yeebum.R
import com.google.android.material.snackbar.Snackbar

class Helpers {

    fun showSnackBar(view: View, message: String, actionText:String?, action: (() -> Unit)?){
        val snackBar = Snackbar.make(view,message,Snackbar.LENGTH_LONG)
        if(actionText!=null && action!=null) snackBar.setAction(actionText) { action() }
        snackBar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorPrimaryLight))
        snackBar.show()
    }
}