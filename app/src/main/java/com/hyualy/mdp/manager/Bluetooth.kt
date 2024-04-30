package com.hyualy.mdp.manager

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult


class Bluetooth(private val context: Context) {

    private val bluetoothAdapter = (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    fun scanBluetoothDiscovery() {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
        } else {
            if (!bluetoothAdapter.isEnabled) {
                println("clicked")
                requestEnableBluetooth()
            } else {
                startBluetoothDiscovery()
            }
        }
    }

    private fun openBluetoothSettings() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        context.startActivity(intent)
    }

    private fun startBluetoothDiscovery() {
        openBluetoothSettings()
    }

    private fun requestEnableBluetooth() {
        val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(context as Activity, enableBluetoothIntent, 1, null)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                startBluetoothDiscovery()
                Toast.makeText(context, "블루투스 활성화", Toast.LENGTH_SHORT).show()
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("서비스 이용 알림")
                builder.setMessage("정상적인 서비스 사용을 위해 블루투스 허용이 필요합니다.\n요청을 반드시 허용해주세요.")
                builder.setPositiveButton("블루투스 재요청") { _, _ ->
                    requestEnableBluetooth()
                }
                builder.setNegativeButton("닫기") { _, _ -> }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}