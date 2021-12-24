package com.example.christmas.product

import DefaultTestSetting
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductTest : DefaultTestSetting() {
    override val rootPath = "/product"

    @Test
    fun productList_returnProductList_success() {

    }

    @Test
    fun product_returnProduct_success() {

    }

    @Test
    fun product_throwException_notFound() {

    }
}