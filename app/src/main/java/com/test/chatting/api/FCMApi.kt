package com.test.chatting.api

import android.content.Context
import android.util.Log
import com.test.chatting.BuildConfig
import com.test.chatting.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class FCMApi(private val context: Context) {

    private val client = OkHttpClient()

    fun sendFCM(message: String, fcmToken: String) {

        val root = JSONObject()
        val notification = JSONObject()
        notification.put("title", context.getString(R.string.app_name))
        notification.put("body", message)

        root.put("to", fcmToken)
        root.put("priority", "high")
        root.put("notification", notification)

        val requestBody = root.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder().post(requestBody).url("https://fcm.googleapis.com/fcm/send")
            .header("Authorization", "key=${BuildConfig.FCM_SERVER_KEY}").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.stackTraceToString()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("FCMApi", response.toString())
            }
        })
    }
}