package com.example.standardstudy

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.standardstudy.aac.AacActivity
import com.example.standardstudy.antonio.AntonioActivity
import com.example.standardstudy.broadcast.BroadcastActivity
import com.example.standardstudy.compose.ComposeActivity
import com.example.standardstudy.coroutine.CoroutineActivity
import com.example.standardstudy.databinding.BindingTestActivity
import com.example.standardstudy.hilt.HiltActivity
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
        startActivity(R.id.btnHilt, HiltActivity::class.java)
        startActivity(R.id.btnAac, AacActivity::class.java)
        startActivity(R.id.btnDataBinding, BindingTestActivity::class.java)
        startActivity(R.id.btnAntonio, AntonioActivity::class.java)
        startActivity(R.id.btnCompose, ComposeActivity::class.java)

        findViewById<Button>(R.id.btnCompose).performClick()

    }

}





