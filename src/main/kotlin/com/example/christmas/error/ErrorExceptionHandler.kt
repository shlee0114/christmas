package com.example.christmas.error

import com.example.christmas.utils.ApiUtils
import com.example.christmas.utils.ErrorMessage
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CommonException::class)
    fun errorHandler(e: CommonException): ResponseEntity<ApiUtils> {
        log.error(e.message)
        return ResponseEntity(ErrorMessage.getErrorMessage(e.message ?: ""), e.status)
    }

    @ExceptionHandler(RuntimeException::class)
    fun errorHandler5xx(e: RuntimeException): ResponseEntity<ApiUtils> {
        log.error(e.message)
        return ResponseEntity(ErrorMessage.getErrorMessage(e.message ?: ""), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}