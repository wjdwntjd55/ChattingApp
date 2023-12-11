package com.test.chatting.repository

import com.test.chatting.api.FCMApi

class FCMRepository(private val fcmApi: FCMApi) {

    fun sendFCM(userName: String, message: String, fcmToken: String) {
        fcmApi.sendFCM(userName, message, fcmToken)
    }
}