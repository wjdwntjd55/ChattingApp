package com.test.chatting.ui.join

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.test.chatting.repository.JoinRepository

class JoinViewModel : ViewModel() {

    private val joinRepository = JoinRepository()

    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return joinRepository.createUser(email, password)
    }

}