package com.google.telegram.utilits

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class AppChildEventListener (val onSuccess: (DataSnapshot) -> Unit) : ChildEventListener {
    override fun onCancelled(dataSnapshot: DatabaseError) {
        //TODO("Not yet implemented")
    }

    override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {
        //TODO("Not yet implemented")
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
        //TODO("Not yet implemented")
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
        onSuccess(dataSnapshot)
    }

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        //TODO("Not yet implemented")
    }

}