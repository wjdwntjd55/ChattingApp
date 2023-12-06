package com.test.chatting.repository

import com.test.chatting.api.FCMApi

class FCMRepository(private val fcmApi: FCMApi) {

    fun sendFCM(message: String, fcmToken: String) {
        fcmApi.sendFCM(message, fcmToken)
    }
}