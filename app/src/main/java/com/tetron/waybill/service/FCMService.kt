package com.tetron.waybill.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.util.TokenUtils

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Check if message contains a data payload.
        if (BuildConfig.DEBUG)
            Log.d(this.toString(), message.data.toString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val tokenUtils = TokenUtils()
        tokenUtils.setToken(token)
    }
}