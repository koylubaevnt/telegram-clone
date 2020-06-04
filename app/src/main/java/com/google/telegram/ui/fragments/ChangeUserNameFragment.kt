package com.google.telegram.ui.fragments

import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_user_name.*

class ChangeUserNameFragment : BaseChangeFragment(R.layout.fragment_change_user_name) {

    lateinit var mNewUserName: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }

    override fun change() {
        mNewUserName = settings_input_username.text.toString().toLowerCase()
        if (mNewUserName.isEmpty()) {
            showToast("Поле пустое")
        } else {
            REF_DATABASE_ROOT.child(
                NODE_USERNAMES
            )
                .addListenerForSingleValueEvent(AppValueEventListener{
                    if (it.hasChild(mNewUserName)) {
                        showToast("Такой пользователь уже существует")
                    } else {
                        changeUsername(mNewUserName)
                    }
                })
        }
    }
}
