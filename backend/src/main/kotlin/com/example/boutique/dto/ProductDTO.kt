package com.example.boutique.dto

import java.math.BigDecimal

data class ProductDTO(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val material: String,
    val sizes: List<String>,
    val colors: List<String>,
    val photos: List<String>
)
