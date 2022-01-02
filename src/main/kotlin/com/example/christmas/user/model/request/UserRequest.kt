package com.example.christmas.user.model.request

import javax.validation.constraints.NotBlank

data class UserRequest(
    val login: LoginRequest = LoginRequest(),
    val user: User = User()
) {
    data class User(
        @field:NotBlank(message = "INVALID_VALUE!@#name")
        val name: String = "",
        @field:NotBlank(message = "INVALID_VALUE!@#phoneNumber")
        val phoneNumber: String = ""
    )
}