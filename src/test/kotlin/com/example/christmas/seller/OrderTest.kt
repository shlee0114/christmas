package com.example.christmas.seller

import DefaultTestSetting
import com.example.christmas.utils.ErrorMessage
import org.hamcrest.Matchers.`is`
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
    override val rootPath = "/seller/order"

    @Test
    fun myOrderList_returnOrderList_success() {
        doGet()
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data").isArray)
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
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
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun order_throwException_notFound() {
        doGet("99999")
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_FOUND, "99999")
    }

    @Test
    fun updateOrder_returnStep_success() {
        doGet("1")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.state", `is`("0")))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))

        doPut("1")
        doGet("1")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.state", `is`("1")))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))

        doPut("1")
        doGet("1")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.state", `is`("2")))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))

        doPut("1")
        doGet("1")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.state", `is`("3")))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))

        doDelete("1")
        doGet("1")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.state", `is`("4")))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))

        doDelete("2")
        doGet("2")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.state", `is`("4")))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun updateOrder_throwException_notFound() {
        doGet("99999")
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_FOUND, "99999")
    }
}