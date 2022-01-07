package com.example.christmas.utils.security

import javax.validation.constraints.NotBlank

data class JwtAuthentication(
    @field:NotBlank(message = "INVALID_VALUE!@#id")
    val id: String,
    @field:NotBlank(message = "INVALID_VALUE!@#name")
    val name: String
)