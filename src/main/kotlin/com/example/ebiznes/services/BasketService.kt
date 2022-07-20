package com.example.ebiznes.services

import com.example.ebiznes.models.Basket
import com.example.ebiznes.models.Users
import com.example.ebiznes.repositories.BasketRepository
import org.springframework.stereotype.Service

@Service
class BasketService(private val basketRepository: BasketRepository) {
    fun saveBasket(basket: Basket): Basket {
        return this.basketRepository.save(basket)
    }

    fun getBasket(id: Int): Basket {
        return this.basketRepository.getReferenceById(id)
    }

    fun getBasketByUser(user: Users): Basket? {
        return this.basketRepository.findByUsers(user)
    }


    fun deleteBasket(basket: Basket) {
        return this.basketRepository.delete(basket)
    }
}