package com.example.boutique.service

import com.example.boutique.domain.Customer
import com.example.boutique.repository.CustomerRepository
import com.example.boutique.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository
) {

    fun getDashboardSummary(): DashboardSummary {
        val totalUsers = customerRepository.count()
        val totalOrders = orderRepository.count()
        val totalRevenue = orderRepository.findAll()
            .flatMap { it.items }
            .sumOf { it.priceAtPurchase.multiply(it.quantity.toBigDecimal()) }
            .toDouble()

        return DashboardSummary(
            totalUsers = totalUsers,
            totalOrders = totalOrders,
            totalRevenue = totalRevenue
        )
    }

    fun getAllUsers(): List<Customer> {
        return customerRepository.findAll()
    }

    fun toggleAdminStatus(userId: Long): Customer {
        val customer = customerRepository.findById(userId)
            .orElseThrow { NoSuchElementException("User not found with id: $userId") }
        customer.isAdmin = !customer.isAdmin
        return customerRepository.save(customer)
    }
}

data class DashboardSummary(
    val totalUsers: Long,
    val totalOrders: Long,
    val totalRevenue: Double
)