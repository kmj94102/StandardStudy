package com.example.standardstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.standardstudy.broadcast.BroadcastActivity
import com.example.standardstudy.coroutine.CoroutineActivity
import com.example.standardstudy.retrofit.RetrofitActivity
import com.example.standardstudy.service.ServiceActivity
import com.example.standardstudy.util.startActivity
import com.example.standardstudy.workmanager.WorkManagerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(R.id.btnService, ServiceActivity::class.java)
        startActivity(R.id.btnBroadcast, BroadcastActivity::class.java)
        startActivity(R.id.btnWorkManager, WorkManagerActivity::class.java)
        startActivity(R.id.btnRetrofit, RetrofitActivity::class.java)
        startActivity(R.id.btnCoroutine, CoroutineActivity::class.java)

    }

}





