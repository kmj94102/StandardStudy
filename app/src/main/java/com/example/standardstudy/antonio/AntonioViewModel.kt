package com.example.standardstudy.antonio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.naverz.antonio.core.AntonioModel
import io.github.naverz.antonio.core.state.RecyclerViewState
import javax.inject.Inject

@HiltViewModel
class AntonioViewModel @Inject constructor(

) : ViewModel() {

    private val _onDataSetChanged = MutableLiveData<List<AntonioModel>>()
    val onDataSetChanged : LiveData<List<AntonioModel>>
        get() = _onDataSetChanged

    val antonioModels : List<AntonioModel> = listOf()

    val test = RecyclerViewState<AntonioModel>().apply {
        currentList.addAll(
            listOf(
                ContentSmallModel(),
                ContentSmallModel(),
                ContentSmallModel(),
                ContentSmallModel(),
            )
        )
    }

//    private val fashionPageState = RecyclerViewState<AntonioModel>().apply {
//        currentList.add(ContentHeader("New items"))
////        currentList.add(ViewHolderRecyclerViewHorizontal(horizontalState))
//        currentList.addAll(contentBuilder.makeDummySmallContents(50, onClick = ::onContentClicked))
//        currentList.add(ContentHeader("Hot Items"))
//        currentList.addAll(contentBuilder.makeDummyContainersForSmallContents(onClick = ::onContentClicked))
//    }

}