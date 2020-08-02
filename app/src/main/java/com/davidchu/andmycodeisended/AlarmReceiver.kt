package com.davidchu.andmycodeisended

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver() {
    companion object Extra {
        const val triggerExtra = "Trigger"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val titleRes = intent.getIntExtra(triggerExtra, R.string.alarm_title_time_trigger)
        Notifications.startFullScreenNotification(context, titleRes)
        Settings.setAlarmOn(context, false)
    }
}