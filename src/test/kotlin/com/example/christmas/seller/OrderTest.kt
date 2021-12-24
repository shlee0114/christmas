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
class OrderTest : DefaultTestSetting() {
    override val rootPath = "/seller"

    @Test
    fun myOrderList_returnOrderList_success() {

    }

    @Test
    fun myOrderList_throwException_unauthorized() {

    }

    @Test
    fun order_returnOrder_success() {

    }

    @Test
    fun order_throwException_notFound() {

    }

    @Test
    fun updateOrder_returnStep_success() {

    }

    @Test
    fun updateOrder_throwException_notFound() {

    }
}