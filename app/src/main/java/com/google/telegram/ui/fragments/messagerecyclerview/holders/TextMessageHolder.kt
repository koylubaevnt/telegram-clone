package com.google.telegram.ui.fragments.messagerecyclerview.holders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item_text.view.*

class TextMessageHolder(view: View): RecyclerView.ViewHolder(view) {

    val blockUserMessage: ConstraintLayout = view.block_user_message
    val chatUserMessage: TextView = view.chat_user_message
    val chatUserMessageTime: TextView = view.chat_user_message_time

    val blockReceivingMessage: ConstraintLayout = view.block_receiving_message
    val chatReceivingMessage: TextView = view.chat_receiving_message
    val chatReceivingMessageTime: TextView = view.chat_receiving_message_time

}