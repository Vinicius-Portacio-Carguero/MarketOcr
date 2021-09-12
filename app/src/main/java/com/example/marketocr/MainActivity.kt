package com.example.marketocr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketocr.service.dao.ValueDao

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ValueDao(applicationContext)
    }
}