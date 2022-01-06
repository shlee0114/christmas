package com.example.christmas.error

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {

    private val deniedError = "{\"success\":false,\"data\":null,\"error\":{\"code\":\"NONE\",\"message\":\"Forbidden\"}}"

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.setHeader("content-type", "application/json")
        response.writer.write(deniedError)
        response.writer.flush()
        response.writer.close()
    }
}