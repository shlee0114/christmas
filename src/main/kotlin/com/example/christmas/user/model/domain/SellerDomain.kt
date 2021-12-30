package com.example.christmas.user.model.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "seller_info")
data class SellerDomain(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long,
    val userId: String,
    val name: String,
    val representativeName: String,
    val phoneNumber: String,
    val crn: String,
    val email: String,
    val address: String,
    val addressDetail: String
) {
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString() = super.toString()
}
