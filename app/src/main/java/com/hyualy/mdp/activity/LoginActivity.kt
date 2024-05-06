package com.hyualy.mdp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.util.Util

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MDP)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Util.twoBackToFinish(this)
    }
}