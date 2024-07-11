package com.hyualy.mdp.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.control.OffFragment
import com.hyualy.mdp.fragment.control.OnFragment
import com.hyualy.mdp.manager.Wifi
import com.hyualy.mdp.util.LoginUtil
import com.hyualy.mdp.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ControlActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        val name = LoginUtil.getSharedPrefData(this, "name")
        val nameView = findViewById<TextView>(R.id.control_name)
        nameView.text = "${name}님"

        if (savedInstanceState == null) {
            switchControlBox(this, false)
        }
        settingButtons()

        Util.twoBackToFinish(this)
    }

    private fun settingButtons() {
        val fragment = findViewById<FrameLayout>(R.id.control_fragment)
        fragment.setOnClickListener {
            switchControlBox(this, true)
        }

        val logout = findViewById<TextView>(R.id.control_logout)
        logout.setOnClickListener {
            logout()
        }
    }


    private fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("정말 로그아웃 하시겠습니까?")

        builder.setPositiveButton("예") { _, _ ->
            LoginUtil.addSharedPrefData(this, "logout", "true")
            val list = listOf("id", "password", "name")
            list.forEach {
                LoginUtil.removeSharedPrefData(this, it)
            }
            Util.changeActivity(this, LobbyActivity::class.java)
        }
        builder.setNegativeButton("아니오") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }

    private var systemFlag: Boolean? = null
    private var bottleJobFlag = false

    private fun switchControlFragment(activity: AppCompatActivity) {
        if (systemFlag == true) {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.control_fragment, OnFragment())
                .commit()
        } else {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.control_fragment, OffFragment())
                .commit()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getBottleInfo() {
        Wifi.sendData("bottle/getCount")
        Wifi.receiveData(this) { data ->
            val splitData = data.split("/").toMutableList()
            if (splitData[0].toInt() > 999) { splitData[0] = 999.toString() }
            findViewById<TextView>(R.id.control_good)
                .text = "양품 개수 : ${splitData[0]}"
            if (splitData[1].toInt() > 999) { splitData[1] = 999.toString() }
            findViewById<TextView>(R.id.control_defect)
                .text = "불량품 개수 : ${splitData[1]}"
        }
    }

    private fun switchControlBox(activity: AppCompatActivity, switching: Boolean) {
        if (switching) {
            Wifi.sendData("control/switching")
            systemFlag = !systemFlag!!
            switchControlFragment(activity)
        } else {
            Wifi.sendData("control/isRunning")
            Wifi.receiveData(activity) { data ->
                if (data == "control/yes") {
                    systemFlag = true
                } else if (data == "control/no") {
                    systemFlag = false
                }
                switchControlFragment(activity)
                getBottleInfo()
            }
        }
        if (bottleJobFlag) { return }
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            bottleJobFlag = true
            delay(1000)
            while (systemFlag == true) {
                getBottleInfo()
                delay(1000)
            }
            bottleJobFlag = false
        }
    }
}