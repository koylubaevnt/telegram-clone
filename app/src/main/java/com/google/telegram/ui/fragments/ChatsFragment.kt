package com.google.telegram.ui.fragments

import androidx.fragment.app.Fragment
import com.google.telegram.R
import com.google.telegram.utilits.APP_ACTIVITY

class ChatsFragment : Fragment(R.layout.fragment_chat) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
    }

}
