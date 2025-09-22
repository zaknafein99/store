package com.example.boutique.service

import com.example.boutique.domain.Customer
import com.example.boutique.dto.RegisterRequestDTO
import com.example.boutique.repository.CustomerRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerCustomer(request: RegisterRequestDTO): Customer {
        if (customerRepository.findByEmail(request.email).isPresent) {
            throw IllegalStateException("Email is already in use")
        }

        val customer = Customer(
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password)
        )

        return customerRepository.save(customer)
    }
}
