package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserPreferences
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.UserNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

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

    fun loginGoogleUser(idToken: GoogleIdToken) {
        val payload: GoogleIdToken.Payload = idToken.payload
        userRepository.findUserByEmail(payload.email) ?: userRepository.save(
            User(
                email = payload.email,
                username = payload["name"]?.toString(),
                firstName = payload["given_name"]?.toString(),
                lastName = payload["family_name"]?.toString()
            )
        )
    }

    fun loadUserByUsername(username: String): User {
        return userRepository.findUserByEmail(username) ?: throw UserNotFoundException()
    }
}