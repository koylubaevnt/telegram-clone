package com.google.telegram.ui.fragments.messagerecyclerview.views

data class ImageMessageView(
    override val id: String,
    override val from: String,
    override val timestamp: String,
    override val fileUrl: String,
    override val text: String = ""
) : MessageView {
    override fun getTypeVIew(): Int {
        return MessageView.MESSAGE_IMAGE;
    }

    override fun equals(other: Any?): Boolean {
        return (other as MessageView).id == id
    }
}