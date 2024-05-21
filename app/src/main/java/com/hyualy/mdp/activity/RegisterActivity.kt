package com.hyualy.mdp.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        settingButton()
    }

    private fun settingButton() {
        val btnRegister = findViewById<Button>(R.id.register_register)
        btnRegister.setOnClickListener {
            TODO("register")
        }
    }
}