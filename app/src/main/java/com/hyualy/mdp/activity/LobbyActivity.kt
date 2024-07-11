package com.hyualy.mdp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.lobby.AccountFragment
import com.hyualy.mdp.fragment.lobby.StartFragment
import com.hyualy.mdp.manager.Permission
import com.hyualy.mdp.util.LoginUtil
import com.hyualy.mdp.util.Util

class LobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        if (LoginUtil.getSharedPrefData(this, "logout") == "true") {
            LoginUtil.removeSharedPrefData(this, "logout")
            supportFragmentManager.beginTransaction()
                .replace(R.id.lobby_fragment, AccountFragment())
                .commit()
        } else if (savedInstanceState == null) {
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