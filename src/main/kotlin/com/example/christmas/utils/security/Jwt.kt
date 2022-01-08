package com.example.christmas.utils.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Component
import java.util.*


class Jwt(
    private val issuer: String,
    private val clientSecret: String,
    private val expirySeconds: Int
) {
    private val algorithm = Algorithm.HMAC512(clientSecret)
    private val jwtVerifier = JWT.require(Algorithm.HMAC512(clientSecret))
        .withIssuer(issuer)
        .build()

    fun create(claims: Claims) =
        JWT.create()
            .withIssuer(issuer)
            .withIssuedAt(Date())
            .withExpiresAt()
            .withClaim("userId", claims.userId)
            .withClaim("userName", claims.userName)
            .withClaim("roles", claims.roles)
            .sign(algorithm)

    private fun JWTCreator.Builder.withExpiresAt() =
        if(expirySeconds > 0)
            withExpiresAt(Date(Date().time + expirySeconds * 1_000L))
        else
            this

    fun verify(token: String) =
        Claims(jwtVerifier.verify(token))

    data class Claims(
        val userId: String,
        val userName: String,
        val roles: List<String>,
        val iat: Date? = null,
        val exp: Date? = null
    ) {
        constructor(decodedJWT: DecodedJWT) : this(
            userId = decodedJWT.getClaim("userId").asString(),
            userName = decodedJWT.getClaim("userName").asString(),
            roles = decodedJWT.getClaim("roles").asList(String::class.java),
            iat = decodedJWT.issuedAt,
            exp = decodedJWT.expiresAt,
        )

        companion object {
            fun of(userId: String, userName: String, roles: List<String>) =
                Claims(userId, userName, roles)
        }

        override fun equals(other: Any?) = super.equals(other)
        override fun hashCode() = super.hashCode()
    }
}