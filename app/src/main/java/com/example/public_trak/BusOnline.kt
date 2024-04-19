package com.example.public_trak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class BusOnline : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_online)

        val onlineBtn: Button = findViewById(R.id.online_btn)

        onlineBtn.setOnClickListener {
            // Start Offline Mode
            val intent = Intent(this, BusOffline::class.java)
            startActivity(intent)
        }
    }
}