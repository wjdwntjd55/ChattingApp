package com.test.chatting.ui.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatting.model.User
import com.test.chatting.repository.UserListRepository
import kotlinx.coroutines.launch

class MyPageViewModel: ViewModel() {

    private val userListRepository = UserListRepository()
    val currentUserLiveData = MutableLiveData<User>()

    fun getCurrentLoginUserInfo(currentUserUid : String) {
        viewModelScope.launch {
            val currentUser = userListRepository.getCurrentLoginUserInfo(currentUserUid)
            currentUserLiveData.postValue(currentUser)
        }
    }

}