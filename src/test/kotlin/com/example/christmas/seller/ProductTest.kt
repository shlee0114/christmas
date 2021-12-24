package com.example.christmas.seller

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
    override val rootPath = "/seller"
    
    @Test
    fun myProductList_returnProductList_success() {

    }

    @Test
    fun myProductList_throwException_unauthorized() {

    }

    @Test
    fun addProduct_returnProductInfo_success() {

    }

    @Test
    fun addProduct_returnFailMessage_fail() {

    }

    @Test
    fun addProduct_throwException_unauthorized() {

    }

    @Test
    fun addProduct_throwException_unsuitable() {

    }

    @Test
    fun addProduct_throwException_invalid() {

    }

    @Test
    fun modifyProduct_returnTrue_success() {

    }

    @Test
    fun modifyProduct_throwException_unauthorized() {

    }

    @Test
    fun modifyProduct_throwException_unsuitable() {

    }

    @Test
    fun modifyProduct_throwException_invalid() {

    }
}