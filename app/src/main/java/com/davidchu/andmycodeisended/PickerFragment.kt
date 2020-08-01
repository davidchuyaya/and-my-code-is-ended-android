package com.davidchu.andmycodeisended

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

//Needs to be separate class because Android requires Fragments to be
class PickerFragment: DialogFragment() {
    lateinit var picker: Dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return picker
    }
}