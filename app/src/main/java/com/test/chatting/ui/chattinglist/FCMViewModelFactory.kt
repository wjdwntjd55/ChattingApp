package com.test.chatting.ui.chattinglist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.chatting.api.FCMApi
import com.test.chatting.repository.FCMRepository

class FCMViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FCMViewModel::class.java)) {
            val fcmApi = FCMApi(context)
            val fcmRepository = FCMRepository(fcmApi)
            return FCMViewModel(fcmRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
