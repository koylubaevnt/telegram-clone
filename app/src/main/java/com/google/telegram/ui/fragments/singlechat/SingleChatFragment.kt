package com.google.telegram.ui.fragments.singlechat

import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DatabaseReference
import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.models.CommonModel
import com.google.telegram.models.UserModel
import com.google.telegram.ui.fragments.BaseFragment
import com.google.telegram.utilits.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*

class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private val MESSAGE_COUNT = 3


    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: UserModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference
    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppChildEventListener
    private var mCountMessages = MESSAGE_COUNT
    private var mIsScrolling = false
    private var mSmoothScrollToPosition = true
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onResume() {
        super.onResume()
        initToolbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mSwipeRefreshLayout = chat_swipe_refresh
        mRecyclerView = chat_recycle_view
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT
            .child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        mRecyclerView.adapter = mAdapter
        mMessagesListener = AppChildEventListener {
            mAdapter.addItem(it.getCommonModel(), mSmoothScrollToPosition) {
                if (mSmoothScrollToPosition) {
                    mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
                }
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (mIsScrolling && dy < 0) {
//                    updateData()
//                }
//            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true
                }
            }
        })

        mSwipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
    }

    private fun updateData() {
        if (mAdapter.itemCount >= mCountMessages) {
            mSmoothScrollToPosition = false
            mIsScrolling = false
            mCountMessages += MESSAGE_COUNT
            mRefMessages.removeEventListener(mMessagesListener)
            mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)
        } else {
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(
            NODE_USERS
        ).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)

        chat_btn_send_message.setOnClickListener {
            mSmoothScrollToPosition = true
            val message = chat_input_message.text.toString()
            if (message.isEmpty()) {
                showToast("Введите сообщение")
            } else {
                sendMessage(
                    message,
                    contact.id,
                    TYPE_TEXT
                ) {
                    chat_input_message.setText("")
                }
            }
        }
    }

    private fun initInfoToolbar() {
        if (mReceivingUser.fullname.isEmpty()) {
            mToolbarInfo.toolbar_chat_fullname.text = contact.fullname
        } else {
            mToolbarInfo.toolbar_chat_fullname.text = mReceivingUser.fullname
        }
        mToolbarInfo.toolbar_chat_image.downloadAndSetImage(mReceivingUser.photoUrl)
        mToolbarInfo.toolbar_chat_status.text = mReceivingUser.state
    }

    override fun onPause() {
        super.onPause()
        mToolbarInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessages.removeEventListener(mMessagesListener)
    }
}
