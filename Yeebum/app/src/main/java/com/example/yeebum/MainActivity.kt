package com.example.yeebum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yeebum.screens.components.AppDrawer
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            setContentView(R.layout.activity_main)
        }catch (ex:Exception){}


        AppDrawer().setupDrawerNavigation(this)

    }
}