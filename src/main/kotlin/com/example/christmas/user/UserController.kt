package com.example.christmas.user

import com.example.christmas.user.model.request.UserRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/login")
    fun login(user: UserRequest) {
        userService.login(user.login, '0')
    }
}