package com.example.yeebum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yeebum.models.DaggerFlowComponent
import com.example.yeebum.screens.AppDrawer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppDrawer().setupDrawerNavigation(this)

    }
}