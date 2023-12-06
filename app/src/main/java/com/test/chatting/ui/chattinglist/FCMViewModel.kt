package com.test.chatting.ui.chattinglist

import androidx.lifecycle.ViewModel
import com.test.chatting.repository.FCMRepository

class FCMViewModel(private val fcmRepository: FCMRepository) : ViewModel() {

    fun sendFCM(message: String, fcmToken: String) {
        fcmRepository.sendFCM(message, fcmToken)
    }
}