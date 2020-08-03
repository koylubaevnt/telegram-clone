package com.google.telegram.ui.screens.mainlist

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.models.CommonModel
import com.google.telegram.ui.screens.settings.ChangeNameFragment
import com.google.telegram.utilits.*
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

        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener {mainListDS ->
            mListItems = mainListDS.children.map { it.getCommonModel() }
            mListItems.forEach { model ->
                mRefUsers.child(model.id).addListenerForSingleValueEvent(AppValueEventListener {userDS ->
                    val newModel = userDS.getCommonModel()
                    mRefMessages.child(model.id).limitToLast(1)
                        .addListenerForSingleValueEvent(AppValueEventListener {messageDS ->
                            val tempList = messageDS.children.map { it.getCommonModel() }
                            newModel.lastMessage = tempList[0].text
                            if (newModel.fullname.isEmpty()) {
                                newModel.fullname = newModel.phone
                            }
                            mAdapter.updateListItems(newModel)
                        })
                })
            }
        })

        mRecyclerView.adapter = mAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.single_chat_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return true
    }
}
