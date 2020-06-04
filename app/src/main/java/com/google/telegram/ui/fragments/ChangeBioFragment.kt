package com.google.telegram.ui.fragments

import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        val mNewBio = settings_input_bio.text.toString()
        setBioToDatabase(mNewBio)
    }
}
