package com.hyualy.mdp.manager

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import kotlin.concurrent.Volatile

class Permission(private val context: Context) {

    @Volatile
    private var sInstance: Permission? = null
    fun getInstance(): Permission? {
        if (sInstance == null) {
            synchronized(Permission::class.java) {
                if (sInstance == null) {
                    sInstance = Permission(context)
                }
            }
        }
        return sInstance
    }

    fun requestPermission() {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.CHANGE_WIFI_STATE
        )
        var isPermissionsGranted = true
        permissions.forEach {
            if (checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED) {
                isPermissionsGranted = false
            }
        }
        if (!isPermissionsGranted) {
            requestPermissions(
                context as Activity,
                permissions,
                0
            )
        } else {
            Wifi.requestEnableWifi(context)
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray
    ) {
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Wifi.requestEnableWifi(context)
            } else {
                explainPermissionRationale()
            }
        }
    }

    private fun explainPermissionRationale() {
        val sharedPref: SharedPreferences = context.getSharedPreferences("permission", Context.MODE_PRIVATE)
        val deniedCount = sharedPref.getInt("wifi", Context.MODE_PRIVATE)
        println(deniedCount.toString())
        val builder = AlertDialog.Builder(context)
        builder.setTitle("서비스 이용 알림")
        if (deniedCount < 1) {
            sharedPref.edit().putInt("wifi", deniedCount + 1).apply()
            builder.setMessage("정상적인 서비스 사용을 위한 필수 권한입니다.\n권한 요청을 반드시 허용해주세요.")
            builder.setPositiveButton("권한 재요청") { _, _ ->
                requestPermission()
            }
        } else {
            builder.setMessage("정상적인 서비스 사용을 위한 필수 권한입니다.\n권한 허용을 위해 설정화면으로 이동합니다.")
            builder.setPositiveButton("설정") { _, _ ->
                openAppSettings()
            }
        }
        builder.setNegativeButton("닫기") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}