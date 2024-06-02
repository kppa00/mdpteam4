package com.hyualy.mdp.manager

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.hyualy.mdp.R
import com.hyualy.mdp.fragment.AccountFragment
import com.hyualy.mdp.util.BluetoothUtil


class Bluetooth(private val context: Context) : BroadcastReceiver() {

    private val bluetoothAdapter = (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    private val bluetoothUtil = BluetoothUtil(context).getInstance()!!

    fun scanBluetoothDiscovery() {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show()
        } else {
            startBluetoothDiscovery()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startBluetoothDiscovery() {
        if (!bluetoothAdapter.isEnabled) {
            bluetoothUtil.requestEnableBluetooth()
        } else {
            println(bluetoothUtil.getConnectedDevices())
            if (bluetoothUtil.getConnectedDevices() == null) {
                bluetoothUtil.openBluetoothSettings()
            } else {
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.lobby_fragment, AccountFragment())
                    .commit()
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                startBluetoothDiscovery()
                bluetoothUtil.onBluetoothRequestResult()
                Toast.makeText(context, "블루투스 활성화", Toast.LENGTH_SHORT).show()
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setCancelable(false)
                builder.setTitle("서비스 이용 알림")
                builder.setMessage("정상적인 서비스 사용을 위해 블루투스 허용이 필요합니다.\n요청을 반드시 허용해주세요.")
                builder.setPositiveButton("블루투스 재요청") { _, _ ->
                    bluetoothUtil.requestEnableBluetooth()
                    bluetoothUtil.onBluetoothRequestResult()
                }
                builder.setNegativeButton("닫기") { _, _ ->
                    bluetoothUtil.onBluetoothRequestResult()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
            if (state == BluetoothAdapter.STATE_ON) {
                startBluetoothDiscovery()
            }
        }
    }
}