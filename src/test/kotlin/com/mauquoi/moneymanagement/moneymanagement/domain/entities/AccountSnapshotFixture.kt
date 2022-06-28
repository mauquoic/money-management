package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import java.time.LocalDate
import java.util.*

class AccountSnapshotFixture {
    companion object {
        fun snapshot(
            id: UUID = UUID.randomUUID(),
            balance: Double = 1000.0,
            validFrom: LocalDate = LocalDate.now(),
            validTo: LocalDate = LocalDate.now(),
            account: Account
        ): AccountSnapshot {
            return AccountSnapshot(
                id = id,
                balance = balance,
                validFrom = validFrom,
                validTo = validTo,
                account = account
            )
        }
    }
}