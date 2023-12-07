package com.test.chatting.ui.mypage

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatting.model.User
import com.test.chatting.repository.LoginRepository
import com.test.chatting.repository.UserListRepository
import kotlinx.coroutines.launch

class MyPageViewModel: ViewModel() {

    private val userListRepository = UserListRepository()
    private val loginRepository = LoginRepository()

    val currentUserLiveData = MutableLiveData<User>()
    private var _selectedUri = MutableLiveData<Uri?>()
    var selectedUri: LiveData<Uri?> = _selectedUri

    fun getCurrentLoginUserInfo(currentUserUid : String) {
        viewModelScope.launch {
            val currentUser = userListRepository.getCurrentLoginUserInfo(currentUserUid)
            currentUserLiveData.postValue(currentUser)
        }
    }

    fun updateSelectedUri(uri: Uri?) {
        _selectedUri.value = uri
    }

    fun upLoadImage(imageUri: Uri?) {
        viewModelScope.launch {
            userListRepository.upLoadImage(imageUri)
        }
    }

    fun upDateDescription(description : String) {
        userListRepository.upDateDescription(description)
    }

    fun logOut() {
        loginRepository.logOutUser()
    }

}