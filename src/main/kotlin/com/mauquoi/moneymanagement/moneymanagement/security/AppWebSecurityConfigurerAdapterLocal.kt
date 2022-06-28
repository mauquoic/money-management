package com.mauquoi.moneymanagement.moneymanagement.security

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.session.SessionManagementFilter
import javax.inject.Inject


@Configuration
@EnableWebSecurity
@Profile("postman")
class AppWebSecurityConfigurerAdapterLocal @Inject constructor(
    private val oAuthGoogleSecurityFilter: OAuthGoogleSecurityFilter,
    private val corsFilter: CorsFilter
) :
    WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/actuator/*", "/users", "/users/**", "/error/**", "/stubs/**", "/cryptos/assets/**")
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(oAuthGoogleSecurityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(corsFilter, SessionManagementFilter::class.java)
            .csrf().disable()
    }
}