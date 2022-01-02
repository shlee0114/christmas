package com.example.christmas.user.model.request

import javax.validation.constraints.NotBlank

data class SellerRequest(
    val login: LoginRequest = LoginRequest(),
    val seller: Seller = Seller()
) {
    data class Seller(
        @field:NotBlank(message = "INVALID_VALUE!@#name")
        val name: String = "",
        @field:NotBlank(message = "INVALID_VALUE!@#representativeName")
        val representativeName: String = "",
        @field:NotBlank(message = "INVALID_VALUE!@#phoneNumber")
        val phoneNumber: String = "",
        @field:NotBlank(message = "INVALID_VALUE!@#crn")
        val crn: String = "",
        @field:NotBlank(message = "INVALID_VALUE!@#email")
        val email: String = "",
        @field:NotBlank(message = "INVALID_VALUE!@#address")
        val address: String = "",
        @field:NotBlank(message = "INVALID_VALUE!@#addressDetail")
        val addressDetail: String = ""
    )
}