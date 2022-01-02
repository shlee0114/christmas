package com.example.christmas.user

import com.example.christmas.user.model.request.UserRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/seller")
class SellerController(
    private val userService: UserService
) {
    @PostMapping("/login")
    fun login(@RequestBody seller: UserRequest) {
        userService.login(seller.login, '1')
    }
}