package com.davidchu.andmycodeisended

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class InstructionsAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    private val textRes = arrayOf(R.string.instructions_0, R.string.instructions_1, R.string.instructions_2, R.string.instructions_3)
    private val imageRes = arrayOf(R.drawable.ic_code_wait, R.drawable.ic_code_black_24dp, R.drawable.ic_hotel_24, R.drawable.ic_launcher_foreground_raw)

    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): Fragment {
        val fragment = InstructionsFragment()
        val arguments = Bundle()
        arguments.putInt(InstructionsFragment.Text, textRes[position])
        arguments.putInt(InstructionsFragment.Image, imageRes[position])
        fragment.arguments = arguments
        return fragment
    }
}