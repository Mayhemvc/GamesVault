package com.example.testeteste

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var clickCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btn_click)
        val title = findViewById<TextView>(R.id.tv_title)

        button.setOnClickListener {
            clickCount++
            title.text = "Botão Clicado $clickCount vezes!"
        }
    }
}