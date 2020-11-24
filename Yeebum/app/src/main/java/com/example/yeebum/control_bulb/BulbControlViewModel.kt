package com.example.yeebum.control_bulb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedOutputStream
import java.net.Socket

class BulbControlViewModel:ViewModel() {
    private var socket = MutableLiveData<Socket>()
    private var bos = MutableLiveData<BufferedOutputStream>()

    private var ip = MutableLiveData<String>()
    private var port = MutableLiveData<Int>()

    fun setSocket(socketToSet:Socket){
        this.socket.value = socketToSet
    }

    fun getSocket():LiveData<Socket> = this.socket

    fun setBOS(bosToSet:BufferedOutputStream){
        this.bos.value = bosToSet
    }

    fun getBOS():LiveData<BufferedOutputStream> = bos

    fun setIp(ipToSet:String){
        this.ip.value = ipToSet
    }

    fun getIp():LiveData<String> = ip

    fun setPort(portToSet:Int){
        this.port.value = portToSet
    }

    fun getPort():LiveData<Int> = port

}