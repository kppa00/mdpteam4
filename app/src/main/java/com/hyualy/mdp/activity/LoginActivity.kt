package com.hyualy.mdp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.manager.Wifi
import com.hyualy.mdp.util.LoginUtil
import com.hyualy.mdp.util.Util

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        settingButtons()
    }

    private fun settingButtons() {
        val btnRegister = findViewById<TextView>(R.id.login_register)
        btnRegister.setOnClickListener {
            Util.changeActivity(this, RegisterActivity::class.java)
        }
        val btnLogin = findViewById<Button>(R.id.login_login)
        btnLogin.setOnClickListener {
            val id = findViewById<EditText>(R.id.login_id).text.toString()
            val password = findViewById<EditText>(R.id.login_password).text.toString()

            if (!LoginUtil.lengthChecker(this, id.length, password.length)) { return@setOnClickListener }
            if (id.isNotEmpty() && password.isNotEmpty()) {
                Wifi.sendData("login/$id/$password")
                Wifi.receiveData (this) { data ->
                    if ("login/yes" in data) {
                        val name = data.replace("login/yes/", "")
                        LoginUtil.addSharedPrefData(this, "id", id)
                        LoginUtil.addSharedPrefData(this, "password", password)
                        LoginUtil.addSharedPrefData(this, "name", name)

                        Util.changeActivity(this, ControlActivity::class.java)
                        Toast.makeText(this, "${name}님 환영합니다.", Toast.LENGTH_SHORT).show()
                    } else if (data == "login/no") {
                        Toast.makeText(this, "아이디 혹은 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "모든 양식을 채워주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}