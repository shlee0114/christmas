package com.example.christmas.user.model.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "user")
data class LoginDomain(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq : Long,
    val id : String,
    val pw : String,
    val userType : Char = '0',
    val deletedAt : LocalDateTime? = null,
    val useYn : Char = 'Y'
) {
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString() = super.toString()
}
