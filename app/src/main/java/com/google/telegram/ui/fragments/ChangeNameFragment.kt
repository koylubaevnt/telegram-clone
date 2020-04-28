package com.google.telegram.ui.fragments

import com.google.telegram.R
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
            REF_DATABASE_ROOT
                .child(NODE_USERS)
                .child(CURRENT_UID)
                .child(CHILD_FULL_NAME)
                .setValue(fullname)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_updated))
                        USER.fullname = fullname
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                        fragmentManager?.popBackStack()
                    }
                }
        }

    }
}
