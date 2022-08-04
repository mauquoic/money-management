package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserPreferences
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.UserNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.models.UserDetails
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.inject.Inject

@Service
class UserService @Inject constructor(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun getUser(userId: UUID): User {
        return userRepository.findById(userId).orElseThrow { UserNotFoundException() }
    }

    @Transactional
    fun updatePreferences(newPreferences: UserPreferences) {
        val user = getLoggedInUser()
        val updatedUser = user.copy(preferences = newPreferences)
        userRepository.save(updatedUser)
    }

    fun loginGoogleUser(idToken: GoogleIdToken) {
        val payload: GoogleIdToken.Payload = idToken.payload
        userRepository.findUserByEmail(payload.email) ?: userRepository.save(
            User(
                email = payload.email,
                username = payload.email,
                firstName = payload["given_name"]?.toString(),
                lastName = payload["family_name"]?.toString()
            )
        )
    }

    fun loadUserByUsername(username: String): User {
        return userRepository.findUserByEmail(username) ?: throw UserNotFoundException()
    }

    fun getLoggedInUser(): User {
        return userRepository.findUserByEmail((SecurityContextHolder.getContext().authentication.principal as UserDetails).email)!!
    }

    fun getLoggedInUserId(): UUID {
        return (SecurityContextHolder.getContext().authentication.principal as UserDetails).id
    }
}