package com.hyualy.mdp.util

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

object Util {
    fun twoBackToFinish(context: Context) {
        var backpressedTime: Long = 0
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() > backpressedTime + 2000) {
                    backpressedTime = System.currentTimeMillis()
                    Toast.makeText(context, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
                } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
                    (context as Activity).finishAffinity()
                }
            }
        }
        (context as AppCompatActivity).onBackPressedDispatcher
            .addCallback(context, onBackPressedCallback)
    }

    fun changeActivity(current: Context, target: Class<*>, isFinish: Boolean = true) {
        val intent = Intent(current, target)
        val options = ActivityOptions.makeCustomAnimation(current, 0, 0)
        current.startActivity(intent, options.toBundle())
        if (isFinish) { (current as Activity).finish() }
    }
}