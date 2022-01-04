package com.example.christmas.user

import com.example.christmas.user.model.request.SellerRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/login")
    fun login(@RequestBody user: SellerRequest) {
        userService.login(user.login, '1')
    }
}