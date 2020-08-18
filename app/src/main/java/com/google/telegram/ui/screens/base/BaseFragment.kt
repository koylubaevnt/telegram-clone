package com.google.telegram.ui.screens.base

import androidx.fragment.app.Fragment
import com.google.telegram.utilits.APP_ACTIVITY

open class BaseFragment(private val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mAppDrawer.disableDrawer()
    }
}
