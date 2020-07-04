package com.google.telegram.ui.messagerecyclerview.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.ui.messagerecyclerview.views.MessageView
import com.google.telegram.utilits.asTime
import com.google.telegram.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.message_item_image.view.*

class ImageMessageHolder(view: View): RecyclerView.ViewHolder(view), MessageHolder {

    private val blockUserImageMessage: ConstraintLayout = view.block_user_image_message
    private val chatUserImageMessage: ImageView = view.chat_user_image_message
    private val chatUserImageMessageTime: TextView = view.chat_user_image_message_time

    private val blockReceivingImageMessage: ConstraintLayout = view.block_receiving_image_message
    private val chatReceivingImageMessage: ImageView = view.chat_receiving_image_message
    private val chatReceivingImageMessageTime: TextView = view.chat_receiving_image_message_time

    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blockUserImageMessage.visibility = View.VISIBLE
            blockReceivingImageMessage.visibility = View.GONE
            chatUserImageMessage.downloadAndSetImage(view.fileUrl)
            chatUserImageMessageTime.text =
                view.timestamp.asTime()
        } else {
            blockUserImageMessage.visibility = View.GONE
            blockReceivingImageMessage.visibility = View.VISIBLE
            chatReceivingImageMessage.downloadAndSetImage(view.fileUrl)
            chatReceivingImageMessageTime.text =
                view.timestamp.asTime()
        }
    }

    override fun onAttach(view: MessageView) {
    }

    override fun onDetach() {
    }

}