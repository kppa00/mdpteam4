package com.hyualy.mdp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.ActivityCompat

class BluetoothUtil(private val context: Context) {

    private val bluetoothAdapter = (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    @Volatile
    private var sInstance: BluetoothUtil? = null
    fun getInstance(): BluetoothUtil? {
        if (sInstance == null) {
            synchronized(BluetoothUtil::class.java) {
                if (sInstance == null) {
                    sInstance = BluetoothUtil(context)
                }
            }
        }
        return sInstance
    }

    companion object {
        var isBluetoothRequestInProgress = false
    }

    fun requestEnableBluetooth() {
        if (!isBluetoothRequestInProgress) {
            isBluetoothRequestInProgress = true
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(context as Activity, enableBluetoothIntent, 1, null)
        }
    }

    fun onBluetoothRequestResult() {
        isBluetoothRequestInProgress = false
    }

    fun openBluetoothSettings() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        context.startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    fun isDeviceConnected(deviceName: String): Boolean {
        val connectedDevices = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET)
        if (connectedDevices == BluetoothProfile.STATE_CONNECTED) {
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
            pairedDevices?.forEach { device ->
                if (device.name == deviceName) {
                    return true
                }
            }
        }
        return false
    }
}