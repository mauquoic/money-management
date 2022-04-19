package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountSnapshot
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserPreferences
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountSnapshotDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.PreferencesDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.UserDto

fun Account.toDto() = AccountDto(
    id = this.id,
    name = this.name,
    balance = this.balance,
    currency = this.currency,
    description = this.description,
    addedOn = this.addedOn,
    snapshots = this.accountSnapshots.map { it.toDto() }
)

fun AccountDto.toDomain() = Account(
    name = this.name,
    balance = this.balance,
    currency = this.currency,
    description = this.description
)

fun AccountSnapshot.toDto() = AccountSnapshotDto(
    id = this.id!!,
    balance = this.balance,
    validFrom = this.validFrom,
    validTo = this.validTo
)

fun UserDto.toDomain() = User(
    email = this.email,
    username = this.username
)

fun User.toDto() = UserDto(
    id = this.id,
    email = this.email,
    username = this.username,
    preferences = this.preferences.toDto()
)

fun UserPreferences.toDto() = PreferencesDto(
    currency = this.currency,
    locale = this.locale,
    darkMode = this.darkMode
)

fun PreferencesDto.toDomain() = UserPreferences(
    currency = this.currency,
    locale = this.locale,
    darkMode = this.darkMode
)

