package com.example.boutique.web

import com.example.boutique.dto.AdminUserDTO
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
    fun getAllUsers(): ResponseEntity<List<AdminUserDTO>> {
        val users = adminService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @PostMapping("/users/{userId}/toggle-admin")
    fun toggleAdminStatus(@PathVariable userId: Long): ResponseEntity<AdminUserDTO> {
        return try {
            val updatedUser = adminService.toggleAdminStatus(userId)
            ResponseEntity.ok(updatedUser)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}