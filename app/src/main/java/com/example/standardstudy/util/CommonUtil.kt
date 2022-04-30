package com.example.standardstudy.util

import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.annotation.IdRes

fun Activity.toast(message : String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun <T> Activity.startActivity(@IdRes id : Int, clazz: Class<T>) {
    findViewById<Button>(id).setOnClickListener {
        Intent(this, clazz).also {
            startActivity(it)
        }
    }
}