package com.example.christmas.utils

data class ApiUtils(
    val success: Boolean = true,
    val data: Map<String, Any>? = null,
    val error: ErrorUtils? = null
) {
    data class ErrorUtils(
        val code: String = "NONE",
        val message: String = ""
    )
}