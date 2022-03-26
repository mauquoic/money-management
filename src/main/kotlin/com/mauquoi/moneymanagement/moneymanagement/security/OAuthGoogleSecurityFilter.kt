package com.mauquoi.moneymanagement.moneymanagement.security

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.mauquoi.moneymanagement.moneymanagement.domain.services.UserService
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.inject.Inject
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuthGoogleSecurityFilter @Inject constructor(
    private val googleIdTokenVerifier: GoogleIdTokenVerifier,
    private var userService: UserService
) : OncePerRequestFilter() {


    override fun shouldNotFilterAsyncDispatch(): Boolean {
        return false
    }

    override fun shouldNotFilterErrorDispatch(): Boolean {
        return false
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val headerNames = request.headerNames
        while (headerNames.hasMoreElements()) {
            val key = headerNames.nextElement()
            if (AUTHORIZATION.lowercase() == key.lowercase()) {
                val idTokenString = request.getHeader(key)
                if (idTokenString.length > 7) {
                    try {
                        val idToken: GoogleIdToken = googleIdTokenVerifier.verify(idTokenString.substring(7))
                        val user = userService.loadUserByUsername(idToken.payload.email)
                        SecurityContextHolder.getContext().authentication =
                            UsernamePasswordAuthenticationToken(user, "not-applicable", listOf())
                    } catch (e: Exception) {
                        // TODO: 21.03.22 Add exception logging
                    }
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}