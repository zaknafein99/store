package com.example.boutique.service

import com.example.boutique.domain.Product
import com.example.boutique.dto.ProductDTO
import com.example.boutique.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Transactional(readOnly = true)
    fun findAll(): List<ProductDTO> {
        return productRepository.findAll().map { it.toDTO() }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Optional<ProductDTO> {
        return productRepository.findById(id).map { it.toDTO() }
    }

    private fun Product.toDTO(): ProductDTO {
        return ProductDTO(
            id = this.id,
            name = this.name,
            description = this.description,
            price = this.price,
            material = this.material,
            sizes = this.sizes,
            colors = this.colors,
            photos = this.photos
        )
    }
}
