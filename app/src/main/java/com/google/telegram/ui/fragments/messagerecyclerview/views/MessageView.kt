package com.google.telegram.ui.fragments.messagerecyclerview.views

interface MessageView {

    val id: String
    val from: String
    val timestamp: String
    val fileUrl: String
    val text: String

    companion object {
        val MESSAGE_IMAGE: Int
            get() = 0
        val MESSAGE_TEXT: Int
            get() = 1
        val MESSAGE_VOICE: Int
            get() = 2
    }

    fun getTypeVIew(): Int
}