package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.AccountNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.services.AccountService
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDtoFixture
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.util.*

@WebMvcTest(AccountController::class)
@ActiveProfiles("test")
internal class AccountControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var accountService: AccountService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun getAccount() {
        val id = UUID.randomUUID()
        every { accountService.getAccount(any()) } returns AccountFixture.account(id = id)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/$id")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`("name")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.`is`("description")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.addedOn", CoreMatchers.`is`(LocalDate.now().toString())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currency", CoreMatchers.`is`("USD")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(id.toString())))

        assertAll(
            { verify(exactly = 1) { accountService.getAccount(id) } }
        )
    }

    @Test
    fun getAccount_accountNotFound_404Returned() {
        val id = UUID.randomUUID()
        every { accountService.getAccount(any()) } throws AccountNotFoundException()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/$id")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun createAccount() {
        val capturedAccount = slot<Account>()
        val accountDto = AccountDtoFixture.accountDto(amount = 100.0)
        every { accountService.createAccount(capture(capturedAccount)) } returns AccountFixture.account(amount = 100.0)

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(accountDto)))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("currency", CoreMatchers.`is`("USD")))
            .andExpect(MockMvcResultMatchers.jsonPath("amount", CoreMatchers.`is`(100.0)))

        assertAll(
            { assertThat(capturedAccount.captured.amount).isEqualTo(100.0) },
            { assertThat(capturedAccount.captured.description).isNull() }
        )
    }
}