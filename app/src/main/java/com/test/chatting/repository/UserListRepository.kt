package com.test.chatting.repository

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.test.chatting.model.Key
import com.test.chatting.model.User
import kotlinx.coroutines.tasks.await

class UserListRepository {

    private val db = Firebase.database

    suspend fun getAllUserInfo(): MutableList<User> {
        val userList = mutableListOf<User>()

        val snapshot = db.reference.child(Key.DB_USERS)
            .get()
            .await()

        if (snapshot.exists()) {
            for (userSnapshot in snapshot.children) {
                val userId = userSnapshot.child("userId").getValue(String::class.java)
                val userName = userSnapshot.child("userName").getValue(String::class.java)
                val userDescription = userSnapshot.child("description").getValue(String::class.java) ?: ""
                val fcmToken = userSnapshot.child("fcmToken").getValue(String::class.java) ?: ""

                if (userId != LoginRepository.CURRENT_USER_UID && userId != null && userName != null) {
                    val user = User(userId, userName, userDescription, fcmToken)
                    userList.add(user)
                }
            }
        }

        return userList
    }

    suspend fun getCurrentLoginUserInfo(currentUserUid : String): User {
        var currentUserInfo = User("", "", "", "")

        val snapshot = db.reference.child(Key.DB_USERS)
            .child(currentUserUid)
            .get()
            .await()

        if (snapshot.exists()) {

            val currentUserUid = snapshot.child("userId").getValue(String::class.java) ?: ""
            val currentUserName = snapshot.child("userName").getValue(String::class.java) ?: ""
            val currentUserDescription = snapshot.child("description").getValue(String::class.java) ?: ""
            val currentUserFcmToken = snapshot.child("fcmToken").getValue(String::class.java) ?: ""

            currentUserInfo = User(currentUserUid, currentUserName, currentUserDescription, currentUserFcmToken)

        }

        return currentUserInfo
    }

    fun upDateDescription(description : String) {

        val userInfo = mutableMapOf<String, Any >()
        userInfo["description"] = description

        db.reference.child(Key.DB_USERS)
            .child(LoginRepository.CURRENT_USER_UID)
            .updateChildren(userInfo)

    }

}