package com.hyualy.mdp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.control.OffFragment
import com.hyualy.mdp.util.LoginUtil
import com.hyualy.mdp.util.Util

class ControlActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        val name = LoginUtil.getSharedPrefData(this, "name")
        val nameView = findViewById<TextView>(R.id.control_name)
        nameView.text = "${name}님"

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.control_fragment, OffFragment())
                .commit()
        }

        Util.twoBackToFinish(this)
    }
}