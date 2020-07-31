package com.davidchu.andmycodeisended

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.time.LocalDate

class DatePickerFragment: DialogFragment() {
    lateinit var date: LocalDate
    lateinit var setDateCallback: (LocalDate?) -> Unit

    fun setDateAndCallback(date: LocalDate, setDateCallback: (LocalDate?) -> Unit) {
        this.date = date
        this.setDateCallback = setDateCallback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(context!!,
            {_, year, month, day -> setDateCallback(LocalDate.of(year, month + 1, day))},
            date.year, date.monthValue - 1, date.dayOfMonth)
    }
}