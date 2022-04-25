package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.json.webtoken.JsonWebSignature
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture.Companion.preferences
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture.Companion.user
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.UserNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.models.UserDetails
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.UserRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@ExtendWith(MockKExtension::class)
internal class UserServiceTest {

    @MockK
    private lateinit var securityContext: SecurityContext

    @MockK
    private lateinit var authentication: Authentication

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
    fun loadUser_notFound_exceptionIsThrown() {
        every { userRepository.findUserByEmail(any()) } returns null
        assertThrows<UserNotFoundException> { userService.loadUserByUsername("me@mail.com") }
    }
    @Test
    fun loadUser_found() {
        every { userRepository.findUserByEmail(any()) } returns user()
        val user = userService.loadUserByUsername("me@mail.com")
        assertAll(
            { assertThat(user.email).isEqualTo("me@mail.com") }
        )
    }

    @Test
    fun getLoggedInUser() {
        var user: User? = null
        mockkStatic(SecurityContextHolder::class) {
            every { userRepository.findUserByEmail(any()) } returns user()
            every { SecurityContextHolder.getContext() } returns securityContext
            every { securityContext.authentication } returns authentication
            every { authentication.principal } returns UserDetails(id = UUID.randomUUID(), "me@mail.com")

            user = userService.getLoggedInUser()
        }

        unmockkStatic(SecurityContextHolder::class)
        assertAll(
            { assertThat(user!!.email).isEqualTo("me@mail.com") },
            { verify(exactly = 1) { userRepository.findUserByEmail("me@mail.com") } }
        )
    }

    @Test
    fun updatePreferences() {
        val updatedUser = slot<User>()
        val user = user()
        val newPreferences =
            preferences(darkMode = false, locale = Locale.GERMAN, currency = Currency.getInstance("USD"))
        every { userRepository.findUserByEmail(any()) } returns user
        every { userRepository.save(capture(updatedUser)) } returns user

        mockkStatic(SecurityContextHolder::class) {
            every { SecurityContextHolder.getContext() } returns securityContext
            every { securityContext.authentication } returns authentication
            every { authentication.principal } returns UserDetails(id = UUID.randomUUID(), "me@mail.com")

            userService.updatePreferences(newPreferences)
        }

        unmockkStatic(SecurityContextHolder::class)

        assertAll(
            { verify(exactly = 1) { userRepository.findUserByEmail("me@mail.com") } },
            { verify(exactly = 1) { userRepository.save(any()) } },
            { assertThat(updatedUser.captured.id).isEqualTo(user.id) },
            { assertThat(updatedUser.captured.email).isEqualTo(user.email) },
            { assertThat(updatedUser.captured.preferences.darkMode).isFalse() },
            { assertThat(updatedUser.captured.preferences.locale).isEqualTo(Locale.GERMAN) },
            { assertThat(updatedUser.captured.preferences.currency).isEqualTo(Currency.getInstance("USD")) }
        )
    }
    @Test
    fun loginGoogleUser() {
        every { userRepository.findUserByEmail(any()) } returns user()

        userService.loginGoogleUser(UserFixture.googleToken())

        assertAll(
            { verify(exactly = 1) { userRepository.findUserByEmail("me@mail.com") } },
            { verify(exactly = 0) { userRepository.save(any()) } }
        )
    }
    @Test
    fun loginGoogleUser_notExisting_created() {
        val user = slot<User>()
        every { userRepository.findUserByEmail(any()) } returns null
        every { userRepository.save(capture(user)) } returns user()

        userService.loginGoogleUser(UserFixture.googleToken())

        assertAll(
            { verify(exactly = 1) { userRepository.findUserByEmail("me@mail.com") } },
            { verify(exactly = 1) { userRepository.save(any()) } },
            { assertThat(user.captured.email).isEqualTo("me@mail.com") },
            { assertThat(user.captured.username).isEqualTo("me@mail.com") },
            { assertThat(user.captured.firstName).isEqualTo("Cédric") },
            { assertThat(user.captured.lastName).isEqualTo("Mauquoi") },
            { assertThat(user.captured.preferences.locale).isEqualTo(Locale.UK) },
        )
    }
}