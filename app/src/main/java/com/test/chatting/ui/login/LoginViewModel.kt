package com.test.chatting.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.chatting.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()
    val loginResultLiveData = MutableLiveData<Boolean>()

    fun loginUser(email: String, password: String) {
        loginRepository.loginUser(email, password) { loginResult ->

            val userId = LoginRepository.CURRENT_USER_UID
            val userEmail = LoginRepository.CURRENT_USER_EMAIL

            val user = mutableMapOf<String, Any>()
            user["userId"] = userId
            user["userName"] = userEmail

            loginRepository.updateUserInDatabase(userId, user)

            loginResultLiveData.value = loginResult
        }
    }

}