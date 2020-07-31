package com.davidchu.andmycodeisended

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.time.LocalTime

class TimePickerFragment : DialogFragment() {
    lateinit var time: LocalTime
    lateinit var setTimeCallback: (LocalTime?) -> Unit

    fun setTimeAndCallback(time: LocalTime, setTimeCallback: (LocalTime?) -> Unit) {
        this.time = time
        this.setTimeCallback = setTimeCallback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(context!!,
            {_, hour, minute -> setTimeCallback(LocalTime.of(hour, minute))},
            time.hour, time.minute, false)
    }
}