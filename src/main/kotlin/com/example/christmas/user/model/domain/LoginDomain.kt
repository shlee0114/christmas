package com.example.christmas.user.model.domain

import javax.persistence.*

@Entity(name = "user")
data class LoginDomain(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq : Long,
    val id : String,
    val pw : String,
    val userType : Char = '0'
) {
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString() = super.toString()
}
