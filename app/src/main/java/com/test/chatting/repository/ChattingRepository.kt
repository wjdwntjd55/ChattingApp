package com.test.chatting.repository

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.test.chatting.model.ChattingItem
import com.test.chatting.model.ChattingRoomItem
import com.test.chatting.model.Key
import com.test.chatting.model.Key.Companion.DB_CHAT_ROOMS
import com.test.chatting.model.User
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChattingRepository {

    private val db = Firebase.database

    suspend fun createChattingRoom(currentUserUid: String, otherUserUid: User): ChattingRoomItem {

        val snapshot = db.reference.child(DB_CHAT_ROOMS).child(currentUserUid).child(otherUserUid.userId)
            .get()
            .await()

        if (snapshot.exists()) {
            // 이미 채팅방이 존재하는 경우
            return ChattingRoomItem(
                chatRoomId = snapshot.child("chatRoomId").getValue(String::class.java),
                otherUserUid = snapshot.child("otherUserUid").getValue(String::class.java),
                otherUserName = snapshot.child("otherUserName").getValue(String::class.java)
            )
        } else {
            // 채팅방이 없는 경우
            val chatRoomId = UUID.randomUUID().toString()
            val newChattingRoomItem = ChattingRoomItem(chatRoomId = chatRoomId, otherUserUid = otherUserUid.userId, otherUserName = otherUserUid.username)

            db.reference.child(DB_CHAT_ROOMS).child(currentUserUid).child(otherUserUid.userId)
                .setValue(newChattingRoomItem)
                .await()

            return newChattingRoomItem
        }
    }

    suspend fun createMessage(chatRoomId: String, message: String): ChattingItem {

        val newChattingItem = ChattingItem(
            message = message,
            userUid = LoginRepository.CURRENT_USER_UID
        )

        db.reference.child(Key.DB_CHATS).child(chatRoomId).push().apply {
            newChattingItem.chatId = key
            setValue(newChattingItem)
                .await()
        }

        Log.d("ChattingRepository", "newChatItem : ${newChattingItem}")

        return newChattingItem

    }


}
