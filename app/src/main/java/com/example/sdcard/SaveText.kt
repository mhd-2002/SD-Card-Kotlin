package com.example.sdcard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sdcard.databinding.ActivitySaveTextBinding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class SaveText : AppCompatActivity() {

    lateinit var binding: ActivitySaveTextBinding

    companion object {
        const val TEXT_NAME = "example.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaveTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSaveText.setOnClickListener {
            save()
        }

        binding.btShowText.setOnClickListener {
            load()
        }

    }

    fun save() {
        val text = binding.etText.text.toString()
        var fos: FileOutputStream? = null

        try {
            fos = openFileOutput(TEXT_NAME, MODE_PRIVATE)
            fos.write(text.toByteArray())

            binding.etText.text.clear()
            Toast.makeText(this, "save to $filesDir/$TEXT_NAME", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            println(e)
        } finally {
            fos?.close()

        }
    }

    fun load() {
        var fis: FileInputStream? = null
        try {
            fis = openFileInput(TEXT_NAME)

            val isr: InputStreamReader = InputStreamReader(fis)
            val br: BufferedReader = BufferedReader(isr)
            val sb: StringBuilder = StringBuilder()
            var test: String

            while ((br.readLine().also { test = it }) != null) {
                sb.append(test).append("\n")
                binding.etText.setText(sb.toString())

            }
        } catch (e: Exception) {
            println(e)
        } finally {
            fis!!.close()
        }


    }
}