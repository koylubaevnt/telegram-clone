package com.google.telegram.ui.fragments

import com.google.telegram.R
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
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener{
                    if (it.hasChild(mNewUserName)) {
                        showToast("Такой пользователь уже существует")
                    } else {
                        changeUsername()
                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT
            .child(NODE_USERNAMES)
            .child(mNewUserName)
            .setValue(CURRENT_UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT
            .child(NODE_USERS)
            .child(CURRENT_UID)
            .child(CHILD_USERNAME)
            .setValue(mNewUserName)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    deleteOldUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES)
            .child(USER.username)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_updated))
                    USER.username = mNewUserName
                    fragmentManager?.popBackStack()
                }  else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}
