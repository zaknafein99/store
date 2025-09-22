package com.example.boutique.service

import com.example.boutique.domain.Product
import com.example.boutique.dto.ProductUpsertDTO
import com.example.boutique.repository.ProductRepository
import com.example.boutique.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class AdminService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) {

    fun createProduct(dto: ProductUpsertDTO): Product {
        val product = Product(
            name = dto.name,
            description = dto.description,
            price = dto.price,
            material = dto.material,
            sizes = dto.sizes,
            colors = dto.colors,
            photos = dto.photos
        )
        return productRepository.save(product)
    }

    fun updateProduct(id: Long, dto: ProductUpsertDTO): Optional<Product> {
        return productRepository.findById(id).map { existingProduct ->
            existingProduct.name = dto.name
            existingProduct.description = dto.description
            existingProduct.price = dto.price
            existingProduct.material = dto.material
            existingProduct.sizes = dto.sizes
            existingProduct.colors = dto.colors
            existingProduct.photos = dto.photos
            productRepository.save(existingProduct)
        }
    }

    fun deleteProduct(id: Long) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        }
    }
}
