package com.example.ebiznes.services

import com.example.ebiznes.models.Category
import com.example.ebiznes.models.Product
import com.example.ebiznes.repositories.CategoryRepository
import com.example.ebiznes.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ShopService(private val categoryRepository: CategoryRepository, private val productRepository: ProductRepository){

    fun saveCategory(category: Category): Category {
        return this.categoryRepository.save(category)
    }

    fun getCategory(id: Int): Category{
        return this.categoryRepository.getReferenceById(id)
    }

    fun deleteCategory(category: Category) {
        return this.categoryRepository.delete(category)
    }

    fun saveProduct(product: Product): Product{
        return this.productRepository.save(product)
    }

    fun getProduct(id: Int): Product?{
        return this.productRepository.getReferenceById(id)
    }

    fun getAllProducts(): MutableList<Product> {
        return this.productRepository.findAll()
    }

    fun deleteProduct(product: Product) {
        return this.productRepository.delete(product)
    }
}