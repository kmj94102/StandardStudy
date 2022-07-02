package com.example.standardstudy.compose

data class RandomUser(
    val name: String = "이름",
    val description: String = "설명",
    val profileImage: String = "https://phinf.pstatic.net/contact/20190909_51/1568005452740b76nt_PNG/avatar_profile.png?type=s160"
)

object DummyDataProvider {

    val userList = List(200) { RandomUser() }

}