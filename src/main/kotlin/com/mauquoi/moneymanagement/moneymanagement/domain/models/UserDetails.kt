package com.mauquoi.moneymanagement.moneymanagement.domain.models

import java.util.UUID

data class UserDetails (
    val id: UUID,
    val email: String
)