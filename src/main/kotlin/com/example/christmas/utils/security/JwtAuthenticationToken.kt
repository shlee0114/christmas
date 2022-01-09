package com.example.christmas.utils.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

data class JwtAuthenticationToken(
    private val principal: JwtAuthentication,
    private var credentials: String?,
    private val authorities: List<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {

    override fun getCredentials() = credentials
    override fun getPrincipal() = principal
    override fun setAuthenticated(isAuthenticated: Boolean) =
        super.setAuthenticated(false)

    override fun eraseCredentials() {
        super.eraseCredentials()
        credentials = null
    }
}
