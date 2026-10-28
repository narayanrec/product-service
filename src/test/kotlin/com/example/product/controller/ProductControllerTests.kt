package com.example.product.controller

import com.example.product.domain.Product
import com.example.product.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.springframework.http.HttpStatus

class ProductControllerTests {

    private lateinit var productRepository: ProductRepository
    private lateinit var productController: ProductController

    @BeforeEach
    fun setUp() {
        productRepository = Mockito.mock(ProductRepository::class.java)
        productController = ProductController(productRepository)
    }

    @Test
    fun `getAllProducts returns all products from repository`() {
        val products = listOf(Product(id = 1, name = "Example product"))
        Mockito.`when`(productRepository.findAll()).thenReturn(products)

        assertEquals(products, productController.getAllProducts())
    }

    @Test
    fun `addProduct saves product and returns it`() {
        val savedProduct = Product(id = 1, name = "Example product")
        Mockito.`when`(productRepository.save(Mockito.any(Product::class.java))).thenReturn(savedProduct)

        val response = productController.addProduct(CreateProductRequest("Example product"))

        assertEquals(HttpStatus.OK, response.statusCode)
        assertSame(savedProduct, response.body)
        val savedProductCaptor = ArgumentCaptor.forClass(Product::class.java)
        Mockito.verify(productRepository).save(savedProductCaptor.capture())
        assertEquals("Example product", savedProductCaptor.value.name)
    }

    @Test
    fun `addProduct rejects blank name without saving`() {
        val response = productController.addProduct(CreateProductRequest(""))

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(mapOf("message" to "Name is required"), response.body)
        Mockito.verifyNoInteractions(productRepository)
    }
}
