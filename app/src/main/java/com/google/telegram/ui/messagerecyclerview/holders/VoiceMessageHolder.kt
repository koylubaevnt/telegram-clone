package com.google.telegram.ui.messagerecyclerview.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.ui.messagerecyclerview.views.MessageView
import com.google.telegram.utilits.AppVoicePlayer
import com.google.telegram.utilits.asTime
import kotlinx.android.synthetic.main.message_item_voice.view.*

class VoiceMessageHolder(view: View): RecyclerView.ViewHolder(view), MessageHolder {

    private val mAppVoicePlayer = AppVoicePlayer()

    private val blockUserVoiceMessage: ConstraintLayout = view.block_user_voice_message
    private val chatUserBtnPlayMessage: ImageView = view.chat_user_btn_play
    private val chatUserBtnStopMessage: ImageView = view.chat_user_btn_stop
    private val chatUserVoiceMessageTime: TextView = view.chat_user_voice_message_time

    private val blockReceivingVoiceMessage: ConstraintLayout = view.block_receiving_voice_message
    private val chatReceivingBtnPlayMessage: ImageView = view.chat_receiving_btn_play
    private val chatReceivingBtnStopMessage: ImageView = view.chat_receiving_btn_stop
    private val chatReceivingVoiceMessageTime: TextView = view.chat_receiving_voice_message_time

    override fun drawMessage(view: MessageView) {
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

    override fun onAttach(view: MessageView) {
        mAppVoicePlayer.init()
        if (view.from == CURRENT_UID) {
            chatUserBtnPlayMessage.setOnClickListener {
                chatUserBtnPlayMessage.visibility = View.GONE
                chatUserBtnStopMessage.visibility = View.VISIBLE
                chatUserBtnStopMessage.setOnClickListener {
                    stop {
                        chatUserBtnStopMessage.setOnClickListener(null)
                        chatUserBtnPlayMessage.visibility = View.VISIBLE
                        chatUserBtnStopMessage.visibility = View.GONE
                    }
                }
                play(view) {
                    chatUserBtnStopMessage.setOnClickListener(null)
                    chatUserBtnPlayMessage.visibility = View.VISIBLE
                    chatUserBtnStopMessage.visibility = View.GONE
                }
            }
        } else {
            chatReceivingBtnPlayMessage.setOnClickListener {
                chatReceivingBtnPlayMessage.visibility = View.GONE
                chatReceivingBtnStopMessage.visibility = View.VISIBLE
                chatReceivingBtnStopMessage.setOnClickListener {
                    stop {
                        chatReceivingBtnStopMessage.setOnClickListener(null)
                        chatReceivingBtnPlayMessage.visibility = View.VISIBLE
                        chatReceivingBtnStopMessage.visibility = View.GONE
                    }
                }
                play(view) {
                    chatReceivingBtnStopMessage.setOnClickListener(null)
                    chatReceivingBtnPlayMessage.visibility = View.VISIBLE
                    chatReceivingBtnStopMessage.visibility = View.GONE
                }
            }
        }
    }

    private fun stop(function: () -> Unit) {
        mAppVoicePlayer.stop() {
            function()
        }
    }

    private fun play(
        view: MessageView,
        function: () -> Unit
    ) {
        mAppVoicePlayer.play(view.id, view.fileUrl) {
            function()
        }
    }

    override fun onDetach() {
        chatUserBtnPlayMessage.setOnClickListener(null)
        chatReceivingBtnPlayMessage.setOnClickListener(null)
        mAppVoicePlayer.release()
    }

}