package com.example.standardstudy.antonio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.standardstudy.R
import com.example.standardstudy.databinding.ActivityAntonioBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.databinding.adapter.AntonioAdapter

@AndroidEntryPoint
class AntonioActivity : AppCompatActivity() {

    private val binding : ActivityAntonioBinding by lazy { ActivityAntonioBinding.inflate(layoutInflater) }
    private val viewModel : AntonioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_antonio)

        binding.lifecycleOwner = this
        binding.vm = viewModel

    }

    private fun initAdapter(){
        // You also can specify the type of Antonio model for the adapter, if you don't need various view types.
        // e.g., AntonioAdapter<ContentSmallModel>()
        binding.recyclerView.adapter =
            AntonioAdapter<AntonioModel>().apply { currentList = viewModel.antonioModels }
//         Don't forget to set the layout manager to your recycler view :)
        binding.recyclerView.layoutManager = GridLayoutManager(this,4)
        viewModel.onDataSetChanged.observe(this) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

}