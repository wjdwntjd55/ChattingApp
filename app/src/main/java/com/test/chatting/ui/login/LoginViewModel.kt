package com.test.chatting.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.chatting.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()
    val loginResultLiveData = MutableLiveData<Boolean>()

    fun loginUser(email: String, password: String) {
        loginRepository.loginUser(email, password) { loginResult ->
            loginResultLiveData.value = loginResult
        }
    }

}