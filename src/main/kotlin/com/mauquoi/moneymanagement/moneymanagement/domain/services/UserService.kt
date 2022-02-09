package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserPreferences
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.UserNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject

@Service
class UserService @Inject constructor(private val userRepository: UserRepository) {

    fun createUser(user: User): User {
        return userRepository.save(user);
    }

    fun getUser(userId: UUID): User {
        return userRepository.findById(userId).orElseThrow { UserNotFoundException() }
    }

    fun updatePreferences(userId: UUID, newPreferences: UserPreferences) {
        val user = getUser(userId)
        val updatedUser = user.copy(preferences = newPreferences)
        userRepository.save(updatedUser)
    }
}