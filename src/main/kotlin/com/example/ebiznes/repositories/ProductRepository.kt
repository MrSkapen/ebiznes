package com.example.ebiznes.repositories

import com.example.ebiznes.models.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Int> {
}