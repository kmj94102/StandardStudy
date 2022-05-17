package com.example.standardstudy.aac

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardstudy.network.address.data.SearchAddressResult
import com.example.standardstudy.repository.CoroutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AacViewModel @Inject constructor(
    private val repository: CoroutineRepository
) : ViewModel() {

    private val _stateLiveData = MutableLiveData<AddressState>(AddressState.Uninitialized)
    val stateLiveData : LiveData<AddressState>
        get() = _stateLiveData

    fun searchAddress(keyword: String) = viewModelScope.launch {
        val result = repository.selectAddress(keyword = keyword, currentPage = 1)
        if (result.isNullOrEmpty()) {
            _stateLiveData.postValue(AddressState.Error("조회 결과가 없습니다."))
        } else {
            _stateLiveData.postValue(AddressState.Address(result))
        }
    }

    sealed class AddressState {

        object Uninitialized : AddressState()

        data class Address(
            val list : List<SearchAddressResult>
        ) : AddressState()

        data class Error(
            val msg : String
        ) : AddressState()

    }

}