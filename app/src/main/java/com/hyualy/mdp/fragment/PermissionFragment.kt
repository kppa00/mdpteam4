package com.hyualy.mdp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hyualy.mdp.R
import com.hyualy.mdp.activity.LobbyActivity
import com.hyualy.mdp.manager.Permission

class PermissionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionSettingButtons(view)
    }

    private fun permissionSettingButtons(view: View) {
        val btnStart = view.findViewById<Button>(R.id.lobby_start_start)
        btnStart.setOnClickListener {
            val activity = activity as? LobbyActivity
            if (activity != null) {
                Permission(activity).requestPermission()
            }
        }
    }
}