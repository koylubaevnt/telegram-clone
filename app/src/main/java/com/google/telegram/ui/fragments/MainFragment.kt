package com.google.telegram.ui.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.utilits.APP_ACTIVITY
import com.google.telegram.utilits.hideKeyboard

class MainFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var mRecyclerView: RecyclerView

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Telegram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
    }

}
