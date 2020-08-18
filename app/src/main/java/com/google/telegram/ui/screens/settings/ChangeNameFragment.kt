package com.google.telegram.ui.screens.settings

import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.ui.screens.base.BaseChangeFragment
import com.google.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_name.*

class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        initFullName()
    }

    private fun initFullName() {
        val fullNameList = USER.fullname.split(" ")
        if (fullNameList.size > 1) {
            settings_input_name.setText(fullNameList[0])
            settings_input_surname.setText(fullNameList[1])
        } else settings_input_name.setText(fullNameList[0])
    }

    override fun change() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()

        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_nameis_empty))
        } else {
            val fullname = "$name $surname"
            setFullnameToDatabase(fullname)

        }
    }
}
