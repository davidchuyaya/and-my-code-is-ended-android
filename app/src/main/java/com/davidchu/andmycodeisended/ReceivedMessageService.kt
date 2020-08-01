package com.davidchu.andmycodeisended

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ReceivedMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Settings.setToken(applicationContext, token)
    }
    override fun onMessageReceived(message: RemoteMessage) {
        Notifications.startFullScreenNotification(applicationContext, Notifications.Trigger.Code)
    }
}