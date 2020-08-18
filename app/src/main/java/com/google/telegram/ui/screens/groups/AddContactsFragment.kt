package com.google.telegram.ui.screens.groups

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.models.CommonModel
import com.google.telegram.utilits.APP_ACTIVITY
import com.google.telegram.utilits.AppValueEventListener
import com.google.telegram.utilits.hideKeyboard
import com.google.telegram.utilits.showToast
import kotlinx.android.synthetic.main.fragment_add_contacts.*
import kotlinx.android.synthetic.main.fragment_main_list.*

class AddContactsFragment : Fragment(R.layout.fragment_add_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)

    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Добавить участников"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()

        initRecyclerView()

        add_contacts_btn_next.setOnClickListener {
            listContacts.forEach {
                println(it.id)
            }
        }
    }

    private fun initRecyclerView() {
        setHasOptionsMenu(true)
        mRecyclerView = add_contacts_recycle_view
        mAdapter = AddContactsAdapter()

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

    companion object {
        val listContacts = mutableListOf<CommonModel>()
    }
}
