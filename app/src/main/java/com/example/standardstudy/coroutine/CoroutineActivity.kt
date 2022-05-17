package com.example.standardstudy.coroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.standardstudy.BaseActivity
import com.example.standardstudy.R
import com.example.standardstudy.databinding.ActivityCoroutineBinding
import com.example.standardstudy.retrofit.RetrofitUtil
import com.example.standardstudy.util.repeatOnStarted
import com.example.standardstudy.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class CoroutineActivity : BaseActivity<ActivityCoroutineBinding, CoroutineViewModel>(R.layout.activity_coroutine) {

    override val viewModel: CoroutineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeatOnStarted {
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }

        initViews()

    }

    private fun initViews() = with(binding) {
        button.setOnClickListener {
            val searchText = editText.text.toString()
            if (searchText.isEmpty()) {
                toast("주소를 입력해주세요")
                return@setOnClickListener
            }

            viewModel.selectAddress(searchText, 1)
        }
    }

    private fun handleEvent(event : CoroutineViewModel.AddressEvent) {
        when(event) {
            is CoroutineViewModel.AddressEvent.AddressResultSuccess -> {
                var result = ""
                event.result.address?.forEach {
                    result += "${it.roadAddr}\n"
                }
                binding.textView.text = result
            }
            is CoroutineViewModel.AddressEvent.Error -> {
                toast("오류가 발생하였습니다.")
            }
        }
    }
}