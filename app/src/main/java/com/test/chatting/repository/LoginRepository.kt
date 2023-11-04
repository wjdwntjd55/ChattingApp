package com.test.chatting.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginRepository {

    private val db = Firebase.auth

    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {

        db.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

}