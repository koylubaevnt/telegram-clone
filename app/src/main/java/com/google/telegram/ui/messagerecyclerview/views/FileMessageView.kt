package com.google.telegram.ui.messagerecyclerview.views

data class FileMessageView(
    override val id: String,
    override val from: String,
    override val timestamp: String,
    override val fileUrl: String,
    override val text: String = ""
) : MessageView {
    override fun getTypeVIew(): Int {
        return MessageView.MESSAGE_FILE
    }

    override fun equals(other: Any?): Boolean {
        return (other as MessageView).id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}