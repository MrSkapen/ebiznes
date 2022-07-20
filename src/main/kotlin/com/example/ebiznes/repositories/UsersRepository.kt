package com.example.ebiznes.repositories

import com.example.ebiznes.models.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository: JpaRepository<Users,Int> {
    fun findByEmail(email: String): Users?
}