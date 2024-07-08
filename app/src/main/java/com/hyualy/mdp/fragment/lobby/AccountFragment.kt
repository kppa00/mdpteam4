package com.hyualy.mdp.fragment.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hyualy.mdp.R
import com.hyualy.mdp.activity.LoginActivity
import com.hyualy.mdp.activity.RegisterActivity
import com.hyualy.mdp.util.Util

class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountSettingButtons(view)
    }

    private fun accountSettingButtons(view: View) {
        val lobbyActivity = view.context

        val btnLogin = view.findViewById<Button>(R.id.lobby_account_login)
        btnLogin.setOnClickListener {
            Util.changeActivity(lobbyActivity, LoginActivity::class.java, false)
        }
        val btnRegister = view.findViewById<Button>(R.id.lobby_account_register)
        btnRegister.setOnClickListener {
            Util.changeActivity(lobbyActivity, RegisterActivity::class.java, false)
        }
    }
}