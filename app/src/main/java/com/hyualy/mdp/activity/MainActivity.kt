package com.hyualy.mdp.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.manager.Bluetooth
import com.hyualy.mdp.manager.Permission

class MainActivity : AppCompatActivity() {

    private val permissionClass = Permission(this).getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bluetoothImg = findViewById<ImageView>(R.id.bluetooth_img)
        bluetoothImg.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                permissionClass?.requestBluetoothPermission()
            } else {
                Bluetooth(this).scanBluetoothDiscovery()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionClass?.onRequestPermissionsResult(requestCode, grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Bluetooth(this).onActivityResult(requestCode, resultCode)
    }
}