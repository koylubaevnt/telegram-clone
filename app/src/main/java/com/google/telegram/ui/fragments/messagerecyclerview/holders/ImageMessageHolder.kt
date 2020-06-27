package com.google.telegram.ui.fragments.messagerecyclerview.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item_image.view.*

class ImageMessageHolder(view: View): RecyclerView.ViewHolder(view) {

    val blockUserImageMessage: ConstraintLayout = view.block_user_image_message
    val chatUserImageMessage: ImageView = view.chat_user_image_message
    val chatUserImageMessageTime: TextView = view.chat_user_image_message_time

    val blockReceivingImageMessage: ConstraintLayout = view.block_receiving_image_message
    val chatReceivingImageMessage: ImageView = view.chat_receiving_image_message
    val chatReceivingImageMessageTime: TextView = view.chat_receiving_image_message_time


}