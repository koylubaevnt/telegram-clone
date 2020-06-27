package com.google.telegram.ui.fragments.messagerecyclerview.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.ui.fragments.messagerecyclerview.views.MessageView

class AppHolderFactory {

    companion object {

        fun getHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                MessageView.MESSAGE_IMAGE -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.message_item_image, parent, false)
                    ImageMessageHolder(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.message_item_text, parent, false)
                    TextMessageHolder(view)
                }
            }
        }
    }

}