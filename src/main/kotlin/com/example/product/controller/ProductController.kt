package com.example.product.controller

import com.example.product.domain.Product
import com.example.product.repository.ProductRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// DTO to handle custom payload structures safely
data class CreateProductRequest(val name: String)

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = ["http://localhost:5173"])
class ProductController(private val productRepository: ProductRepository) {

    // GET /products - Fetches all products inside the database
    @GetMapping
    fun getAllProducts(): List<Product> = productRepository.findAll()

    // POST /products - Accepts {"name": "Your Product Name"} and inserts it
    @PostMapping
    fun addProduct(@RequestBody request: CreateProductRequest): ResponseEntity<Any> {
        if (request.name.isBlank()) {
            return ResponseEntity.badRequest().body<Any>(mapOf("message" to "Name is required"))
        }

        val newProduct = Product(name = request.name)
        return ResponseEntity.ok<Any>(productRepository.save(newProduct))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Any> {
        if(!productRepository.existsById(id)) {
            return ResponseEntity.notFound()
                .header("message", "Product not found")
                .build()
        }
        productRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
