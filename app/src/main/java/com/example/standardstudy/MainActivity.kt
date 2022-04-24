package com.example.standardstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.IdRes
import com.example.standardstudy.broadcast.BroadcastActivity
import com.example.standardstudy.service.ServiceActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(R.id.btnService, ServiceActivity::class.java)
        startActivity(R.id.btnBroadcast, BroadcastActivity::class.java)

    }

    private fun <T> startActivity(@IdRes id : Int, clazz: Class<T>) {
        findViewById<Button>(id).setOnClickListener {
            Intent(this, clazz).also {
                startActivity(it)
            }
        }
    }

}





