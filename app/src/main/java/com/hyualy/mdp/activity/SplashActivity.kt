package com.hyualy.mdp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.manager.Wifi
import com.hyualy.mdp.util.LoginUtil
import com.hyualy.mdp.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MDP)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Util.twoBackToFinish(this)

        var isReceived = false

        val id = LoginUtil.getSharedPrefData(this, "id")
        val password = LoginUtil.getSharedPrefData(this, "password")
        val name = LoginUtil.getSharedPrefData(this, "name")

        val sendTime = System.currentTimeMillis()
        Wifi.sendData("login/$id/$password")
        Wifi.receiveData (this) { data ->
            if ("login/yes" in data) {
                val receiveTime = System.currentTimeMillis()
                val elapsedTime = receiveTime - sendTime
                println(elapsedTime)

                isReceived = true
                Util.changeActivity(this, ControlActivity::class.java)
                Toast.makeText(this, "${name}님 환영합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        coroutineScope.launch {
            delay(750)
            if (!isReceived) {
                Util.changeActivity(this@SplashActivity, LobbyActivity::class.java)
            }
        }
    }
}