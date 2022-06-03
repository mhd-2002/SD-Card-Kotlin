package com.example.sdcard.Music

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.sdcard.R
import com.example.sdcard.databinding.ActivityMusicBinding

class Music : AppCompatActivity() {

    lateinit var binding: ActivityMusicBinding
    private var listOfSong = ArrayList<SongInfo>()
    var mp: MediaPlayer? = null
    var adapter: MySongAdapter? = null

    inner class MySongAdapter(var myListSong: ArrayList<SongInfo>) : BaseAdapter() {

        override fun getCount(): Int {
            return myListSong.size
        }

        override fun getItem(p0: Int): Any {
            return myListSong[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        @SuppressLint("InflateParams", "ViewHolder", "SetTextI18n")
        override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.list_of_items, null)
            val song = myListSong[position]

            val text1 = myView.findViewById<TextView>(R.id.tv_name)
            val text2 = myView.findViewById<TextView>(R.id.tv_name2)
            val bt = myView.findViewById<Button>(R.id.bt_play)


            text1.text = song.title
            text2.text = song.author
            bt.setOnClickListener {

                if (bt.text == "Stop") {
                    mp!!.stop()
                    bt.text = "Play"
                } else {
                    mp = MediaPlayer()

                    try {

                        mp?.setDataSource(song.url)
                        mp?.prepare()
                        mp?.start()
                        bt.text = "Stop"
                    } catch (e: Exception) {

                    }

                }

            }

            return myView


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                111
            )

        } else {
            loadSong()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSong()
        }
    }

    @SuppressLint("Recycle", "Range")
    private fun loadSong() {
        val url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"

        val rs = contentResolver.query(url, null, selection, null, null)

        if (rs != null) {
            while (rs.moveToNext()) {
                val url1 = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DATA))
                val author = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val title = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))

                listOfSong.add(SongInfo(url = url1, author = author, title = title))
            }
        }
        adapter = MySongAdapter(listOfSong)
        binding.listView.adapter = adapter
    }

    override fun onBackPressed() {
        finish()
    }
}