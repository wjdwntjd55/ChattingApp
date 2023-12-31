package com.test.chatting.model

data class ChattingRoomItem(
    val chatRoomId: String? = null,
    val lastMessage: String? = null,
    val otherUserName: String? = null,
    val otherUserUid: String? = null,
    val otherUserFcmToken: String? = null,
    val otherUserProfile: String? = null,
)

