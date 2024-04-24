package com.example.mdp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bluetoothImg = findViewById<ImageView>(R.id.bluetooth_img)
        bluetoothImg.setOnClickListener {
            Permission(this).getInstance(this)?.requestBluetoothPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Permission 클래스에 있는 콜백 호출
        Permission(this).getInstance(this)?.onRequestPermissionsResult(requestCode, grantResults)
    }
}