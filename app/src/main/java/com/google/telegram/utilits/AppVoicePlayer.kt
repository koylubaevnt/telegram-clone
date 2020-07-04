package com.google.telegram.utilits

import android.media.MediaPlayer
import com.google.telegram.database.getFileFromStorage
import java.io.File

class AppVoicePlayer {

    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mFile: File

    fun play(messageKey: String, fileUrl: String, function: () -> Unit) {
        mFile = File(APP_ACTIVITY.filesDir, messageKey)
        if (mFile.exists() && mFile.length() > 0 && mFile.isFile) {
            startPlay() {
                function()
            }
        } else {
            mFile.createNewFile()
            getFileFromStorage(mFile, fileUrl) {
                startPlay() {
                    function()
                }
            }
        }
    }

    private fun startPlay(function: () -> Unit) {
        try {
            mMediaPlayer.setDataSource(mFile.absolutePath)
            mMediaPlayer.prepare()
            mMediaPlayer.start()
            mMediaPlayer.setOnCompletionListener {
                stop() {
                    function()
                }
            }
        } catch (ex: Exception) {
            showToast(ex.message.toString())
        }
    }

    fun stop(function: () -> Unit) {
        try {
            mMediaPlayer.stop()
            mMediaPlayer.reset()
        } catch (ex: Exception) {
            showToast(ex.message.toString())
        } finally {
            function()
        }
    }

    fun release() {
        mMediaPlayer.release()
    }

    fun init() {
        mMediaPlayer = MediaPlayer()
    }
}