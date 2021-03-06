package com.google.telegram.ui.screens.groups

import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.models.CommonModel
import com.google.telegram.ui.screens.base.BaseFragment
import com.google.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_add_contacts.*

class AddContactsFragment : BaseFragment(R.layout.fragment_add_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private val mRefContactList = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)

    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        listContacts.clear()
        super.onResume()
        APP_ACTIVITY.title = getString(R.string.add_contacts)
        hideKeyboard()

        initRecyclerView()

        add_contacts_btn_next.setOnClickListener {
            if (listContacts.isEmpty()) {
                showToast("Добавьте участников")
            } else {
                replaceFragment(CreateGroupFragment(listContacts))
            }
        }
    }

    private fun initRecyclerView() {
        setHasOptionsMenu(true)
        mRecyclerView = add_contacts_recycle_view
        mAdapter = AddContactsAdapter()

        mRefContactList.addListenerForSingleValueEvent(AppValueEventListener { mainListDS ->
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
