package com.example.christmas.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt.token")
data class JwtTokenConfig(
    var header: String = "",
    var issuer: String = "",
    var clientSecret: String = "",
    var expirySeconds: String = "",
)