package com.hyualy.mdp.manager

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.AccountFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

object Wifi {

    private const val port = 5000

    fun sendData(ipAddress: String, message: String) {
        Thread {
            val socket = Socket(ipAddress, port)
            val writer = OutputStreamWriter(socket.getOutputStream())
            writer.write(message)
            writer.flush()
            writer.close()
            socket.close()
        }.start()
    }

    fun receiveData(callback: (String) -> Unit) {
        Thread {
            val serverSocket = ServerSocket(port)
            val clientSocket = serverSocket.accept()
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val message = reader.readLine()
            callback(message)
            reader.close()
            clientSocket.close()
            serverSocket.close()
        }.start()
    }

    fun getAllIPAddresses(connectivityManager: ConnectivityManager): List<String> {
        val ipAddresses = mutableListOf<String>()

        val networks = connectivityManager.allNetworks
        for (network in networks) {
            val linkProperties = connectivityManager.getLinkProperties(network)
            val addresses = linkProperties?.linkAddresses
            addresses?.forEach {
                ipAddresses.add(it.address.hostAddress!!)
            }
        }

        return ipAddresses
    }

    fun requestEnableWifi(context: Context) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            context.startActivity(intent)
        } else {
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.lobby_fragment, AccountFragment())
                .commit()
        }
    }
}