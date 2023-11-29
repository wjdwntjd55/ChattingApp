package com.test.chatting.ui.chattinglist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.chatting.model.ChattingRoomItem
import com.test.chatting.repository.ChattingRepository
import kotlinx.coroutines.launch

class ChattingListViewModel : ViewModel() {

    private val chattingRepository = ChattingRepository()
    val myChattingRoomLiveData = MutableLiveData<List<ChattingRoomItem>>()

    fun getMyChattingRoom(currentUserUid: String) {
        viewModelScope.launch {
            val myChattingRoomList = chattingRepository.getMyChattingRoom(currentUserUid)
            myChattingRoomLiveData.postValue(myChattingRoomList)
        }
    }

}