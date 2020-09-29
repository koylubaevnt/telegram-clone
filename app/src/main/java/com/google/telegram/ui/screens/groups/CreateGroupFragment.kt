package com.google.telegram.ui.screens.groups

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.google.telegram.R
import com.google.telegram.database.*
import com.google.telegram.models.CommonModel
import com.google.telegram.ui.screens.base.BaseFragment
import com.google.telegram.ui.screens.mainlist.MainListFragment
import com.google.telegram.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_create_group.*
import kotlinx.android.synthetic.main.fragment_settings.*

class CreateGroupFragment(private var listContacts: List<CommonModel>) :
    BaseFragment(R.layout.fragment_create_group) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private var mUri = Uri.EMPTY

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = getString(R.string.create_group)
        hideKeyboard()

        initRecyclerView()

        create_group_photo.setOnClickListener { addPhoto() }

        create_group_btn_done.setOnClickListener {
            val nameGroup = create_group_input_name.text.toString()
            if (nameGroup.isEmpty()) {
                showToast("Введите имя")
            } else {
                createGroupToDatabase(nameGroup, mUri, listContacts) {
                    replaceFragment(MainListFragment())
                }
            }
        }
        create_group_input_name.requestFocus()
        create_group_counts.text = getPlurals(listContacts.size)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                mUri = result.uri
                create_group_photo.setImageURI(mUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                showToast(result.error.message.toString())
            }
        }
    }

    private fun addPhoto() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(250, 250)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
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
