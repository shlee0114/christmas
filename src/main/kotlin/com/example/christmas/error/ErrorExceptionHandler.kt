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
    fun customErrorHandler(e: CommonException): ResponseEntity<ApiUtils> {
        log.error(e.message)
        return ResponseEntity(ErrorMessage.getErrorMessage(e.message ?: ""), e.status)
    }

    @ExceptionHandler(IllegalStateException::class, IllegalArgumentException::class)
    fun badRequestHandler(e: RuntimeException): ResponseEntity<ApiUtils> {
        log.error(e.message)
        return ResponseEntity(ErrorMessage.getErrorMessage(e.message ?: ""), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun notFoundHandler(e: RuntimeException): ResponseEntity<ApiUtils> {
        log.error(e.message)
        return ResponseEntity(ErrorMessage.getErrorMessage(e.message ?: ""), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RuntimeException::class)
    fun error5xxHandler(e: RuntimeException): ResponseEntity<ApiUtils> {
        log.error(e.message)
        return ResponseEntity(ErrorMessage.getErrorMessage(e.message ?: ""), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}