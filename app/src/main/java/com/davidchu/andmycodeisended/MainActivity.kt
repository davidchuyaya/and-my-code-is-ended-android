package com.davidchu.andmycodeisended

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TokenUtil.callWithNewToken {token ->
            if (token == null)
                return@callWithNewToken
            code_dynamic_text.text = getString(R.string.code_dynamic_text, token)
            Settings.setToken(this, token)
        }

        createListeners()
    }

    private fun createListeners() {
        code_clickable_area.setOnClickListener { onShareClicked() }
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
}
