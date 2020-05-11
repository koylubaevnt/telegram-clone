package com.google.telegram.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.telegram.R
import com.google.telegram.utilits.APP_ACTIVITY

/**
 * A simple [Fragment] subclass.
 */
class ContactFragment : BaseFragment(R.layout.fragment_contact) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Контакты"
    }
}
