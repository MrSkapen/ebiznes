package com.example.ebiznes.controllers

import com.example.ebiznes.dto.CategoryDTO
import com.example.ebiznes.dto.ProductDTO
import com.example.ebiznes.models.Category
import com.example.ebiznes.models.Product
import com.example.ebiznes.services.ShopService
import com.example.ebiznes.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api")
class ShopController(private val shopService: ShopService, private val userService: UserService) {

    @GetMapping("products")
    fun getProducts(
        @RequestHeader("Authorization") bearer: String?,
    ): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            var products: ArrayList<Product> = shopService.getAllProducts() as ArrayList<Product>
            return ResponseEntity.ok(products)
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }

    @PostMapping("add_category")
    fun addCategory(
        @RequestHeader("Authorization") bearer: String?,
        @RequestBody body: CategoryDTO
    ): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            val category = Category()
            category.name = body.name
            return ResponseEntity.ok(this.shopService.saveCategory(category))
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }

    @DeleteMapping("delete_category")
    fun deleteCategory(
        @RequestHeader("Authorization") bearer: String?,
        @RequestParam id: String
    ): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            val category = shopService.getCategory(id.toInt())
            return ResponseEntity.ok(this.shopService.deleteCategory(category))
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }

    @PostMapping("add_product")
    fun addProduct(
        @RequestHeader("Authorization") bearer: String?,
        @RequestBody body: ProductDTO
    ): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            val product = addOrEditProduct(body, "")
            return ResponseEntity.ok(this.shopService.saveProduct(product))
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }

    @PutMapping("edit_product")
    fun editProduct(
        @RequestHeader("Authorization") bearer: String?,
        @RequestBody body: ProductDTO,
        @RequestParam id: String
    ): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            val product = addOrEditProduct(body, id)
            return ResponseEntity.ok(this.shopService.saveProduct(product))
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }

    @DeleteMapping("delete_product")
    fun deleteProduct(@RequestHeader("Authorization") bearer: String?, @RequestParam id: String): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if (user != null) {
            val product = shopService.getProduct(id.toInt())
            return ResponseEntity.ok(product?.let { this.shopService.deleteProduct(it) })
        } else {
            ResponseEntity.status(401).body("unauthenticated")
        }
    }

    fun addOrEditProduct(body: ProductDTO, id: String): Product {
        var product = Product()
        if (id != "") {
            product = shopService.getProduct(id.toInt())!!
        }
        product.name = body.name
        product.price = body.price
        product.urlImage = body.urlImage
        val category = shopService.getCategory(body.category)
        product.category = category
        product.amount = body.amount
        return product
    }
}