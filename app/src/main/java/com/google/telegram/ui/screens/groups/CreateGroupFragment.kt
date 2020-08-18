package com.google.telegram.ui.screens.groups

import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.models.CommonModel
import com.google.telegram.ui.screens.base.BaseFragment
import com.google.telegram.utilits.APP_ACTIVITY
import com.google.telegram.utilits.hideKeyboard
import com.google.telegram.utilits.showToast
import kotlinx.android.synthetic.main.fragment_create_group.*

class CreateGroupFragment(private var listContacts: List<CommonModel>) :
    BaseFragment(R.layout.fragment_create_group) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = getString(R.string.create_group)
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()

        initRecyclerView()

        create_group_btn_done.setOnClickListener {
            showToast("Click")
        }
        create_group_input_name.requestFocus()
    }


    private fun initRecyclerView() {
        setHasOptionsMenu(true)
        mRecyclerView = create_group_recycle_view
        mAdapter = AddContactsAdapter()
        mRecyclerView.adapter = mAdapter
        listContacts.forEach {
            mAdapter.updateListItems(it)
        }
    }

}
