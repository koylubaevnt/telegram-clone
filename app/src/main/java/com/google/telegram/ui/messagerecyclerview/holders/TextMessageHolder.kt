package com.google.telegram.ui.messagerecyclerview.holders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.ui.messagerecyclerview.views.MessageView
import com.google.telegram.utilits.asTime
import kotlinx.android.synthetic.main.message_item_text.view.*

class TextMessageHolder(view: View): RecyclerView.ViewHolder(view), MessageHolder {

    private val blockUserMessage: ConstraintLayout = view.block_user_message
    private val chatUserMessage: TextView = view.chat_user_message
    private val chatUserMessageTime: TextView = view.chat_user_message_time

    private val blockReceivingMessage: ConstraintLayout = view.block_receiving_message
    private val chatReceivingMessage: TextView = view.chat_receiving_message
    private val chatReceivingMessageTime: TextView = view.chat_receiving_message_time

    override fun drawMessage(view: MessageView) {
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

    override fun onAttach(view: MessageView) {
    }

    override fun onDetach() {
    }

}