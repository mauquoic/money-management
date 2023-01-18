package com.mauquoi.moneymanagement.moneymanagement.gateways.dto

import java.time.LocalDate

data class CurrencyLookupDto(val base: String, val date: LocalDate, val rates: Map<String, Double>)
