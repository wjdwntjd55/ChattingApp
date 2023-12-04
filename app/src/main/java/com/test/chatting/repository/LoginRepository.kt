package com.test.chatting.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.test.chatting.model.Key.Companion.DB_URL
import com.test.chatting.model.Key.Companion.DB_USERS

class LoginRepository {

    private val db = Firebase.auth

    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {

        db.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = db.currentUser
                    if (user != null) {
                        CURRENT_USER_UID = user.uid
                        CURRENT_USER_EMAIL = user.email.toString()

                        Firebase.messaging.token.addOnCompleteListener {
                            val token = it.result
                            val userId = CURRENT_USER_UID
                            val userEmail = CURRENT_USER_EMAIL

                            val currentUserInfo = mutableMapOf<String, Any>()
                            currentUserInfo["userId"] = userId
                            currentUserInfo["userName"] = userEmail
                            currentUserInfo["fcmToken"] = token
                            
                            updateUserInDatabase(userId, currentUserInfo)
                        }

                    }
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    fun updateUserInDatabase(userId: String, user: Map<String, Any>) {
        Firebase.database(DB_URL).reference.child(DB_USERS).child(userId).updateChildren(user)
    }

    fun logOutUser() {
        db.signOut()
    }

    companion object {
        var CURRENT_USER_UID: String = ""
        var CURRENT_USER_EMAIL: String = ""
    }
}