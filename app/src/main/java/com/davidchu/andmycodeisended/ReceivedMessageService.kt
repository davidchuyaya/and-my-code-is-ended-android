package com.davidchu.andmycodeisended

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ReceivedMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Settings.setToken(applicationContext, token)
    }
    override fun onMessageReceived(message: RemoteMessage) {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        intent.putExtra(AlarmReceiver.triggerExtra, Notifications.Trigger.Code.notificationTitle)
        sendBroadcast(intent)
    }
}