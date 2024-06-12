package com.hyualy.mdp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.StartFragment
import com.hyualy.mdp.manager.Permission
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

    private val permissionClass = Permission(this).getInstance()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionClass?.onRequestPermissionsResult(requestCode, grantResults)
    }
}