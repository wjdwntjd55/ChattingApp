package com.test.chatting.repository

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.test.chatting.model.ChattingItem
import com.test.chatting.model.ChattingRoomItem
import com.test.chatting.model.Key
import com.test.chatting.model.Key.Companion.DB_CHAT_ROOMS
import com.test.chatting.model.User
import com.test.chatting.repository.LoginRepository.Companion.CURRENT_USER_EMAIL
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
                otherUserName = snapshot.child("otherUserName").getValue(String::class.java),
                otherUserFcmToken = snapshot.child("otherUserFcmToken").getValue(String::class.java)
            )
        } else {
            // 채팅방이 없는 경우
            val chatRoomId = UUID.randomUUID().toString()
            val newChattingRoomItem = ChattingRoomItem(chatRoomId = chatRoomId, otherUserUid = otherUserUid.userId, otherUserName = otherUserUid.username, otherUserFcmToken = otherUserUid.fcmToken)

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

    suspend fun updateInfo(currentUserUid: String, otherUser: User, chatRoomId: String, message: String) {

        val updates: MutableMap<String, Any> = hashMapOf(
            "${DB_CHAT_ROOMS}/$currentUserUid/${otherUser.userId}/lastMessage" to message,
            "${DB_CHAT_ROOMS}/${otherUser.userId}/$currentUserUid/lastMessage" to message,
            "${DB_CHAT_ROOMS}/${otherUser.userId}/$currentUserUid/chatRoomId" to chatRoomId,
            "${DB_CHAT_ROOMS}/${otherUser.userId}/$currentUserUid/otherUserUid" to currentUserUid,
            "${DB_CHAT_ROOMS}/${otherUser.userId}/$currentUserUid/otherUserName" to CURRENT_USER_EMAIL,
        )

        db.reference.updateChildren(updates).await()
    }

    fun getAllChattingData(chatRoomId: String, onDataReceived: (List<ChattingItem>) -> Unit) {
        val chattingItemList = mutableListOf<ChattingItem>()

        val chatReference = db.reference.child(Key.DB_CHATS).child(chatRoomId)

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatId = snapshot.child("chatId").getValue(String::class.java)
                val message = snapshot.child("message").getValue(String::class.java)
                val userUid = snapshot.child("userUid").getValue(String::class.java)

                val chattingItem = ChattingItem(chatId, userUid, message)
                chattingItemList.add(chattingItem)

                onDataReceived(chattingItemList)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // 변경 사항이 있을 경우 처리
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // 삭제 사항이 있을 경우 처리
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // 이동 사항이 있을 경우 처리
            }

            override fun onCancelled(error: DatabaseError) {
                // 취소가 발생한 경우 처리
            }
        }

        chatReference.addChildEventListener(childEventListener)
    }

    suspend fun getMyChattingRoom(currentUserUid: String): MutableList<ChattingRoomItem> {
        val myChattingRoomList = mutableListOf<ChattingRoomItem>()

        val snapshot = db.reference.child(DB_CHAT_ROOMS).child(currentUserUid)
            .get()
            .await()

        if (snapshot.exists()) {
            for (chattingRoomSnapshot in snapshot.children) {
                val myChattingRoomItem = chattingRoomSnapshot.getValue(ChattingRoomItem::class.java)

                if (myChattingRoomItem != null) {
                    myChattingRoomList.add(myChattingRoomItem)
                }

                myChattingRoomList.sortBy { it.otherUserName }
            }
        }

        return myChattingRoomList
    }

}