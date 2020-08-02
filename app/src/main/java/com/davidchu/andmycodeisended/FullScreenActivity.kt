package com.davidchu.andmycodeisended

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_full_screen.*

class FullScreenActivity: AppCompatActivity() {
    companion object Extra {
        const val triggerExtra = "Trigger"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        turnScreenOn()
        title_text.text = getString(intent.getIntExtra(triggerExtra, R.string.alarm_info))
        ok_button.setOnClickListener { dismissFullscreen() }
        Log.i("Full", "here we go")
    }

    private fun turnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        }
        else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    private fun dismissFullscreen() {
        onBackPressed()
        Notifications.cancelAlarm(this)
        Notifications.cancelNotification(this) //dismiss notification
    }
}