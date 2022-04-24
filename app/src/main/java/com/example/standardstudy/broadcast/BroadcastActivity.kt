package com.example.standardstudy.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.standardstudy.R
import com.example.standardstudy.databinding.ActivityBroadcastBinding

class BroadcastActivity : AppCompatActivity() {

    private var powerConnectionReceiver : PowerConnectionReceiver?= null
    private var batteryStatus : Intent? = null
    private val binding : ActivityBroadcastBinding by lazy { ActivityBroadcastBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        registerBroadcast()

    }

    override fun onPause() {
        super.onPause()

        unregisterBroadcast()
    }

    private fun registerBroadcast() {
        powerConnectionReceiver = PowerConnectionReceiver()

        val powerConnectionFilter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }

        batteryStatus = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        registerReceiver(powerConnectionReceiver, powerConnectionFilter)
    }

    private fun unregisterBroadcast() {
        unregisterReceiver(powerConnectionReceiver)
    }

    fun setBatteryState() {
        val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) ||
                (status == BatteryManager.BATTERY_STATUS_FULL)
        val chargePlug = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
        val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
        val batteryPct = batteryStatus?.let { intent ->
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level / scale.toFloat()
        }
        val batteryPercent = batteryPct?.times(100)?.toInt() ?: 0

        var result = ""

        if (isCharging) {
            result += "충전 중입니다."
            binding.imgBattery.setImageResource(R.drawable.ic_battery_charge)
            if (usbCharge) result = "USB $result"
            if (acCharge) result = "AC $result"
        } else {
            result += "충전 중이 아닙니다."
            binding.imgBattery.setImageResource(R.drawable.ic_battery)
        }

        result += " ($batteryPercent %)"
        if (batteryPercent <= 30) {
            binding.imgBattery.setImageResource(R.drawable.ic_battery_row)
        }

        binding.txtBattery.text = result

    }

    inner class PowerConnectionReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Handler(Looper.getMainLooper()).postDelayed({
                setBatteryState()
            }, 100)
        }
    }

}

// https://developer.android.com/training/monitoring-device-state/battery-monitoring?hl=ko
// http://jinyongjeong.github.io/2018/09/27/bluetoothpairing/
