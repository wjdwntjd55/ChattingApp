package com.test.chatting.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.chatting.R

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the NotificationChannel
            val name = "카카오톡 채팅 알림"
            val descriptionText = "채팅 알림입니다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(getString(R.string.default_notification_channel_id), name, importance)
            mChannel.description = descriptionText

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

            // RemoteMessage 에서 데이터를 가져오는 방법
            // - RemoteMessage 의 notification 라는 인스턴스 안에 body 가 있다
            val body = message.notification?.body

            val notificationBuilder = NotificationCompat.Builder(applicationContext, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.img_chat)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(body)

            notificationManager.notify(0, notificationBuilder.build())

        }

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}