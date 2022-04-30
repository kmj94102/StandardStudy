package com.example.standardstudy

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<B: ViewDataBinding, VM : ViewModel>(
    @LayoutRes
    private val layoutId: Int
) : AppCompatActivity() {

    abstract val viewModel : VM
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)

        with(binding) {
            lifecycleOwner = this@BaseActivity
            setVariable(BR._all, viewModel)
        }

    }

}