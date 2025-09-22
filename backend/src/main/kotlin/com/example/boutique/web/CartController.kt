package com.example.boutique.web

import com.example.boutique.dto.CartItemDTO
import com.example.boutique.dto.CartViewDTO
import com.example.boutique.service.CartService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(private val cartService: CartService) {

    @GetMapping
    fun getCart(@AuthenticationPrincipal user: UserDetails): ResponseEntity<CartViewDTO> {
        val cart = cartService.getCart(user.username)
        return ResponseEntity.ok(cart)
    }

    @PostMapping("/items")
    fun addItemToCart(@AuthenticationPrincipal user: UserDetails, @RequestBody item: CartItemDTO): ResponseEntity<CartViewDTO> {
        val updatedCart = cartService.addItem(user.username, item)
        return ResponseEntity.ok(updatedCart)
    }

    @DeleteMapping("/items/{productId}")
    fun removeItemFromCart(@AuthenticationPrincipal user: UserDetails, @PathVariable productId: Long): ResponseEntity<CartViewDTO> {
        val updatedCart = cartService.removeItem(user.username, productId)
        return ResponseEntity.ok(updatedCart)
    }
}
