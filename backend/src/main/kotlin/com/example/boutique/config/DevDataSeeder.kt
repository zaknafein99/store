package com.example.boutique.config

import com.example.boutique.domain.Customer
import com.example.boutique.domain.Order
import com.example.boutique.domain.OrderItem
import com.example.boutique.domain.OrderStatus
import com.example.boutique.repository.CustomerRepository
import com.example.boutique.repository.OrderRepository
import com.example.boutique.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@Profile("dev")
class DevDataSeeder {

    private val log = LoggerFactory.getLogger(DevDataSeeder::class.java)

    @Bean
    @org.springframework.core.annotation.Order(3) // Use fully qualified name to avoid import conflict
    fun seedDevelopmentData(
        customerRepository: CustomerRepository,
        productRepository: ProductRepository,
        orderRepository: OrderRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner = CommandLineRunner {
        // 1. Create a regular user
        val userEmail = "user@example.com"
        if (customerRepository.findByEmail(userEmail).isEmpty) {
            val user = Customer(
                email = userEmail,
                passwordHash = passwordEncoder.encode("password"),
                isAdmin = false
            )
            customerRepository.save(user)
            log.info("Seeded regular user with email: {}", userEmail)

            // 2. Create a shopping cart for the user
            val products = productRepository.findAll()
            if (products.isNotEmpty()) {
                val cart = Order(customer = user, status = OrderStatus.CART)

                // 3. Add some items to the cart
                val item1 = OrderItem(
                    order = cart, // Corrected from parentOrder
                    product = products[0],
                    quantity = 2,
                    priceAtPurchase = products[0].price
                )
                if (products.size > 1) {
                    val item2 = OrderItem(
                        order = cart, // Corrected from parentOrder
                        product = products[1],
                        quantity = 1,
                        priceAtPurchase = products[1].price
                    )
                    cart.items.addAll(listOf(item1, item2))
                } else {
                    cart.items.add(item1)
                }

                orderRepository.save(cart)
                log.info("Seeded shopping cart for user: {}", userEmail)
            }
        } else {
            log.info("Regular user already exists, skipping dev data seeding.")
        }
    }
}