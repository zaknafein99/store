package com.example.boutique.dto

import java.math.BigDecimal

data class CartViewDTO(
    val items: List<CartItemViewDTO>,
    val totalPrice: BigDecimal
)

data class CartItemViewDTO(
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal,
    val photoUrl: String?
)
