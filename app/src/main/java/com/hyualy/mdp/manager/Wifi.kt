package com.hyualy.mdp.manager

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
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

    fun sendData(ipAddress: String, message: String) {
        Thread {
            val socket = Socket(ipAddress, PORT)
            val writer = OutputStreamWriter(socket.getOutputStream())
            writer.write(message)
            writer.flush()
            writer.close()
            socket.close()
        }.start()
    }

    fun receiveData(callback: (String) -> Unit) {
        Thread {
            val serverSocket = ServerSocket(PORT)
            val clientSocket = serverSocket.accept()
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val message = reader.readLine()
            callback(message)
            reader.close()
            clientSocket.close()
            serverSocket.close()
        }.start()
    }

    fun requestEnableWifi(context: Context) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        println((!wifiManager.isWifiEnabled).toString())
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(context, "Meister_login Wifi에 연결해 주세요.", Toast.LENGTH_SHORT).show()
            connectedWifiSSID(context)
//            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
//            context.startActivity(intent)
        } else {
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.lobby_fragment, AccountFragment())
                .commit()
        }
    }

    @SuppressLint("MissingPermission")
    private fun connectedWifiSSID(context: Context) {
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val scanResults: List<ScanResult> = manager.scanResults
        var ssid: String? = null

        // 지금 연결된 wifi ssid 얻기
        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback(
            FLAG_INCLUDE_LOCATION_INFO
        ) {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val wifiInfo = networkCapabilities.transportInfo as WifiInfo
                ssid = wifiInfo.ssid
                println(ssid)
            }
        }

        if (ssid == null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerNetworkCallback(request, networkCallback)

            for (scanResult in scanResults) {
                if (scanResult.wifiSsid.toString() == "Meister_login") {
                    val wifiNetworkSpecifier = WifiNetworkSpecifier.Builder()
                        .setSsid(scanResult.wifiSsid.toString())
                        .setWpa2Passphrase("intec1234")
                        .build()
                    val networkRequest = NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .setNetworkSpecifier(wifiNetworkSpecifier)
                        .build()

                    connectivityManager.requestNetwork(
                        networkRequest,
                        ConnectivityManager.NetworkCallback()
                    )
                }
            }
        }
    }
}