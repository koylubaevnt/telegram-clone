package com.google.telegram.ui.messagerecyclerview.holders

import com.google.telegram.ui.messagerecyclerview.views.MessageView

interface MessageHolder {

    fun drawMessage(view: MessageView)

    fun onAttach(view: MessageView)

    fun onDetach()

}