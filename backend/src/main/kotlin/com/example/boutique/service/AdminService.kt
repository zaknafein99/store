package com.example.boutique.service

import com.example.boutique.domain.Customer
import com.example.boutique.dto.AdminUserDTO
import com.example.boutique.repository.CustomerRepository
import com.example.boutique.repository.OrderRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class AdminService(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository
) {

    fun getDashboardSummary(): DashboardSummary {
        val totalUsers = customerRepository.count()
        val totalOrders = orderRepository.count()
        val totalRevenue = orderRepository.calculateTotalRevenue() ?: BigDecimal.ZERO

        return DashboardSummary(
            totalUsers = totalUsers,
            totalOrders = totalOrders,
            totalRevenue = totalRevenue
        )
    }

    fun getAllUsers(): List<AdminUserDTO> {
        return customerRepository.findAll().map { customer ->
            AdminUserDTO(
                id = customer.id,
                email = customer.email,
                isAdmin = customer.isAdmin
            )
        }
    }

    @Transactional
    fun toggleAdminStatus(userId: Long): AdminUserDTO {
        val customer = customerRepository.findById(userId)
            .orElseThrow { NoSuchElementException("User not found with id: $userId") }

        // Prevent self-demotion: get current authenticated user ID
        val authentication = SecurityContextHolder.getContext().authentication
        val currentUserEmail = authentication.name
        val currentUser = customerRepository.findByEmail(currentUserEmail)
            .orElseThrow { IllegalStateException("Authenticated user not found") }

        if (currentUser.id == userId && customer.isAdmin) {
            throw IllegalArgumentException("Cannot revoke admin privileges from yourself")
        }

        customer.isAdmin = !customer.isAdmin
        val savedCustomer = customerRepository.save(customer)

        return AdminUserDTO(
            id = savedCustomer.id,
            email = savedCustomer.email,
            isAdmin = savedCustomer.isAdmin
        )
    }
}

data class DashboardSummary(
    val totalUsers: Long,
    val totalOrders: Long,
    val totalRevenue: BigDecimal
)