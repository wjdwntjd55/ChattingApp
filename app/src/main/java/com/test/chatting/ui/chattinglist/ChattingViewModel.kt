package com.test.chatting.ui.chattinglist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatting.model.ChattingRoomItem
import com.test.chatting.model.User
import com.test.chatting.repository.ChattingRepository
import kotlinx.coroutines.launch

class ChattingViewModel: ViewModel() {

    private val chattingRepository = ChattingRepository()
    val chattingRoomLiveData = MutableLiveData<ChattingRoomItem>()

    fun createChattingRoom(currentUserUid: String, otherUserUid: User) {
        viewModelScope.launch {
            val chattingRoomInfo = chattingRepository.createChattingRoom(currentUserUid, otherUserUid)
            chattingRoomLiveData.postValue(chattingRoomInfo)
        }

    }

    fun createMessage(chatRoomId: String, message: String) {
        viewModelScope.launch {
            chattingRepository.createMessage(chatRoomId, message)
        }
    }

    fun updateInfo(currentUserUid: String, otherUserUid: User, chatRoomId: String, message: String) {
        viewModelScope.launch {
            chattingRepository.updateInfo(currentUserUid, otherUserUid, chatRoomId, message)
        }
    }

}