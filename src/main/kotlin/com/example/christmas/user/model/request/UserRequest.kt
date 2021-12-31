package com.example.christmas.user.model.request

data class UserRequest(
    val login: LoginRequest = LoginRequest(),
    val user: User = User()
) {
    data class User(
        val name: String = "",
        val phoneNumber: String = ""
    )
}