package com.example.christmas.user.model.request

import javax.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "INVALID_VALUE!@#id")
    val id: String = "",
    @field:NotBlank(message = "INVALID_VALUE!@#password")
    val password: String = ""
)