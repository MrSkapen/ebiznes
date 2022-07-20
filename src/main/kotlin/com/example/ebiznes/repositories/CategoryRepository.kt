package com.example.ebiznes.repositories

import com.example.ebiznes.models.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Int> {
}