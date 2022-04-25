package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.*
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.*

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

fun CryptoPosition.toDto() = CryptoPositionDto(
    id = this.id!!,
    name = this.name,
    amount = this.amount,
    description = this.description,
    addedOn = this.addedOn,
    editedOn = this.editedOn,
    positionSnapshots = this.positionSnapshots.map { it.toDto() }
)

fun CryptoPositionDto.toDomain() = CryptoPosition(
    name = this.name,
    amount = this.amount,
    description = this.description
)

fun CryptoPositionSnapshot.toDto(): CryptoPositionSnapshotDto {
    val (id, startAmount, endAmount, startDate, endDate, _, type) = this
    return CryptoPositionSnapshotDto(
        id = id,
        startAmount = startAmount,
        endAmount = endAmount,
        startDate = startDate,
        increase = endAmount - startAmount,
        endDate = endDate, type = type
    )
}

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

