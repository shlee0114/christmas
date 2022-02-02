package com.example.christmas.error

import com.example.christmas.utils.ApiUtils
import com.example.christmas.utils.ErrorMessage
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorExceptionHandler {
    private val _log = LoggerFactory.getLogger(javaClass)

    private fun newResponse(throwable: Throwable, status: HttpStatus) =
        newResponse(throwable.message, status)

    private fun newResponse(message: String?, status: HttpStatus) =
        ResponseEntity(ErrorMessage.getErrorMessage(message ?: "NONE"), status)
            .also {
                _log.error(message)
            }

    @ExceptionHandler(CustomException::class)
    fun customErrorHandler(e : CustomException): ResponseEntity<ApiUtils> =
        newResponse(e, e.status)

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun badRequestHandler(e: RuntimeException): ResponseEntity<ApiUtils> =
        newResponse(e, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun badRequestBindHandler(e: BindException): ResponseEntity<ApiUtils> =
        newResponse(e.bindingResult.fieldError!!.defaultMessage, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(NoSuchElementException::class)
    fun notFoundHandler(e: NoSuchElementException): ResponseEntity<ApiUtils> =
        newResponse(e, HttpStatus.NOT_FOUND)

    @ExceptionHandler(RuntimeException::class)
    fun error5xxHandler(e: RuntimeException): ResponseEntity<ApiUtils> =
        newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR)
}