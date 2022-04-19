package com.example.standardstudy.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.standardstudy.R
import java.lang.Exception

class MyBindService : Service() {

    private val binder : IBinder = MyServiceBinder()
    private var mediaPlayer : MediaPlayer?= null

    inner class MyServiceBinder : Binder() {
        val service : MyBindService
            get() = this@MyBindService
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    fun play() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.music_02).also {
                it.seekTo(215000)
                it.start()
                it.setOnCompletionListener {
                    mediaPlayer?.stop()
                    mediaPlayer = null
                }
            }
        } catch (e : Exception) {
            mediaPlayer?.stop()
            mediaPlayer = null
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        Log.e("MyBindService", "bind Service end")
    }

}