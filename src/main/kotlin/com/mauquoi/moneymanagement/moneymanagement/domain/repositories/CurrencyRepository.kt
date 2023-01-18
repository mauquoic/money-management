package com.mauquoi.moneymanagement.moneymanagement.domain.repositories

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CurrencyExchange
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CurrencyRepository : JpaRepository<CurrencyExchange, LocalDate> {
}