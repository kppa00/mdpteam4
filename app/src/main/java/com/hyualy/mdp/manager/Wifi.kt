package com.hyualy.mdp.manager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.AccountFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket


object Wifi {

    private const val PORT = 5000
    private const val IP_ADDRESS = "192.168.137.36"

    fun sendData(message: String) {
        Thread {
            try {
                val socket = Socket(IP_ADDRESS, PORT)
                val writer = OutputStreamWriter(socket.getOutputStream())
                writer.write(message)
                writer.flush()
                writer.close()
                socket.close()
            } catch (_: Exception) {
                println("Data send Failed")
            }
        }.start()
    }

    fun receiveData(context: Context, callback: (String) -> Unit) {
        Thread {
            try {
                val serverSocket = ServerSocket(PORT)
                val clientSocket = serverSocket.accept()
                val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val message = reader.readLine()
                (context as Activity).runOnUiThread {
                    callback(message)
                }
                reader.close()
                clientSocket.close()
                serverSocket.close()
            } catch (_: Exception) {
                println("Data reception failed")
            }
        }.start()
    }

    fun requestEnableWifi(context: Context) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ssid = connectedWifiSSID(context)
        val removedQuotesSSID = removeQuotes(ssid)
        if (wifiManager.isWifiEnabled && removedQuotesSSID == "aifactory") {
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.lobby_fragment, AccountFragment())
                .commit()
        } else {
            Toast.makeText(context, "aifactory Wifi에 연결해 주세요.", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            context.startActivity(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun connectedWifiSSID(context: Context) : String? {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return null
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return null
        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            return wifiInfo.ssid
        }
        return null
    }

    private fun removeQuotes(input: String?): String? {
        if (input == null) { return null }
        if (input.length >= 2 && input.first() == '"' && input.last() == '"') {
            return input.substring(1, input.length - 1)
        }
        return input
    }
}