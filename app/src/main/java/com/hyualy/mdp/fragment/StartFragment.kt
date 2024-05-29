package com.hyualy.mdp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hyualy.mdp.R

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startSettingButtons(view)
    }

    private fun startSettingButtons(view: View) {
        val btnStart = view.findViewById<Button>(R.id.lobby_start_start)
        btnStart.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.lobby_fragment, PermissionFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}