package com.mauquoi.moneymanagement.moneymanagement.security

import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.stereotype.Component

@Component
class AppWebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/actuator/*", "/accounts", "/accounts/**", "/error/**")
    }
}