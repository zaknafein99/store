package com.example.boutique.web

import com.example.boutique.dto.LoginRequestDTO
import com.example.boutique.dto.LoginResponseDTO
import com.example.boutique.dto.RegisterRequestDTO
import com.example.boutique.security.JwtTokenProvider
import com.example.boutique.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val customerService: CustomerService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<LoginResponseDTO> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtTokenProvider.generateToken(authentication)
        val roles = authentication.authorities.map { it.authority }
        return ResponseEntity.ok(LoginResponseDTO(token = token, roles = roles))
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequestDTO): ResponseEntity<*> {
        return try {
            customerService.registerCustomer(registerRequest)
            ResponseEntity.status(HttpStatus.CREATED).body(mapOf("message" to "User registered successfully"))
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    // Login endpoint will be added here next.
}
