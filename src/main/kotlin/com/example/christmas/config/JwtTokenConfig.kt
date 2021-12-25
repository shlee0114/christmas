package com.example.christmas.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt.token")
class JwtTokenConfig{
    lateinit var header: String
    lateinit var issuer: String
    lateinit var clientSecret: String
    lateinit var expirySeconds: String
}