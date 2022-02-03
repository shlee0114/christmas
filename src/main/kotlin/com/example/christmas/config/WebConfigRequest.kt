package com.example.christmas.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "allowed")
data class WebConfigRequest(
    var mapping : String = "",
    var header : String = "",
    var origins : String = "",
    var methods : String = "",
    var age : Long = 0,
    var credentials : Boolean = false
) {
}