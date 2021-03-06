package com.google.telegram.ui.messagerecyclerview.views

import com.google.telegram.models.CommonModel
import com.google.telegram.utilits.TYPE_MESSAGE_FILE
import com.google.telegram.utilits.TYPE_MESSAGE_IMAGE
import com.google.telegram.utilits.TYPE_MESSAGE_VOICE

class AppViewFactory {

    companion object {
        fun getView(message: CommonModel): MessageView {
            return when (message.type) {
                TYPE_MESSAGE_IMAGE -> ImageMessageView(
                    message.id,
                    message.from,
                    message.timestamp.toString(),
                    message.fileUrl
                )
                TYPE_MESSAGE_VOICE -> VoiceMessageView(
                    message.id,
                    message.from,
                    message.timestamp.toString(),
                    message.fileUrl
                )
                TYPE_MESSAGE_FILE -> VoiceMessageView(
                    message.id,
                    message.from,
                    message.timestamp.toString(),
                    message.fileUrl,
                    message.text
                )
                else -> TextMessageView(
                    message.id,
                    message.from,
                    message.timestamp.toString(),
                    "",
                    message.text
                )
            }
        }
    }
}