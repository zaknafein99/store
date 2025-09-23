package com.example.boutique.service

import com.example.boutique.repository.CustomerRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val customer = customerRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found with email: $username") }

        val authorities = mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_USER"))
        if (customer.isAdmin) {
            authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        }

        return User(
            customer.email,
            customer.passwordHash,
            true, true, true, true,
            authorities
        )
    }
}

