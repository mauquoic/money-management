package com.mauquoi.moneymanagement.moneymanagement.domain.repositories

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {
}