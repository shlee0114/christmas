package com.example.christmas.utils.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.GenericFilterBean
import java.net.URLDecoder
import java.util.regex.Pattern
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JwtAuthenticationTokenFilter(
    private val headerKey: String,
    private val jwt: Jwt
) : GenericFilterBean() {
    companion object {
        private val BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (SecurityContextHolder.getContext().authentication == null) {
            obtainAuthorizationToken(request as HttpServletRequest)?.run {
                val claims = verify(this)

                val userId = claims.userId
                val name = claims.userName
                val authorities = obtainAuthorities(claims)

                SecurityContextHolder.getContext().authentication =
                    JwtAuthenticationToken(JwtAuthentication(userId, name), null, authorities).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }
            }
        }
        chain.doFilter(request, response)
    }

    private fun obtainAuthorities(claims: Jwt.Claims): List<GrantedAuthority> =
        if (claims.roles.isEmpty()) emptyList() else claims.roles.stream()
            .map { role: String ->
                SimpleGrantedAuthority(
                    role
                )
            }.collect(Collectors.toList())

    private fun obtainAuthorizationToken(request: HttpServletRequest): String? {
        var token = request.getHeader(headerKey)
        token = URLDecoder.decode(token, "UTF-8")
        val parts = token.split(" ".toRegex()).toTypedArray()
        if (parts.size == 2) {
            val scheme = parts[0]
            val credentials = parts[1]
            return if (BEARER.matcher(scheme).matches()) credentials else null
        }
        return null
    }

    fun verify(token: String) = jwt.verify(token)
}