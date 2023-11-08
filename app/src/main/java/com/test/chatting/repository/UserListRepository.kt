package com.test.chatting.repository

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

                if (userId != LoginRepository.CURRENT_USER_UID && userId != null && userName != null) {
                    val user = User(userId, userName)
                    userList.add(user)
                }
            }
        }

        return userList
    }

}