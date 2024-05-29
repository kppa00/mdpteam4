package com.hyualy.mdp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.util.BluetoothUtil

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        settingButtons()
    }

    private fun settingButtons() {
        val btnRegister = findViewById<TextView>(R.id.login_register)
        btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
            finish()
        }
        val btnLogin = findViewById<Button>(R.id.login_login)
        btnLogin.setOnClickListener {
            val bluetoothUtil = BluetoothUtil(this).getInstance()!!
//            val device = bluetoothUtil.getDeviceConnected("pi")!!

            val id = findViewById<EditText>(R.id.login_id).text
            val password = findViewById<EditText>(R.id.login_password).text
            if (id.isNotEmpty() && password.isNotEmpty()) {
//                sendData(device, "Login/$editTextID/$editTextPW")
//                receiveBluetoothData(device) { data ->
//                    TODO("리턴값 로그인 true/false 처리")
//                    Log.d("BluetoothData", "Received data: $data")
//                }
            } else {
                Toast.makeText(this, "모든 양식을 채워주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}