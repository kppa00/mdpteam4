package com.example.mdp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val permissionClass = Permission(this).getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bluetoothImg = findViewById<ImageView>(R.id.bluetooth_img)
        bluetoothImg.setOnClickListener {
            permissionClass?.requestBluetoothPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Permission 클래스에 있는 콜백 호출
        permissionClass?.onRequestPermissionsResult(requestCode, grantResults)
    }
}