package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserPreferences
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.PreferencesDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.UserDto

fun AccountDto.toDomain() = Account(
    name = this.name,
    balance = this.balance,
    currency = this.currency,
    description = this.description,
    liquid = this.liquid,
    type = this.type
)

fun UserDto.toDomain() = User(
    email = this.email,
    username = this.username
)

fun PreferencesDto.toDomain() = UserPreferences(
    currency = this.currency,
    locale = this.locale,
    darkMode = this.darkMode
)
