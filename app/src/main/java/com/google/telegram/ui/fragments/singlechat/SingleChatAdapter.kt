package com.google.telegram.ui.fragments.singlechat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.ui.fragments.messagerecyclerview.holders.AppHolderFactory
import com.google.telegram.ui.fragments.messagerecyclerview.holders.ImageMessageHolder
import com.google.telegram.ui.fragments.messagerecyclerview.holders.TextMessageHolder
import com.google.telegram.ui.fragments.messagerecyclerview.views.MessageView
import com.google.telegram.utilits.asTime
import com.google.telegram.utilits.downloadAndSetImage

class SingleChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListMessagesCache = mutableListOf<MessageView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return mListMessagesCache[position].getTypeVIew()
    }

    override fun getItemCount(): Int = mListMessagesCache.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageMessageHolder -> drawMessageImage(holder, position)
            is TextMessageHolder -> drawMessageText(holder, position)
            else -> {
            }
        }
    }

    private fun drawMessageImage(holder: ImageMessageHolder, position: Int) {
        if (mListMessagesCache[position].from == CURRENT_UID) {
            holder.blockUserImageMessage.visibility = View.VISIBLE
            holder.blockReceivingImageMessage.visibility = View.GONE
            holder.chatUserImageMessage.downloadAndSetImage(mListMessagesCache[position].fileUrl)
            holder.chatUserImageMessageTime.text =
                mListMessagesCache[position].timestamp.asTime()
        } else {
            holder.blockUserImageMessage.visibility = View.GONE
            holder.blockReceivingImageMessage.visibility = View.VISIBLE
            holder.chatReceivingImageMessage.downloadAndSetImage(mListMessagesCache[position].fileUrl)
            holder.chatReceivingImageMessageTime.text =
                mListMessagesCache[position].timestamp.asTime()
        }
    }

    private fun drawMessageText(holder: TextMessageHolder, position: Int) {
        if (mListMessagesCache[position].from == CURRENT_UID) {
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivingMessage.visibility = View.GONE
            holder.chatUserMessage.text = mListMessagesCache[position].text
            holder.chatUserMessageTime.text =
                mListMessagesCache[position].timestamp.asTime()
        } else {
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivingMessage.visibility = View.VISIBLE
            holder.chatReceivingMessage.text = mListMessagesCache[position].text
            holder.chatReceivingMessageTime.text =
                mListMessagesCache[position].timestamp.toString().asTime()
        }
    }

    fun addItemToBottom(item: MessageView, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            notifyItemInserted(mListMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: MessageView, onSuccess: () -> Unit) {
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            mListMessagesCache.sortBy { it.timestamp }
            notifyItemInserted(0)
        }
        onSuccess()
    }
}