package com.example.yeebum.control_bulb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedOutputStream
import java.net.Socket

class BulbControlViewModel:ViewModel() {
    private var socket = MutableLiveData<Socket>()
    private var bos = MutableLiveData<BufferedOutputStream>()

    fun setSocket(socketToSet:Socket){
        this.socket.value = socketToSet
    }

    fun getSocket():LiveData<Socket> = this.socket

    fun setBOS(bosToSet:BufferedOutputStream){
        this.bos.value = bosToSet
    }

    fun getBOS():LiveData<BufferedOutputStream> = bos

}