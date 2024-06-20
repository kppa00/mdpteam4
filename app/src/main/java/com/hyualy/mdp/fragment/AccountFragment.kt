package com.hyualy.mdp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hyualy.mdp.R
import com.hyualy.mdp.activity.LoginActivity
import com.hyualy.mdp.activity.RegisterActivity

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
            val intent = Intent(lobbyActivity, LoginActivity::class.java)
            lobbyActivity.startActivity(intent)
        }
        val btnRegister = view.findViewById<Button>(R.id.lobby_account_register)
        btnRegister.setOnClickListener {
            val intent = Intent(lobbyActivity, RegisterActivity::class.java)
            lobbyActivity.startActivity(intent)
        }
    }
}