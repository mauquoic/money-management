package com.mauquoi.moneymanagement.moneymanagement.domain.repositories

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CryptoPositionRepository : JpaRepository<CryptoPosition, UUID>{
}