package com.example.mdp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
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

    fun requestBluetoothPermission() {
        if (checkSelfPermission(
                context,
                android.Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(
                context,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                context as Activity,
                arrayOf(
                    android.Manifest.permission.BLUETOOTH_SCAN,
                    android.Manifest.permission.BLUETOOTH_CONNECT,
                ), 1
            )
            return
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                println("grant")
            } else {
                explainBluetoothPermissionRationale()
            }
        }
    }

    private var deniedCount = 0

    private fun explainBluetoothPermissionRationale() {
        println(deniedCount.toString())
        val builder = AlertDialog.Builder(context)
        builder.setTitle("서비스 이용 알림")
        if (deniedCount < 1) {
            deniedCount++
            builder.setMessage("정상적인 서비스 사용을 위한 필수 권한입니다.\n권한 요청을 반드시 허용해주세요.")
            builder.setPositiveButton("권한 재요청") { _, _ ->
                requestBluetoothPermission()
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