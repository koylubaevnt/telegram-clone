package com.google.telegram.utilits

import com.google.telegram.database.*

enum class AppStates(val state: String) {

    ONLINE("в сети"),
    OFFLINE("был недавно"),
    TYPING("печатает");

    companion object {
        fun updateState(appStates: AppStates) {
            if (AUTH.currentUser != null) {
                REF_DATABASE_ROOT.child(
                    NODE_USERS
                )
                    .child(CURRENT_UID)
                    .child(CHILD_STATE)
                    .setValue(appStates)
                    .addOnSuccessListener {
                        USER.state = appStates.state
                    }
                    .addOnFailureListener {
                        showToast(it.message.toString())
                    }
            }
        }
    }
}