package com.example.christmas.utils

import org.springframework.stereotype.Component
import java.lang.StringBuilder
import java.math.BigInteger
import java.security.MessageDigest

@Component
class Encrypt {
    fun encryptStringSHA256(input: String): String =
        BigInteger(
            1,
            MessageDigest.getInstance("SHA-256")
                .digest(input.toByteArray())
        ).run {
            return if (toString().length < 32) {
                val tmpHash = StringBuilder(toString(16))
                while(toString().length < 32)
                    tmpHash.insert(0, "0")
                tmpHash.toString()
            } else {
                toString(16)
            }
        }
}