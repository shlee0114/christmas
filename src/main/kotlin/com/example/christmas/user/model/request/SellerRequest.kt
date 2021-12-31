package com.example.christmas.user.model.request

data class SellerRequest(
    val login: LoginRequest = LoginRequest(),
    val seller: Seller = Seller()
) {
    data class Seller(
        val name: String = "",
        val representativeName: String = "",
        val phoneNumber: String = "",
        val crn: String = "",
        val email: String = "",
        val address: String = "",
        val addressDetail: String = ""
    )
}