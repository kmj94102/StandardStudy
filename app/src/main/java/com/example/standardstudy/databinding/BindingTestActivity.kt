package com.example.standardstudy.databinding

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableInt
import com.example.standardstudy.R
import com.example.standardstudy.util.toast

class BindingTestActivity : AppCompatActivity() {

    private val binding : ActivityBindingTestBinding by lazy {ActivityBindingTestBinding.inflate(layoutInflater)}
    private var number : ObservableInt = ObservableInt(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.lifecycleOwner = this
//        binding.setVariable(BR._all, null)

        binding.number = number
        binding.activity = this

    }

    fun setIncrease(view: View) {
        number.set(number.get() + 1)
    }

    fun setIncrease(increase: Int) {
        number.set(number.get() + increase)
    }

}

@BindingAdapter("increaseText")
fun increaseText(view: TextView, number: Int) {
    view.text = view.context.getString(R.string.increase, number)
}

@BindingAdapter("increaseButtonClick")
fun increaseButtonClickListener(view: View, activity: Activity) {
    view.setOnClickListener {
        activity.toast("increaseButton을 클릭하였습니다.")
    }
}
