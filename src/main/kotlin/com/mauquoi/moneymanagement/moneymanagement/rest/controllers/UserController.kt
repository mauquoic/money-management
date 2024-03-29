package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.mauquoi.moneymanagement.moneymanagement.domain.services.UserService
import com.mauquoi.moneymanagement.moneymanagement.mappers.UserMapper
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.PreferencesDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.UserDto
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toDomain
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.inject.Inject


@RestController
@RequestMapping("/users")
@CrossOrigin
class UserController @Inject constructor(
    private val userService: UserService,
    private val googleIdTokenVerifier: GoogleIdTokenVerifier,
    private val userMapper: UserMapper
) {

    @PostMapping
    fun createUser(@RequestBody userDto: UserDto): ResponseEntity<UUID> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto.toDomain()).id)
    }

    @GetMapping("/me")
    fun getUser(): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userMapper.toDto(userService.getLoggedInUser()))
    }

    @PutMapping("/me/preferences")
    fun updatePreferences(
        @RequestBody preferencesDto: PreferencesDto
    ): ResponseEntity<Nothing> {
        userService.updatePreferences(preferencesDto.toDomain())
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/login")
    fun getLoginInfo(
        @RequestHeader(value = "Authorization") idTokenString: String
    ): ResponseEntity<Nothing> {
        val idToken: GoogleIdToken = googleIdTokenVerifier.verify(idTokenString.substring(7))
        userService.loginGoogleUser(idToken)
        return ResponseEntity.noContent().build()
    }
}
