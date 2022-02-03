package com.example.christmas.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val request: WebConfigRequest
) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping(request.mapping)
            .allowedHeaders(request.header)
            .allowedOrigins(request.origins)
            .allowedMethods(request.methods)
            .maxAge(request.age)
            .allowCredentials(request.credentials)
    }
}