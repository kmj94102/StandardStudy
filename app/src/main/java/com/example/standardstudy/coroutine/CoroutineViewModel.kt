package com.example.standardstudy.coroutine

import androidx.lifecycle.viewModelScope
import com.example.standardstudy.BaseViewModel
import com.example.standardstudy.network.address.data.AddressData
import com.example.standardstudy.repository.CoroutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoroutineViewModel @Inject constructor(
    private val repository: CoroutineRepository
) : BaseViewModel<CoroutineViewModel.AddressEvent>(){

    fun selectAddress(keyword : String, currentPage: Int) = viewModelScope.launch {
        repository.selectAddress(keyword, currentPage)?.let {
//            event(AddressEvent.AddressResultSuccess(result = it.results))
        } ?: event(AddressEvent.Error)
    }

    override fun event(event: AddressEvent) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class AddressEvent {
        data class AddressResultSuccess(
            val result : AddressData
        ) : AddressEvent()
        object Error : AddressEvent()
    }

}