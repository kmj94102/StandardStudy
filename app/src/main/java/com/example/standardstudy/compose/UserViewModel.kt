package com.example.standardstudy.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardstudy.network.ktor.UserRepo
import com.example.standardstudy.network.ktor.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val usersFlow = MutableStateFlow<List<User>>(emptyList())

    init {
        viewModelScope.launch {
            // 에러 발생 잡기 위한 runCatching
            kotlin.runCatching {
                UserRepo.fetchUsers()
            }.onSuccess {
                usersFlow.value = it
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

}