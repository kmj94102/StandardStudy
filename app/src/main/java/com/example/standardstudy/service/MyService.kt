package com.example.standardstudy.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.widget.Toast
import com.example.standardstudy.R
import java.lang.Exception

class MyService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var serviceLooper: Looper?= null
    private var serviceHandler: ServiceHandler?= null

    // 스레드로부터 메시지를 받는 핸들러
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            try {
                mediaPlayer = MediaPlayer.create(this@MyService, R.raw.music_02).also {
                    // 테스트 편의를 위해 음악의 마지막 부분으로 이동합니다.
                    it.seekTo(215000)
                    // MediaPlayer 실행
                    it.start()
                    // MediaPlayer 종료되었을때 리스너 등록
                    it.setOnCompletionListener {
                        // MediaPlayer 가 종료되면 서비스를 종료 시킵니다.
                        stopSelf()
                    }
                }
            } catch (e : Exception) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                e.printStackTrace()
                stopSelf()
            }
        }
    }

    // 서비스는 일반적으로 우리가 차단하고 싶지 않은 프로세스의 메인 스레드에서 실행되기 때문에 별도의 스레드를 만듭니다.
    // 또한 CPU를 많이 사용하는 작업이 UI를 방해하지 않도록 백그라운드 우선 순위를 지정합니다.
    override fun onCreate() {
        super.onCreate()

        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            // HandlerThread 의 Looper 를 가져와서 ServiceHandler 에 사용
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        // 각 시작 요청에 대해 작업을 시작하라는 메시지를 보내고 시작 ID를 전달하여 작업을 완료할 때 중지할 요청을 알 수 있습니다.
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()

        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.release()
        super.onDestroy()
    }

}