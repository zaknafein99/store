package com.example.boutique.service

import com.example.boutique.domain.Order
import com.example.boutique.domain.OrderItem
import com.example.boutique.domain.OrderStatus
import com.example.boutique.dto.CartItemDTO
import com.example.boutique.dto.CartItemViewDTO
import com.example.boutique.dto.CartViewDTO
import com.example.boutique.repository.CustomerRepository
import com.example.boutique.repository.OrderRepository
import com.example.boutique.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class CartService(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) {

    fun getCart(userEmail: String): CartViewDTO {
        val cart = findOrCreateCartForUser(userEmail)
        return mapOrderToCartViewDTO(cart)
    }

    fun addItem(userEmail: String, itemDto: CartItemDTO): CartViewDTO {
        val cart = findOrCreateCartForUser(userEmail)
        val product = productRepository.findById(itemDto.productId).orElseThrow { NoSuchElementException("Product not found") }

        val existingItem = cart.items.find { it.product.id == product.id }

        if (existingItem != null) {
            existingItem.quantity += itemDto.quantity
        } else {
            val newItem = OrderItem(
                order = cart,
                product = product,
                quantity = itemDto.quantity,
                priceAtPurchase = product.price // Set price when adding to cart
            )
            cart.items.add(newItem)
        }

        orderRepository.save(cart)
        return mapOrderToCartViewDTO(cart)
    }

    fun removeItem(userEmail: String, productId: Long): CartViewDTO {
        val cart = findOrCreateCartForUser(userEmail)
        cart.items.removeIf { it.product.id == productId }
        orderRepository.save(cart)
        return mapOrderToCartViewDTO(cart)
    }

    fun getCartEntityForUser(userEmail: String): Order {
        return findOrCreateCartForUser(userEmail)
    }

    private fun findOrCreateCartForUser(userEmail: String): Order {
        val customer = customerRepository.findByEmail(userEmail).orElseThrow { NoSuchElementException("User not found") }
        return orderRepository.findByCustomerAndStatus(customer, OrderStatus.CART)
            .orElseGet {
                val newCart = Order(customer = customer, status = OrderStatus.CART)
                orderRepository.save(newCart)
            }
    }

    private fun mapOrderToCartViewDTO(order: Order): CartViewDTO {
        val items = order.items.map {
            CartItemViewDTO(
                productId = it.product.id,
                productName = it.product.name,
                quantity = it.quantity,
                unitPrice = it.priceAtPurchase,
                totalPrice = it.priceAtPurchase.multiply(BigDecimal(it.quantity)),
                photoUrl = it.product.photos.firstOrNull()
            )
        }
        val totalPrice = items.sumOf { it.totalPrice }
        return CartViewDTO(items = items, totalPrice = totalPrice)
    }
}
