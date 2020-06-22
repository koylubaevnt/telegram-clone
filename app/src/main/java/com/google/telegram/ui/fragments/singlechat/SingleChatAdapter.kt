package com.google.telegram.ui.fragments.singlechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.models.CommonModel
import com.google.telegram.utilits.TYPE_MESSAGE_IMAGE
import com.google.telegram.utilits.TYPE_MESSAGE_TEXT
import com.google.telegram.utilits.asTime
import com.google.telegram.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mListMessagesCache = mutableListOf<CommonModel>()

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Text
        val blockUserMessage: ConstraintLayout = view.block_user_message
        val chatUserMessage: TextView = view.chat_user_message
        val chatUserMessageTime: TextView = view.chat_user_message_time

        val blockReceivingMessage: ConstraintLayout = view.block_receiving_message
        val chatReceivingMessage: TextView = view.chat_receiving_message
        val chatReceivingMessageTime: TextView = view.chat_receiving_message_time

        // Image
        val blockUserImageMessage: ConstraintLayout = view.block_user_image_message
        val chatUserImageMessage: ImageView = view.chat_user_image_message
        val chatUserImageMessageTime: TextView = view.chat_user_image_message_time

        val blockReceivingImageMessage: ConstraintLayout = view.block_receiving_image_message
        val chatReceivingImageMessage: ImageView = view.chat_receiving_image_message
        val chatReceivingImageMessageTime: TextView = view.chat_receiving_image_message_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = mListMessagesCache.size

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        when(mListMessagesCache[position].type) {
            TYPE_MESSAGE_TEXT -> drawMessageText(holder, position)
            TYPE_MESSAGE_IMAGE -> drawMessageImage(holder, position)
        }

    }

    private fun drawMessageImage(holder: SingleChatHolder, position: Int) {
        holder.blockUserMessage.visibility = View.GONE
        holder.blockReceivingMessage.visibility = View.GONE

        if (mListMessagesCache[position].from == CURRENT_UID) {
            holder.blockUserImageMessage.visibility = View.VISIBLE
            holder.blockReceivingImageMessage.visibility = View.GONE
            holder.chatUserImageMessage.downloadAndSetImage(mListMessagesCache[position].fileUrl)
            holder.chatUserImageMessageTime.text = mListMessagesCache[position].timestamp.toString().asTime()
        } else {
            holder.blockUserImageMessage.visibility = View.GONE
            holder.blockReceivingImageMessage.visibility = View.VISIBLE
            holder.chatReceivingImageMessage.downloadAndSetImage(mListMessagesCache[position].fileUrl)
            holder.chatReceivingImageMessageTime.text = mListMessagesCache[position].timestamp.toString().asTime()
        }
    }

    private fun drawMessageText(holder: SingleChatHolder, position: Int) {
        holder.blockUserImageMessage.visibility = View.GONE
        holder.blockReceivingImageMessage.visibility = View.GONE

        if (mListMessagesCache[position].from == CURRENT_UID) {
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivingMessage.visibility = View.GONE
            holder.chatUserMessage.text = mListMessagesCache[position].text
            holder.chatUserMessageTime.text = mListMessagesCache[position].timestamp.toString().asTime()
        } else {
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivingMessage.visibility = View.VISIBLE
            holder.chatReceivingMessage.text = mListMessagesCache[position].text
            holder.chatReceivingMessageTime.text = mListMessagesCache[position].timestamp.toString().asTime()
        }
    }

    fun addItemToBottom(item: CommonModel, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            notifyItemInserted(mListMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: CommonModel, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            mListMessagesCache.sortBy { it.timestamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }
}
