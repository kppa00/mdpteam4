package com.hyualy.mdp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.AccountFragment
import com.hyualy.mdp.fragment.StartFragment
import com.hyualy.mdp.util.Util

class LobbyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MDP)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.lobby_fragment, StartFragment())
                .commit()
        }

        Util.twoBackToFinish(this)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        startSettingButtons()
        return super.onCreateView(name, context, attrs)
    }

    private fun startSettingButtons() {
        val btnRegister = findViewById<TextView>(R.id.lobby_account_start)
        btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
            finish()
        }
//        replaceFragment(PermissionFragment())
//        permissionSettingButtons()
    }

    private fun permissionSettingButtons() {
        replaceFragment(AccountFragment())
        accountSettingButtons()
    }

    private fun accountSettingButtons() {
        val btnLogin = findViewById<Button>(R.id.lobby_account_login)
        btnLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }
        val btnRegister = findViewById<Button>(R.id.lobby_account_register)
        btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.lobby_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}