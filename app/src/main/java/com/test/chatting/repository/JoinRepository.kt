package com.test.chatting.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinRepository {

    private val db = Firebase.auth

    fun createUser(email: String, password: String): Task<AuthResult> {
        return db.createUserWithEmailAndPassword(email, password)
    }
}