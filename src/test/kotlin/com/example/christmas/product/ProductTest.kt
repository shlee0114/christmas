package com.example.christmas.product

import DefaultTestSetting
import com.example.christmas.utils.ErrorMessage
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.hamcrest.Matchers.`is`

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductTest : DefaultTestSetting() {
    override val rootPath = "/product"

    @Test
    fun productList_returnProductList_success() {
        doGet()
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data").isArray)
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun product_returnProduct_success() {
        doGet("1")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun product_throwException_notFound() {
        doGet("99999")
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_FOUND, "99999")
    }
}