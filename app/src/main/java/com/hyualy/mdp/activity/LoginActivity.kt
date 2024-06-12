package com.hyualy.mdp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.manager.Wifi

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
            val id = findViewById<EditText>(R.id.login_id).text
            val password = findViewById<EditText>(R.id.login_password).text

            if (id.isNotEmpty() && password.isNotEmpty()) {
//                val connectivityManager: ConnectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                Wifi.sendData("192.168.198.75", "login/$id/$password")
//                Wifi.receiveData { data ->
//                    Log.d("WifiData", "Received data: $data")
//                }
            } else {
                Toast.makeText(this, "모든 양식을 채워주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}