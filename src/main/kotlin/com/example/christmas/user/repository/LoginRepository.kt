package com.example.christmas.user.repository

import com.example.christmas.user.model.domain.LoginDomain
import org.springframework.data.repository.CrudRepository

interface LoginRepository : CrudRepository<LoginDomain, Long> {
    fun existsByIdAndPwAndUserType(id: String, pw: String, userType: Char) : Boolean
}