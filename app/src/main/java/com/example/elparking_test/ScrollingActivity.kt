package com.example.elparking_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.elparking_test.databinding.ActivityMainBinding

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}