package com.google.telegram.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.telegram.models.UserModel

lateinit var CURRENT_UID: String
lateinit var AUTH : FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: UserModel

const val TYPE_TEXT = "text"
const val NODE_USERS = "users"
const val NODE_PHONES = "phones"
const val NODE_PHONES_CONTACTS = "phones_contacts"
const val NODE_USERNAMES = "usernames"
const val NODE_MESSAGES = "messages"
const val NODE_MAIN_LIST = "main_list"
const val NODE_GROUPS = "groups"

const val FOLDER_PROFILE_IMAGE = "profile_image"
const val FOLDER_FILES = "message_files"
const val FOLDER_GROUPS_IMAGE = "groups_image"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULL_NAME = "fullname"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photoUrl"
const val CHILD_STATE = "state"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM = "from"
const val CHILD_TIMESTAMP = "timestamp"
const val CHILD_FILE_URL = "fileUrl"
const val CHILD_MEMBER = "member"

const val USER_CREATOR = "creator"
const val USER_ADMIN = "admin"
const val USER_MEMBER = "member"
