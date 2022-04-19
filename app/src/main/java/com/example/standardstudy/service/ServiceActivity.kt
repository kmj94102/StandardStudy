package com.example.standardstudy.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import com.example.standardstudy.R

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        findViewById<Button>(R.id.btnStartService).setOnClickListener {
            Intent(this, MyService::class.java).also {
                startService(it)
            }
        }

        findViewById<Button>(R.id.btnBindService).setOnClickListener {
            service?.play()
        }

    }

    override fun onStart() {
        super.onStart()

        Intent(this, MyBindService::class.java).also {
            bindService(it, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()

        unbindService(connection)
    }

    private var service : MyBindService?= null
    private val connection : ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, iBinder: IBinder?) {
            val binder = iBinder as MyBindService.MyServiceBinder
            service = binder.service
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            service = null
        }
    }

}

// 음악 자료 출처 : https://ncs.io/music
// 자료 출처 (공식문서) : https://developer.android.com/guide/components/services?hl=ko