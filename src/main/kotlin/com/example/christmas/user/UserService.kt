package com.example.christmas.user

import com.example.christmas.user.model.request.LoginRequest
import com.example.christmas.user.repository.LoginRepository
import com.example.christmas.user.repository.SellerRepository
import com.example.christmas.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val loginRepository: LoginRepository,
    private val sellerRepository: SellerRepository,
    private val userRepository: UserRepository
) {
    fun login(loginRequest: LoginRequest, userType: Char) =
        loginRepository.existsByIdAndPwAndUserType(loginRequest.id, loginRequest.password, userType)
}