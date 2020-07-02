package com.google.telegram.ui.fragments.messagerecyclerview.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.ui.fragments.messagerecyclerview.views.MessageView
import com.google.telegram.utilits.asTime
import kotlinx.android.synthetic.main.message_item_image.view.*
import kotlinx.android.synthetic.main.message_item_voice.view.*

class VoiceMessageHolder(view: View): RecyclerView.ViewHolder(view) {

    val blockUserVoiceMessage: ConstraintLayout = view.block_user_voice_message
    val chatUserBtnPlayMessage: ImageView = view.chat_user_btn_play
    val chatUserBtnStopMessage: ImageView = view.chat_user_btn_stop
    val chatUserVoiceMessageTime: TextView = view.chat_user_voice_message_time

    val blockReceivingVoiceMessage: ConstraintLayout = view.block_receiving_voice_message
    val chatReceivingBtnPlayMessage: ImageView = view.chat_receiving_btn_play
    val chatReceivingBtnStopMessage: ImageView = view.chat_receiving_btn_stop
    val chatReceivingVoiceMessageTime: TextView = view.chat_receiving_voice_message_time

    fun drawMessageVoice(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blockUserVoiceMessage.visibility = View.VISIBLE
            blockReceivingVoiceMessage.visibility = View.GONE
            chatUserVoiceMessageTime.text =
                view.timestamp.asTime()

        } else {
            blockUserVoiceMessage.visibility = View.GONE
            blockReceivingVoiceMessage.visibility = View.VISIBLE
            chatReceivingVoiceMessageTime.text =
                view.timestamp.asTime()
        }
    }

}