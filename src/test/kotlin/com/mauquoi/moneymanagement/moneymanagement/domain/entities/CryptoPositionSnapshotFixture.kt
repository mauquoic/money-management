package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.mauquoi.moneymanagement.moneymanagement.domain.models.ChangeType
import java.time.LocalDate
import java.util.*

class CryptoPositionSnapshotFixture {
    companion object {
        fun positionSnapshot(
            id: UUID = UUID.randomUUID(),
            startAmount: Double = 1000.0,
            endAmount: Double = 1000.0,
            cryptoPosition: CryptoPosition,
            startDate: LocalDate = LocalDate.now(),
            endDate: LocalDate = LocalDate.now(),
            type: ChangeType = ChangeType.LENDING_REWARD
        ): CryptoPositionSnapshot {
            return CryptoPositionSnapshot(
                id = id,
                startAmount = startAmount,
                endAmount = endAmount,
                cryptoPosition = cryptoPosition,
                startDate = startDate,
                endDate = endDate,
                type = type
            )
        }
    }
}