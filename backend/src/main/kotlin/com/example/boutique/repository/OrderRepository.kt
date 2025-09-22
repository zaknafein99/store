package com.example.boutique.repository

import com.example.boutique.domain.Customer
import com.example.boutique.domain.Order
import com.example.boutique.domain.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByCustomerAndStatus(customer: Customer, status: OrderStatus): Optional<Order>
}
