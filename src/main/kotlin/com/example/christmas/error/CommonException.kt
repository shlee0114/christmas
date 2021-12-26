package com.example.christmas.error

import org.springframework.http.HttpStatus

open class CommonException(
    message: String,
    val status: HttpStatus
) : RuntimeException(message)