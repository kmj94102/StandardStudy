package com.example.standardstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.standardstudy.service.ServiceActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnService).setOnClickListener { startActivity(ServiceActivity::class.java) }

    }

    private fun <T> startActivity(clazz: Class<T>) {
        Intent(this, clazz).also {
            startActivity(it)
        }
    }

}

//     /* 네트워크, 배터리 충전, 블루투스 Receiver 등록 */
//    override fun onResume() {
//        super.onResume()
//        volumeControlStream = AudioManager.STREAM_MUSIC
//        registerNetworkCallback()
//        registerPowerConnectionReceiver()
//        val filter = IntentFilter().apply {
//            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
//            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
//        }
//        registerReceiver(bluetoothReceiver, filter)
//
//        bluetoothActivation()
//    }
//
//    /* 네트워크, 배터리 충전, 블루투스 Receiver 등록 취소 */
//    override fun onStop() {
//        super.onStop()
//        unregisterNetworkCallback()
//        unregisterPowerConnectionReceiver()
//        unregisterReceiver(bluetoothReceiver)
//    }


//     /* 블루투스 BroadcastReceiver */
//    val bluetoothReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val action = intent?.action
//
//            when(action){
//                BluetoothDevice.ACTION_ACL_CONNECTED -> {
//                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
//                    binding.tvBluetooth.text = device?.name ?: "연결 된 기기 확인하기"
//                }
//                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
//                    binding.tvBluetooth.text = "연결 된 기기 확인하기"
//                }
//            }
//        }
//
//    }





