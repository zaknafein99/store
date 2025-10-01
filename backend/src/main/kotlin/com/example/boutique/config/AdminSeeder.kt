package com.example.boutique.config

import com.example.boutique.domain.Customer
import com.example.boutique.repository.CustomerRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AdminSeeder {

    private val log = LoggerFactory.getLogger(AdminSeeder::class.java)

    @Value("\${app.admin.email:admin@example.com}")
    private lateinit var adminEmail: String

    @Value("\${app.admin.password:admin123}")
    private lateinit var adminPassword: String

    @Bean
    @Order(1)
    fun seedAdminUser(
        customerRepository: CustomerRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner = CommandLineRunner {
        val exists = customerRepository.findByEmail(adminEmail).isPresent
        if (!exists) {
            val admin = Customer(
                email = adminEmail,
                passwordHash = passwordEncoder.encode(adminPassword),
                isAdmin = true
            )
            customerRepository.save(admin)
            log.info("Seeded admin user with email: {}", adminEmail)
        } else {
            log.info("Admin user already exists: {}", adminEmail)
        }
    }
}

