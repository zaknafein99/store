package com.example.boutique.repository

import com.example.boutique.domain.Customer
import com.example.boutique.domain.Order
import com.example.boutique.domain.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByCustomerAndStatus(customer: Customer, status: OrderStatus): Optional<Order>

    @Query("SELECT SUM(oi.priceAtPurchase * oi.quantity) FROM Order o JOIN o.items oi")
    fun calculateTotalRevenue(): Double?
}
