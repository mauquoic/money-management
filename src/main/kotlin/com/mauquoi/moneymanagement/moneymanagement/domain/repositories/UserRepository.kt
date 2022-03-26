package com.mauquoi.moneymanagement.moneymanagement.domain.repositories

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    fun findUserByEmail(email: String): User?

}