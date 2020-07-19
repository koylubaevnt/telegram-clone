package com.google.telegram.ui.messagerecyclerview.holders

import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.database.CURRENT_UID
import com.google.telegram.database.getFileFromStorage
import com.google.telegram.ui.messagerecyclerview.views.MessageView
import com.google.telegram.utilits.WRITE_FILES
import com.google.telegram.utilits.asTime
import com.google.telegram.utilits.checkPermission
import com.google.telegram.utilits.showToast
import kotlinx.android.synthetic.main.message_item_file.view.*
import java.io.File
import java.lang.Exception

class FileMessageHolder(view: View): RecyclerView.ViewHolder(view), MessageHolder {

    private val blockUserFileMessage: ConstraintLayout = view.block_user_file_message
    private val chatUserFileName: TextView = view.chat_user_file_name
    private val chatUserBtnDownload: ImageView = view.chat_user_btn_download
    private val chatUserProgressBar: ProgressBar = view.chat_user_progress_bar
    private val chatUserFileMessageTime: TextView = view.chat_user_file_message_time

    private val blockReceivingFileMessage: ConstraintLayout = view.block_receiving_file_message
    private val chatReceivingFileName: TextView = view.chat_receiving_file_name
    private val chatReceivingBtnDownload: ImageView = view.chat_receiving_btn_download
    private val chatReceivingProgressBar: ProgressBar = view.chat_receiving_progress_bar
    private val chatReceivingFileMessageTime: TextView = view.chat_receiving_file_message_time

    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blockUserFileMessage.visibility = View.VISIBLE
            blockReceivingFileMessage.visibility = View.GONE
            chatUserFileMessageTime.text =
                view.timestamp.asTime()
            chatUserFileName.text = view.text
        } else {
            blockUserFileMessage.visibility = View.GONE
            blockReceivingFileMessage.visibility = View.VISIBLE
            chatReceivingFileMessageTime.text =
                view.timestamp.asTime()
            chatReceivingFileName.text = view.text
        }
    }

    override fun onAttach(view: MessageView) {
        if (view.from == CURRENT_UID) {
            chatUserBtnDownload.setOnClickListener { clickToBtnFile(view) }
        } else {
            chatReceivingBtnDownload.setOnClickListener { clickToBtnFile(view) }
        }
    }

    private fun clickToBtnFile(view: MessageView) {
        if (view.from == CURRENT_UID) {
            chatUserBtnDownload.visibility = View.INVISIBLE
            chatUserProgressBar.visibility = View.VISIBLE
        } else {
            chatReceivingBtnDownload.visibility = View.INVISIBLE
            chatReceivingProgressBar.visibility = View.VISIBLE
        }

        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), view.text)

        try {
            if (checkPermission(WRITE_FILES)) {
                file.createNewFile()
                getFileFromStorage(file, view.fileUrl) {
                    if (view.from == CURRENT_UID) {
                        chatUserBtnDownload.visibility = View.VISIBLE
                        chatUserProgressBar.visibility = View.INVISIBLE
                    } else {
                        chatReceivingBtnDownload.visibility = View.VISIBLE
                        chatReceivingProgressBar.visibility = View.INVISIBLE
                    }
                }
            }
        } catch (e : Exception) {
            showToast(e.message.toString())
        }
    }

    override fun onDetach() {
        chatUserBtnDownload.setOnClickListener { null }
        chatReceivingBtnDownload.setOnClickListener { null }
    }

}