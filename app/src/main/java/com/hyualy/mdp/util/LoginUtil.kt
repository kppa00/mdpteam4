package com.hyualy.mdp.util

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

object LoginUtil {

    fun lengthChecker(context: Context, idLength: Int, pwLength: Int, nameLength: Int = 0, emailLength: Int = 0) : Boolean {
        if (idLength !in 3..8) {
            Toast.makeText(context, "아이디는 3~8자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pwLength !in 4..16) {
            Toast.makeText(context, "비밀번호는 4~16자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (nameLength > 50) {
            Toast.makeText(context, "이름은 50자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (emailLength > 50) {
            Toast.makeText(context, "이메일은 50자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun addSharedPrefData(context: Context, key: String, value: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value).apply()
    }

    fun removeSharedPrefData(context: Context, key: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove(key).apply()
    }

    fun getSharedPrefData(context: Context, key: String): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE)
        return sharedPref.getString(key, null)
    }
}