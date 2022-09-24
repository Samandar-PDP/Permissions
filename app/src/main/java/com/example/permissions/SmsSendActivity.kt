package com.example.permissions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.permissions.model.Contact

class SmsSendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_send)

        findViewById<TextView>(R.id.textView).text = intent.getParcelableExtra<Contact>("contact").toString()
    }
}