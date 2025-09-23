package com.example.boutique.config

import com.example.boutique.domain.Product
import com.example.boutique.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
class ProductSeeder {

    private val log = LoggerFactory.getLogger(ProductSeeder::class.java)

    @Bean
    fun seedSampleProducts(productRepository: ProductRepository): CommandLineRunner = CommandLineRunner {
        if (productRepository.count() > 0) {
            log.info("Products already present, skipping seeding")
            return@CommandLineRunner
        }

        val samples = listOf(
            Product(
                name = "Classic T-Shirt",
                description = "Soft cotton tee with a classic fit.",
                price = BigDecimal("19.99"),
                material = "100% Cotton",
                sizes = listOf("S", "M", "L", "XL"),
                colors = listOf("Black", "White", "Navy"),
                photos = listOf(
                    "https://images.unsplash.com/photo-1512436991641-6745cdb1723f?w=800&q=80&auto=format&fit=crop"
                )
            ),
            Product(
                name = "Denim Jacket",
                description = "Timeless denim jacket with a modern cut.",
                price = BigDecimal("69.00"),
                material = "Denim",
                sizes = listOf("M", "L", "XL"),
                colors = listOf("Blue"),
                photos = listOf(
                    "https://images.unsplash.com/photo-1516826957135-700dedea698c?w=800&q=80&auto=format&fit=crop"
                )
            )
        )

        productRepository.saveAll(samples)
        log.info("Seeded {} sample products", samples.size)
    }
}

