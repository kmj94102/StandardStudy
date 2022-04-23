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

    // 클라이언트 바인더에 사용되는 클래스입니다.
    // 우리는 이 서비스가 항상 클라이언트와 동일한 프로세스에서 실행된다는 것을 알고 있기 때문에 IPC를 다룰 필요가 없습니다.
    inner class MyServiceBinder : Binder() {
        // 클라이언트가 공용 메서드를 호출할 수 있도록 이 MyBindService 인스턴스를 반환합니다.
        val service : MyBindService
            get() = this@MyBindService
    }

    override fun onBind(intent: Intent): IBinder = binder

    // 음악 재생
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

        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.release()
        Log.e("MyBindService", "bind Service end")
    }

}