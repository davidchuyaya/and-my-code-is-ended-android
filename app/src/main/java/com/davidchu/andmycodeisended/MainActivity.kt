package com.davidchu.andmycodeisended

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
    }
}
