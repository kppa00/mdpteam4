package com.hyualy.mdp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R

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
            TODO("Login")
        }
    }
}