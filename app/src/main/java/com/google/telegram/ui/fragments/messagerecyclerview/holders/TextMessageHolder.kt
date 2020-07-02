package com.google.telegram.ui.fragments.messagerecyclerview.holders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.ui.fragments.messagerecyclerview.views.MessageView
import com.google.telegram.utilits.asTime
import kotlinx.android.synthetic.main.message_item_text.view.*

class TextMessageHolder(view: View): RecyclerView.ViewHolder(view) {

    val blockUserMessage: ConstraintLayout = view.block_user_message
    val chatUserMessage: TextView = view.chat_user_message
    val chatUserMessageTime: TextView = view.chat_user_message_time

    val blockReceivingMessage: ConstraintLayout = view.block_receiving_message
    val chatReceivingMessage: TextView = view.chat_receiving_message
    val chatReceivingMessageTime: TextView = view.chat_receiving_message_time

    fun drawMessageText(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blockUserMessage.visibility = View.VISIBLE
            blockReceivingMessage.visibility = View.GONE
            chatUserMessage.text = view.text
            chatUserMessageTime.text =
                view.timestamp.asTime()
        } else {
            blockUserMessage.visibility = View.GONE
            blockReceivingMessage.visibility = View.VISIBLE
            chatReceivingMessage.text = view.text
            chatReceivingMessageTime.text =
                view.timestamp.toString().asTime()
        }
    }

}