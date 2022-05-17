package com.example.standardstudy.aac

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.standardstudy.databinding.ActivityAacBinding
import com.example.standardstudy.util.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AacActivity : AppCompatActivity() {

    private val binding: ActivityAacBinding by lazy { ActivityAacBinding.inflate(layoutInflater) }
    private val viewModel: AacViewModel by viewModels()
    @Inject lateinit var pref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeData()

    }

    private fun observeData() {
        viewModel.stateLiveData.observe(this) {
            when(it) {
                is AacViewModel.AddressState.Uninitialized -> {
                    initViews()
                }

                is AacViewModel.AddressState.Address -> {
                    setSearchResult(it)
                }
                is AacViewModel.AddressState.Error -> {
                    binding.textView.text = it.msg
                }
            }
        }
    }

    private fun initViews() = with(binding) {

        button.setOnClickListener {
            viewModel.searchAddress(editText.text.toString())
        }

        val history = getPreference(SEARCH_HISTORY, "") ?: ""

        if (history.isNotEmpty()) {
            editText.setText(history)
            button.performClick()
        }

    }

    private fun setSearchResult(addressState: AacViewModel.AddressState.Address) = with(binding) {
        var road = ""
        addressState.list.forEach { result ->
            road += "[${result.roadAddr}] ${result.roadAddr}\n"
        }
        binding.textView.text = road

        setPreference(SEARCH_HISTORY, binding.editText.text.toString())
    }

    private fun Activity.setPreference(key: String, value : String) {
        pref.edit().putString(key, value).apply()
    }

    private fun Activity.getPreference(key: String, defaultValue: String) =
        pref.getString(key, defaultValue)

    companion object {
        const val SEARCH_HISTORY = "search_history"
    }


}