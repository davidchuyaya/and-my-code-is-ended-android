package com.davidchu.andmycodeisended

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTimeDateText()
        setTokenText()
        createListeners()
    }

    private fun setTimeDateText() {
        onTimeSwitchClicked(Settings.getAlarmOn(this)) //Enable/disable buttons accordingly
        setTime(Settings.getAlarmTime(this))
        setDate(Settings.getAlarmDate(this))
    }
    private fun setTime(time: LocalTime?) {
        if (time == null) time_picker.text = getString(R.string.time_default)
        else {
            time_picker.text = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
            saveTimeDate(time, Settings.getAlarmDate(this) ?: LocalDate.now())
        }
    }
    private fun setDate(date: LocalDate?) {
        if (date == null) date_picker.text = getString(R.string.date_default)
        else {
            date_picker.text = date.format(DateTimeFormatter.ofPattern("MMMM d"))
            saveTimeDate(Settings.getAlarmTime(this) ?: LocalTime.now(), date)
        }
    }
    private fun saveTimeDate(time: LocalTime, date: LocalDate) {
        val epoch = LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toEpochSecond()
        Settings.setAlarmEpoch(this, epoch)
    }

    private fun setTokenText() {
        TokenUtil.callWithNewToken {token ->
            if (token == null)
                return@callWithNewToken
            code_dynamic_text.text = getString(R.string.code_dynamic_text, token)
            Settings.setToken(this, token)
        }
    }

    private fun createListeners() {
        code_clickable_area.setOnClickListener { onShareClicked() }
        time_picker.setOnClickListener { TimePickerFragment().show(supportFragmentManager, "time")}
        date_picker.setOnClickListener { DatePickerFragment().show(supportFragmentManager, "date")}
        time_switch.setOnCheckedChangeListener { _, isChecked -> onTimeSwitchClicked(isChecked) }
    }

    override fun onAttachFragment(fragment: Fragment) {
        when (fragment) {
            is TimePickerFragment -> {
                val time = Settings.getAlarmTime(this) ?: LocalTime.now()
                fragment.setTimeAndCallback(time, ::setTime)
            }
            is DatePickerFragment -> {
                val date = Settings.getAlarmDate(this) ?: LocalDate.now()
                fragment.setDateAndCallback(date, ::setDate)
            }
        }
    }

    private fun onShareClicked() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, code_dynamic_text.text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun onTimeSwitchClicked(on: Boolean) {
        time_picker.isEnabled = on
        date_picker.isEnabled = on
        Settings.setAlarmOn(this, on)
    }
}
