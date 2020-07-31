package com.davidchu.andmycodeisended

import com.google.firebase.messaging.FirebaseMessagingService

class ReceivedMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Settings.setToken(applicationContext, token)
    }
}