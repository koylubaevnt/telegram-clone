package com.google.telegram.ui.fragments

import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.google.telegram.MainActivity
import com.google.telegram.R
import com.google.telegram.activities.RegisterActivity
import com.google.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
//                enterCode(string)
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                    .addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            showToast("Добро пожаловать")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else showToast(task2.exception?.message.toString())
                    }
            } else showToast(task.exception?.message.toString())
        }
//private fun enterCode(code: String) {
//val credential = PhoneAuthProvider.getCredential(id, code)
//                AUTH.signInWithCredential(credential)
//            .addOnCompleteListener {task ->
//                if (task.isSuccessful) {
//                    val uid = AUTH.currentUser?.uid.toString()
//                    val dataMap = mutableMapOf<String, Any>()
//                    dataMap[CHILD_ID] = uid
//                    dataMap[CHILD_PHONE] = phoneNumber
//                    dataMap[CHILD_USERNAME] = uid
//
//                    REF_DATABASE_ROOT.child(NODE_USERS)
//                        .child(uid)
//                        .updateChildren(dataMap)
//                        .addOnCompleteListener { task2 ->
//                            if (task2.isSuccessful) {
//                                showToast("Добро пожаловать")
//                                (activity as RegisterActivity).replaceActivity(MainActivity())
//                            } else showToast(task2.exception?.message.toString())
//                        }
//                } else showToast(task.exception?.message.toString())
//            }
    }
}
