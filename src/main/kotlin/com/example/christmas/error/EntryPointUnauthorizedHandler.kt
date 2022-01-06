package com.example.christmas.error

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class EntryPointUnauthorizedHandler : AuthenticationEntryPoint {

    private val unauthorizedError = "{\"success\":false,\"data\":null,\"error\":{\"code\":\"NOT_AUTHORIZED\",\"message\":\"TOKEN 값이 올바르지 않습니다.\"}}"

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.setHeader("content-type", "application/json")
        response.writer.write(unauthorizedError)
        response.writer.flush()
        response.writer.close()
    }

}