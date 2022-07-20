package com.example.ebiznes.repositories

import com.example.ebiznes.models.Basket
import com.example.ebiznes.models.Users
import org.springframework.data.jpa.repository.JpaRepository


interface BasketRepository: JpaRepository<Basket, Int> {
    fun findByUsers(users: Users?): Basket?
}