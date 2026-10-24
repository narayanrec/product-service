package com.example.product.controller

import com.example.product.domain.Product
import com.example.product.repository.ProductRepository
import org.springframework.web.bind.annotation.*

// DTO to handle custom payload structures safely
data class CreateProductRequest(val name: String)

@RestController
@RequestMapping("/products")
class ProductController(private val productRepository: ProductRepository) {

    // GET /products - Fetches all products inside the database
    @GetMapping
    fun getAllProducts(): List<Product> = productRepository.findAll()

    // POST /products - Accepts {"name": "Your Product Name"} and inserts it
    @PostMapping
    fun addProduct(@RequestBody request: CreateProductRequest): Product {
        val newProduct = Product(name = request.name)
        return productRepository.save(newProduct)
    }
}
