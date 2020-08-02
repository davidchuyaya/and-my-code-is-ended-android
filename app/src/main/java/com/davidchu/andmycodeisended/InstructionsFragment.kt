package com.davidchu.andmycodeisended

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_instructions.*

class InstructionsFragment : Fragment() {
    companion object Extras {
        const val Text = "text"
        const val Image = "image"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instructions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textRes = arguments?.getInt(Text)
        val imageRes = arguments?.getInt(Image)
        if (textRes == null || imageRes == null) {
            Log.e("InstructionsFragment", "Null text or image resource")
            return
        }

        text.text = getString(textRes)
        image.setImageResource(imageRes)
    }
}