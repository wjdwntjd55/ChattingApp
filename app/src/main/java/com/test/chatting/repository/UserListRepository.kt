package com.test.chatting.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.test.chatting.model.Key
import com.test.chatting.model.User
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UserListRepository {

    private val db = Firebase.database
    private val storage = Firebase.storage

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
                val userProfile = userSnapshot.child("userProfile").getValue(String::class.java) ?: ""

                if (userId != LoginRepository.CURRENT_USER_UID && userId != null && userName != null) {
                    val user = User(userId, userName, userDescription, fcmToken, userProfile)
                    userList.add(user)
                }

                userList.sortBy { it.username }
            }
        }

        return userList
    }

    suspend fun getCurrentLoginUserInfo(currentUserUid : String): User {
        var currentUserInfo = User("", "", "", "", "")

        val snapshot = db.reference.child(Key.DB_USERS)
            .child(currentUserUid)
            .get()
            .await()

        if (snapshot.exists()) {

            val currentUserUid = snapshot.child("userId").getValue(String::class.java) ?: ""
            val currentUserName = snapshot.child("userName").getValue(String::class.java) ?: ""
            val currentUserDescription = snapshot.child("description").getValue(String::class.java) ?: ""
            val currentUserFcmToken = snapshot.child("fcmToken").getValue(String::class.java) ?: ""
            val currentUserProfile = snapshot.child("userProfile").getValue(String::class.java) ?: ""

            currentUserInfo = User(currentUserUid, currentUserName, currentUserDescription, currentUserFcmToken, currentUserProfile)

        }

        return currentUserInfo
    }

    suspend fun upLoadImage(imageUri: Uri?) {

        val fileName = "${UUID.randomUUID()}.jpg"

        if (imageUri != null) {
            storage.reference.child("images/${fileName}")
                .putFile(imageUri)
                .await()

            upDateUserProfile(fileName)
        }

    }

    fun upDateUserProfile(fileName: String) {

        storage.reference.child("images/${fileName}")
            .downloadUrl
            .addOnSuccessListener { uri ->

                val downloadUrl = uri.toString()

                val userProfileInfo = mutableMapOf<String, Any>()
                userProfileInfo["userProfile"] = downloadUrl

                db.reference.child(Key.DB_USERS)
                    .child(LoginRepository.CURRENT_USER_UID)
                    .updateChildren(userProfileInfo)
            }

    }

    fun upDateDescription(description : String) {

        val userInfo = mutableMapOf<String, Any >()
        userInfo["description"] = description

        db.reference.child(Key.DB_USERS)
            .child(LoginRepository.CURRENT_USER_UID)
            .updateChildren(userInfo)

    }

}