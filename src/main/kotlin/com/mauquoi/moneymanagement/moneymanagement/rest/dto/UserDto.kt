package com.mauquoi.moneymanagement.moneymanagement.rest.dto

import java.util.*
import javax.persistence.Column
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class UserDto(
    val id: UUID? = null,
    val username: String? = null,
    @NotNull @Email val email: String,
    val preferences: PreferencesDto? = null
)

data class PreferencesDto(
    val locale: Locale,
    val currency: Currency,
    val darkMode: Boolean
)