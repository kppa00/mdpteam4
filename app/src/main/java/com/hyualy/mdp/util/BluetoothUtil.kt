package com.hyualy.mdp.util

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.app.ActivityCompat

class BluetoothUtil(private val context: Context) {

    private val bluetoothManager = (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
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
        private var isBluetoothRequestInProgress = false
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

    fun getConnectedDevices(): List<BluetoothDevice> {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val connectedDevices = mutableListOf<BluetoothDevice>()

            if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
                val profiles = listOf(BluetoothProfile.HEADSET, BluetoothProfile.SAP,
                    BluetoothProfile.LE_AUDIO, BluetoothProfile.STATE_CONNECTED,
                    BluetoothProfile.HID_DEVICE, BluetoothProfile.A2DP,
                    BluetoothProfile.CSIP_SET_COORDINATOR, BluetoothProfile.GATT,
                    BluetoothProfile.HEARING_AID, BluetoothProfile.STATE_DISCONNECTED,
                    BluetoothProfile.STATE_DISCONNECTING, BluetoothProfile.STATE_CONNECTING)

                for (profile in profiles) {
                    bluetoothAdapter.getProfileConnectionState(profile)
//                    val devices = bluetoothManager.getConnectedDevices(profile)
//                    connectedDevices.addAll(devices)
                    println(bluetoothAdapter.getProfileConnectionState(profile).toString())
                }
            }
            return connectedDevices
        } else {
            println("permission error")
        }
        return listOf()
    }
}