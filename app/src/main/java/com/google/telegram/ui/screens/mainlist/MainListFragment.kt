package com.google.telegram.ui.screens.mainlist

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.models.CommonModel
import com.google.telegram.utilits.APP_ACTIVITY
import com.google.telegram.utilits.AppValueEventListener
import com.google.telegram.utilits.hideKeyboard
import kotlinx.android.synthetic.main.fragment_main_list.*

class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)

    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Telegram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        setHasOptionsMenu(true)
        mRecyclerView = main_list_recycle_view
        mAdapter = MainListAdapter()

        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener { mainListDS ->
            mListItems = mainListDS.children.map { it.getCommonModel() }
            mListItems.forEach { model ->
                mRefUsers.child(model.id)
                    .addListenerForSingleValueEvent(AppValueEventListener { userDS ->
                        val newModel = userDS.getCommonModel()
                        mRefMessages.child(model.id).limitToLast(1)
                            .addListenerForSingleValueEvent(AppValueEventListener { messageDS ->
                                val tempList = messageDS.children.map { it.getCommonModel() }
                                if (tempList.isNotEmpty()) {
                                    newModel.fullname = newModel.phone
                                }
                                mAdapter.updateListItems(newModel)
                            })
                    })
            }
        })

        mRecyclerView.adapter = mAdapter
    }
}
