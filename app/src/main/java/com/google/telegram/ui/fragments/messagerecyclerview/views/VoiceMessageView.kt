package com.google.telegram.ui.fragments.messagerecyclerview.views

data class VoiceMessageView(
    override val id: String,
    override val from: String,
    override val timestamp: String,
    override val fileUrl: String,
    override val text: String = ""
) : MessageView {
    override fun getTypeVIew(): Int {
        return MessageView.MESSAGE_VOICE;
    }

    override fun equals(other: Any?): Boolean {
        return (other as MessageView).id == id
    }
}