package com.example.boutique.domain

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String,
    var description: String,
    var price: BigDecimal,
    var material: String,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_sizes", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "size")
    var sizes: List<String> = mutableListOf(),

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_colors", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "color")
    var colors: List<String> = mutableListOf(),

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_photos", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "photo_url")
    var photos: List<String> = mutableListOf()
)
