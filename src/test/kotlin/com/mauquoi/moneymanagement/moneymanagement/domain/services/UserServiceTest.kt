package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture.Companion.preferences
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture.Companion.user
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.UserNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class UserServiceTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var userService: UserService

    @Test
    fun createUser() {
        val user = user()
        every { userRepository.save(any()) } returns user
        userService.createUser(user)

        assertAll(
            { verify(exactly = 1) { userRepository.save(user) } }
        )
    }

    @Test
    fun getUser() {
        val id = UUID.randomUUID()
        every { userRepository.findById(any()) } returns Optional.of(user())

        userService.getUser(id)

        assertAll(
            { verify(exactly = 1) { userRepository.findById(id) } }
        )
    }

    @Test
    fun getUser_notFound_exceptionIsThrown() {
        every { userRepository.findById(any()) } returns Optional.empty()
        assertThrows<UserNotFoundException> { userService.getUser(UUID.randomUUID()) }
    }

    @Test
    fun updatePreferences() {
        val updatedUser = slot<User>()
        val user = user()
        val newPreferences = preferences(darkMode = false, locale = Locale.GERMAN, currency = Currency.getInstance("USD"))
        every { userRepository.findById(any()) } returns Optional.of(user)
        every { userRepository.save(capture(updatedUser)) } returns user

        userService.updatePreferences(user.id!!, newPreferences)

        assertAll(
            { verify(exactly = 1) { userRepository.save(any()) } },
            { assertThat(updatedUser.captured.id).isEqualTo(user.id) },
            { assertThat(updatedUser.captured.email).isEqualTo(user.email) },
            { assertThat(updatedUser.captured.preferences.darkMode).isFalse() },
            { assertThat(updatedUser.captured.preferences.locale).isEqualTo(Locale.GERMAN) },
            { assertThat(updatedUser.captured.preferences.currency).isEqualTo(Currency.getInstance("USD")) }
        )
    }
}