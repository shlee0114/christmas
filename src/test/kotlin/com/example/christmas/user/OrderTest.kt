package com.example.christmas.user

import DefaultTestSetting
import com.example.christmas.utils.ErrorMessage
import org.hamcrest.Matchers
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class OrderTest : DefaultTestSetting() {
    override val rootPath = "/user/order"

    @Test
    fun myOrderList_returnOrderList_success() {
        doGet()
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(jsonPath("$.data").isArray)
            .andExpect(jsonPath("$.error", Matchers.`is`(IsNull.nullValue())))
    }

    @Test
    fun myOrderList_throwException_unauthorized() {
        doAuthenticatedGet(randomAlphanumeric(60))
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)

    }

    @Test
    fun order_returnOrder_success() {
        doGet("1")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(jsonPath("$.data").isArray)
            .andExpect(jsonPath("$.error", Matchers.`is`(IsNull.nullValue())))
    }

    @Test
    fun order_throwException_notFound() {
        doGet("99999")
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_FOUND, "99999")
    }
}