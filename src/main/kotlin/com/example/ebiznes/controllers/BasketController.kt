package com.example.ebiznes.controllers

import com.example.ebiznes.models.Basket
import com.example.ebiznes.models.Product
import com.example.ebiznes.services.BasketService
import com.example.ebiznes.services.ShopService
import com.example.ebiznes.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class BasketController(
    private val basketService: BasketService,
    private val userService: UserService,
    private val shopService: ShopService
) {
    @PostMapping("cart_add")
    fun addProduct(
        @RequestHeader("Authorization") bearer: String?,
        @RequestParam id: String,
    ): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            var basket = Basket()
            if (shopService.getProduct(id.toInt()) != null) {
                val product: Product = shopService.getProduct(id.toInt())!!
                if (basketService.getBasketByUser(user) != null) {
                    basket = basketService.getBasketByUser(user)!!
                    val products = basket.products?.plus(product)
                    basket.products = products
                } else {
                    basket.users = user
                    basket.products = listOf(product)
                }
                ResponseEntity.ok().body(this.basketService.saveBasket(basket))
            } else {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wrong product id")
            }
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }

    @DeleteMapping("delete_cart")
    fun deleteProductFromBasket(
        @RequestHeader("Authorization") bearer: String?,
        @RequestParam id: String,
    ): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            var basket = Basket()
            if (shopService.getProduct(id.toInt()) != null) {
                val product: Product = shopService.getProduct(id.toInt())!!
                if (basketService.getBasketByUser(user) != null) {
                    basket = basketService.getBasketByUser(user)!!
                    val products: MutableList<Product> = basket.products as MutableList<Product>
                    if(products.contains(product)){
                        val index = products.indexOf(product)
                        products.removeAt(index)
                    }
                    basket.products = products
                }
                ResponseEntity.ok().body(this.basketService.saveBasket(basket))
            } else {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wrong product id")
            }
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }
}