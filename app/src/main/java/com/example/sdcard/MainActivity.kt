package com.example.sdcard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sdcard.Music.Music
import com.example.sdcard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btMusic.setOnClickListener {
            val intent = Intent(this, Music::class.java)
            startActivity(intent)
        }

        binding.btSave.setOnClickListener {
            val intent = Intent(this, SaveText::class.java)
            startActivity(intent)
        }
    }
}
