package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.mauquoi.moneymanagement.moneymanagement.domain.models.ChangeType
import java.time.LocalDate
import java.util.*

class CryptoPositionFixture {
    companion object {
        fun position(
            id: UUID = UUID.randomUUID(),
            name: String = "name",
            amount: Double = 1000.0,
            description: String = "description",
            addedOn: LocalDate = LocalDate.now(),
            editedOn: LocalDate = LocalDate.now(),
            user: User = UserFixture.user(),
            newBalance: Double = 1100.0,
            type: ChangeType = ChangeType.STAKING,
            asset: CryptoAsset = CryptoAssetFixture.cryptoAsset()
        ): CryptoPosition {
            val position = CryptoPosition(
                id = id,
                name = name,
                amount = amount,
                description = description,
                editedOn = editedOn,
                addedOn = addedOn,
                user = user,
                asset = asset
            )
            position.positionSnapshots.add(CryptoPositionSnapshotFixture.positionSnapshot(cryptoPosition = position, endAmount = newBalance, type = type))
            return position
        }
    }
}