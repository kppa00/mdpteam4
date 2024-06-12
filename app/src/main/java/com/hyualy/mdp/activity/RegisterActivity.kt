package com.hyualy.mdp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.manager.Wifi

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        settingButton()
    }

    private fun settingButton() {
        val btnRegister = findViewById<Button>(R.id.register_register)
        btnRegister.setOnClickListener {

            val id = findViewById<EditText>(R.id.register_id).text
            val password = findViewById<EditText>(R.id.register_password).text
            val checkPassword = findViewById<EditText>(R.id.register_check_password).text
            val name = findViewById<EditText>(R.id.register_name).text
            val email = findViewById<EditText>(R.id.register_email).text

            if (listOf(id, password, checkPassword, name, email).all { it.isNotEmpty() }) {
                if (password == checkPassword) {
                    Wifi.sendData("192.168.198.75", "register/$id/$password/$name/$email")
//                    Wifi.receiveData { data ->
//                        Log.d("WifiData", "Received data: $data")
//                    }
                } else {
                    Toast.makeText(this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 양식을 채워주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}