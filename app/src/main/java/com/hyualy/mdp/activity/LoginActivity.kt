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
import com.hyualy.mdp.util.LoginUtil

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

            if (!LoginUtil.lengthChecker(this, id.length, password.length)) { return@setOnClickListener }
            if (id.isNotEmpty() && password.isNotEmpty()) {
                Wifi.sendData("login/$id/$password")
//                Wifi.receiveData (this) { data ->
//                    if (data == "login/yes") {
//                        val intent = Intent(this, ControlActivity::class.java)
//                        startActivity(intent)
//                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
//                        finish()
//                    } else if (data == "login/no") {
//                        Toast.makeText(this, "아이디 혹은 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
            } else {
                Toast.makeText(this, "모든 양식을 채워주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}