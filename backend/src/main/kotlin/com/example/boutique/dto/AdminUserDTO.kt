package com.example.boutique.dto

data class AdminUserDTO(
    val id: Long,
    val email: String,
    val isAdmin: Boolean
)