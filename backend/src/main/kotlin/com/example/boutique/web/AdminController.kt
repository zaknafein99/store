package com.example.boutique.web

import com.example.boutique.service.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController(private val adminService: AdminService) {

    @GetMapping("/summary")
    fun getDashboardSummary(): ResponseEntity<com.example.boutique.service.DashboardSummary> {
        val summary = adminService.getDashboardSummary()
        return ResponseEntity.ok(summary)
    }

    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<com.example.boutique.domain.Customer>> {
        val users = adminService.getAllUsers().map { 
            // TODO: Map to a DTO to avoid exposing password hash
            it
        }
        return ResponseEntity.ok(users)
    }

    @PostMapping("/users/{userId}/toggle-admin")
    fun toggleAdminStatus(@PathVariable userId: Long): ResponseEntity<com.example.boutique.domain.Customer> {
        return try {
            val updatedUser = adminService.toggleAdminStatus(userId)
            // TODO: Map to a DTO to avoid exposing password hash
            ResponseEntity.ok(updatedUser)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}