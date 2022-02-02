package com.example.christmas.error

import org.springframework.http.HttpStatus

open class CustomException(
    message: String,
    val status: HttpStatus
) : RuntimeException(message)