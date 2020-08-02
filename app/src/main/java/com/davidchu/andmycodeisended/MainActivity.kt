package com.davidchu.andmycodeisended

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    val musicPickerRequestCode = 314

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDateTimeButtons()
        setTokenText()
        setMusicText(Settings.getMusic(this))
        createListeners()
    }

    private fun setDateTimeButtons() {
        val alarmOn = Settings.getAlarmOn(this)
        time_picker.isEnabled = alarmOn
        date_picker.isEnabled = alarmOn
        time_switch.isChecked = alarmOn

        val alarmDateTime = Settings.getAlarmDateTime(this)
        setTime(alarmDateTime)
        setDate(alarmDateTime)
    }
    private fun setTime(dateTime: LocalDateTime?) {
        if (dateTime == null) {
            time_picker.text = getString(R.string.time_default)
            return
        }

        time_picker.text = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
        Settings.setAlarmDateTime(this, dateTime)
        if (Settings.getAlarmOn(this))
            Notifications.setAlarm(this, dateTime)
    }
    private fun setDate(dateTime: LocalDateTime?) {
        if (dateTime == null) {
            date_picker.text = getString(R.string.date_default)
            return
        }
        date_picker.text = dateTime.format(DateTimeFormatter.ofPattern("MMMM d"))
        Settings.setAlarmDateTime(this, dateTime)
        if (Settings.getAlarmOn(this))
            Notifications.setAlarm(this, dateTime)
    }

    private fun setTokenText() {
        TokenUtil.callWithNewToken {token ->
            if (token == null)
                return@callWithNewToken
            code_dynamic_text.text = getString(R.string.code_dynamic_text, token)
            Settings.setToken(this, token)
        }
    }

    private fun setMusicText(uri: Uri?) {
        if (uri == null)
            music_picker.text = getString(R.string.default_music)
        else
            music_picker.text = RingtoneManager.getRingtone(this, uri).getTitle(this)
    }

    private fun createListeners() {
        code_clickable_area.setOnClickListener { onShareClicked() }
        music_picker.setOnClickListener { onMusicPickerClicked() }
        time_picker.setOnClickListener { PickerFragment().show(supportFragmentManager, "time") }
        date_picker.setOnClickListener { PickerFragment().show(supportFragmentManager, "date")}
        time_switch.setOnCheckedChangeListener { _, isChecked -> onTimeSwitchClicked(isChecked) }
    }

    override fun onAttachFragment(fragment: Fragment) {
        val originalAlarm = Settings.getAlarmDateTime(this@MainActivity) ?:
            LocalDateTime.now()
        when (fragment.tag) {
            "time" -> {
                val picker = TimePickerDialog(this@MainActivity, {_, hour, minute ->
                    setTime(LocalDateTime.of(originalAlarm.toLocalDate(), LocalTime.of(hour, minute)))
                }, originalAlarm.hour, originalAlarm.minute, false)
                (fragment as PickerFragment).picker = picker
            }
            "date" -> {
                val picker = DatePickerDialog(this@MainActivity, {_, year, month, day ->
                    setDate(LocalDateTime.of(LocalDate.of(year, month + 1, day),
                        originalAlarm.toLocalTime()))
                }, originalAlarm.year, originalAlarm.monthValue - 1, originalAlarm.dayOfMonth)
                (fragment as PickerFragment).picker = picker
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

    private fun onMusicPickerClicked() {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            .putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Settings.getMusic(this))
            .putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.music_picker_title))
            .putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
            .putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            .putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
        startActivityForResult(intent, musicPickerRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return
        when (requestCode) {
            musicPickerRequestCode -> {
                val music = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                        as Uri?
                Settings.setMusic(this, music)
                setMusicText(music)
            }
        }
    }

    private fun onTimeSwitchClicked(on: Boolean) {
        time_picker.isEnabled = on
        date_picker.isEnabled = on
        Settings.setAlarmOn(this, on)
        val alarmTime = Settings.getAlarmDateTime(this)

        if (!on)
            Notifications.cancelAlarm(this)
        else if (alarmTime != null)
            Notifications.setAlarm(this, alarmTime)
    }
}
