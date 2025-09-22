package com.example.boutique.web

import com.example.boutique.dto.ProductDTO
import com.example.boutique.dto.ProductUpsertDTO
import com.example.boutique.service.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/admin")
class AdminController(private val adminService: AdminService) {

    @PostMapping("/products")
    fun createProduct(@RequestBody productDTO: ProductUpsertDTO): ResponseEntity<ProductDTO> {
        val newProduct = adminService.createProduct(productDTO)
        // This is a simplified DTO mapping. A dedicated mapper function would be better in a real app.
        val newProductDto = ProductDTO(
            id = newProduct.id,
            name = newProduct.name,
            description = newProduct.description,
            price = newProduct.price,
            material = newProduct.material,
            sizes = newProduct.sizes,
            colors = newProduct.colors,
            photos = newProduct.photos
        )
        return ResponseEntity.created(URI.create("/api/products/${newProduct.id}")).body(newProductDto)
    }

    @PutMapping("/products/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody productDTO: ProductUpsertDTO): ResponseEntity<ProductDTO> {
        return adminService.updateProduct(id, productDTO)
            .map { updatedProduct ->
                val updatedDto = ProductDTO(
                    id = updatedProduct.id,
                    name = updatedProduct.name,
                    description = updatedProduct.description,
                    price = updatedProduct.price,
                    material = updatedProduct.material,
                    sizes = updatedProduct.sizes,
                    colors = updatedProduct.colors,
                    photos = updatedProduct.photos
                )
                ResponseEntity.ok(updatedDto)
            }
            .orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/products/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        adminService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}
