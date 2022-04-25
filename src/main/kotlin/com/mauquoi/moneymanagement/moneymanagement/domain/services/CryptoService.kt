package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPosition
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.CryptoPositionNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.IllegalEntityAccessException
import com.mauquoi.moneymanagement.moneymanagement.domain.models.ChangeType
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoPositionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.inject.Inject

@Service
class CryptoService @Inject constructor(
    private val cryptoPositionRepository: CryptoPositionRepository,
    private val userService: UserService
) {

    fun getCryptoPosition(cryptoPositionId: UUID): CryptoPosition {
        val cryptoPosition = cryptoPositionRepository.findById(cryptoPositionId).orElseThrow { CryptoPositionNotFoundException() }
        if (cryptoPosition.user!!.id != userService.getLoggedInUserId()) {
            throw IllegalEntityAccessException()
        }
        return cryptoPosition
    }

    fun getCryptoPositions(): List<CryptoPosition> {
        return cryptoPositionRepository.findAll()
    }

    @Transactional
    fun createCryptoPosition(cryptoPosition: CryptoPosition): CryptoPosition {
        val user = userService.getLoggedInUser()

        user.addCryptoPosition(cryptoPosition)
        return cryptoPositionRepository.save(cryptoPosition)
    }

    @Transactional
    fun updateCryptoPosition(cryptoPositionId: UUID, newBalance: Double?, increase: Double?, changeType: ChangeType?) {
        val cryptoPosition = getCryptoPosition(cryptoPositionId)
        val calculatedNewBalance = newBalance ?: (cryptoPosition.amount + increase!!)
        val snapshot = cryptoPosition.createSnapshot(calculatedNewBalance, changeType)
        cryptoPosition.positionSnapshots.add(snapshot)
        val updatedCryptoPosition = cryptoPosition.updateBalance(calculatedNewBalance)
        cryptoPositionRepository.save(updatedCryptoPosition)
    }

    @Transactional
    fun editCryptoPosition(cryptoPositionId: UUID, editedCryptoPosition: CryptoPosition) {
        val cryptoPosition = getCryptoPosition(cryptoPositionId)
        val updatedCryptoPosition = editedCryptoPosition.setImmutableInfoFromCryptoPosition(cryptoPosition)
        cryptoPositionRepository.save(updatedCryptoPosition)
    }
}