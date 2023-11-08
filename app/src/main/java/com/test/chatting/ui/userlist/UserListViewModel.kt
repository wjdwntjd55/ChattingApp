package com.test.chatting.ui.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.chatting.model.User
import com.test.chatting.repository.UserListRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    private val userListRepository = UserListRepository()
    val userListLiveData = MutableLiveData<List<User>>()

    fun getAllUserInfo() {
        viewModelScope.launch {
            val userList = userListRepository.getAllUserInfo()
            userListLiveData.postValue(userList)
        }
    }

}