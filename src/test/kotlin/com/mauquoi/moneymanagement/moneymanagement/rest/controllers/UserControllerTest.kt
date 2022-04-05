package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserPreferences
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.UserNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.services.UserService
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.UserDtoFixture
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
internal class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userService: UserService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun getUser() {
        val id = UUID.randomUUID()
        every { userService.getLoggedInUser() } returns UserFixture.user(id = id)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.`is`("me@mail.com")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(id.toString())))

        assertAll(
            { verify(exactly = 1) { userService.getLoggedInUser() } }
        )
    }

    @Test
    fun getUser_userNotFound_404Returned() {
        every { userService.getLoggedInUser() } throws UserNotFoundException()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun createUser() {
        val capturedUser = slot<User>()
        val createdUser = UserFixture.user(email = "special@mail.com")
        val userDto = UserDtoFixture.userDto(email = "special@mail.com")
        every { userService.createUser(capture(capturedUser)) } returns createdUser

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().string("\"${createdUser.id.toString()}\""))

        assertAll(
            { assertThat(capturedUser.captured.email).isEqualTo("special@mail.com") }
        )
    }

    @Test
    fun updateUserPreferences() {
        val capturedPreferences = slot<UserPreferences>()
        val preferencesDto = UserDtoFixture.preferences(locale = Locale.FRANCE)
        every { userService.updatePreferences(capture(capturedPreferences)) } just runs

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/me/preferences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preferencesDto))
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        assertAll(
            { assertThat(capturedPreferences.captured.locale).isEqualTo(Locale.FRANCE) }
        )
    }
}