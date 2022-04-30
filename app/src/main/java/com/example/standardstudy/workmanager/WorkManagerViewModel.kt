package com.example.standardstudy.workmanager

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardstudy.MutableEventFlow
import com.example.standardstudy.asEventFlow
import com.example.standardstudy.network.address.data.AddressParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkManagerViewModel @Inject constructor(
    private val repository: WorkMangerRepository
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<AddressEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private var _currentPage = 1
    var isLoading : ObservableBoolean = ObservableBoolean(false)

    fun getAddressList(keyword: String, currentPage: Int) = viewModelScope.launch {
        isLoading.set(true)

        repository.getAddress(keyword, currentPage)?.let {
            if (it.results.common.errorCode == "0" && it.results.address != null) {
                val list = it.results.address.mapNotNull { addressList ->  addressList.roadAddr }
                val isMoreData = it.results.common.totalCount?.toInt() ?: 0 > (it.results.common.countPerPage ?: 10) * currentPage
                event(AddressEvent.AddressList(list, isMoreData))
            } else {
                event(AddressEvent.Error)
            }

            isLoading.set(false)
            _currentPage++
        }

    }

    fun event(event: AddressEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class AddressEvent {
        data class AddressList(val list : List<String>, val isMoreData : Boolean) : AddressEvent()
        object Error : AddressEvent()
    }
}