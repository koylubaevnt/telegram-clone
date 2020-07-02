package com.google.telegram.ui.fragments.singlechat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.ui.fragments.messagerecyclerview.holders.AppHolderFactory
import com.google.telegram.ui.fragments.messagerecyclerview.holders.ImageMessageHolder
import com.google.telegram.ui.fragments.messagerecyclerview.holders.TextMessageHolder
import com.google.telegram.ui.fragments.messagerecyclerview.holders.VoiceMessageHolder
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
            is ImageMessageHolder -> holder.drawMessageImage(mListMessagesCache[position])
            is VoiceMessageHolder -> holder.drawMessageVoice(mListMessagesCache[position])
            is TextMessageHolder -> holder.drawMessageText(mListMessagesCache[position])
            else -> {
            }
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