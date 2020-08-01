package com.davidchu.andmycodeisended

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Notifications.startFullScreenNotification(context, Notifications.Trigger.Time)
    }
}