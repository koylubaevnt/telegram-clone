package com.google.telegram.models

data class CommonModel(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullname: String = "",
    var state: String = "",
    var phone: String = "",
    var photoUrl: String = "empty",

    var text: String = "",
    var type: String = "",
    var from: String = "",
    var timestamp: Any = "",
    var fileUrl: String = "empty",
    var lastMessage: String = "",
    var choice: Boolean = false) {

    override fun equals(other: Any?): Boolean {
        return (other as CommonModel).id == id
    }

}