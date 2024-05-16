package com.hyualy.mdp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.util.Util

class LobbyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MDP)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        Util.twoBackToFinish(this)
        settingButtons()
    }

    private fun settingButtons() {
        val btnLogin = findViewById<Button>(R.id.lobby_login)
        btnLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
        val btnRegister = findViewById<Button>(R.id.lobby_register)
        btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }
}