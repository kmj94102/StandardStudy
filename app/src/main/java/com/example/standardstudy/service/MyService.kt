package com.example.standardstudy.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.widget.Toast
import com.example.standardstudy.R
import java.lang.Exception

class MyService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var serviceLooper: Looper?= null
    private var serviceHandler: ServiceHandler?= null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            try {
                mediaPlayer = MediaPlayer.create(this@MyService, R.raw.music_02).also {
                    it.seekTo(215000)
                    it.start()
                    it.setOnCompletionListener {
                        mediaPlayer?.stop()
                        mediaPlayer = null
                        stopSelf()
                    }
                }
            } catch (e : Exception) {
                mediaPlayer?.stop()
                mediaPlayer = null
                e.printStackTrace()
                stopSelf()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
            START_STICKY
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()

        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroy()
    }

}