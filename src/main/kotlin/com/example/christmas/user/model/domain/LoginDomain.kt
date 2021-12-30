package com.example.christmas.user.model.domain

import com.example.christmas.user.model.request.LoginRequest
import javax.persistence.*

@Entity(name = "user")
data class LoginDomain(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long = 0,
    val id: String,
    val pw: String,
    val userType: Char = '0'
) {
    constructor(login: LoginRequest, type: Char) : this(id = login.id, pw = login.password, userType = type)

    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString() = super.toString()
}
