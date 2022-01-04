package com.example.christmas.user

import com.example.christmas.user.model.request.LoginRequest
import com.example.christmas.user.repository.LoginRepository
import com.example.christmas.user.repository.SellerRepository
import com.example.christmas.user.repository.UserRepository
import com.example.christmas.utils.Encrypt
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Service
@Validated
class UserService(
    private val loginRepository: LoginRepository,
    private val sellerRepository: SellerRepository,
    private val userRepository: UserRepository,
    private val encrypt: Encrypt
) {
    fun login(@Valid loginRequest: LoginRequest, userType: Char) {
        loginRepository.existsByIdAndPwAndUserType(loginRequest.id, encrypt.encryptStringSHA256(loginRequest.password), userType)
    }
}