package com.example.boutique.dto

data class LoginResponseDTO(
    val token: String,
    val tokenType: String = "Bearer"
)
