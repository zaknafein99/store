package com.example.boutique.web

import com.example.boutique.service.CartService
import com.example.boutique.service.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/checkout")
class CheckoutController(
    private val paymentService: PaymentService,
    private val cartService: CartService // Use to obtain the current cart Order entity
) {

    @PostMapping("/create-preference")
    fun createPreference(@AuthenticationPrincipal user: UserDetails): ResponseEntity<Map<String, String>> {
        val cartOrder = cartService.getCartEntityForUser(user.username)
        val redirectUrl = paymentService.createPreference(cartOrder)
        return redirectUrl?.let { ResponseEntity.ok(mapOf("redirectUrl" to it)) }
            ?: ResponseEntity.internalServerError().body(mapOf("error" to "Could not create payment preference"))
    }
}
