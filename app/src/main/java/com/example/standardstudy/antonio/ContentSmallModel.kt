package com.example.standardstudy.antonio

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import com.example.standardstudy.R
import io.github.naverz.antonio.databinding.AntonioBindingModel
import io.github.naverz.antonio.databinding.BR

data class ContentSmallModel(
    val id: String = "id",
    @DrawableRes val iconRes: Int = R.drawable.ic_battery,
    val price: Int = 0,
    val onClick: (id: String) -> Unit = {},
    val onLongClick: (id: String) -> Boolean = {false},
//    val selectedIds: LiveData<Set<String>>
) : AntonioBindingModel {
    // Layout id to be inflated
    override fun layoutId(): Int = R.layout.view_holder_content_small
    // Variable id in XML to be bind on bind view holder

    // (e.g., onBindViewHolder in ViewHolder, onViewCreated in Fragment).
    override fun bindingVariableId(): Int = BR.model
}