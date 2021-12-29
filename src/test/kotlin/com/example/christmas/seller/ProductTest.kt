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
class ProductTest : DefaultTestSetting() {
    override val rootPath = "/seller/product"

    @Test
    fun myProductList_returnProductList_success() {
        doGet()
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data").isArray)
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun myProductList_throwException_unauthorized() {
        doAuthenticatedGet(randomAlphanumeric(60))
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun addProduct_returnProductInfo_success() {
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun addProduct_throwException_unauthorized() {
        doAuthenticatedPost(
            randomAlphanumeric(60),
            body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun addProduct_throwException_unsuitable() {
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"${randomAlphanumeric(31)}\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "title!@#1~30")
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"${randomAlphanumeric(31)}\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "subTitle!@#1~30")
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"test\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.TYPE_MISMATCH, "price!@#정수")
    }

    @Test
    fun addProduct_throwException_invalid() {
        doPost(body = "{\"product\" : {\"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "thumbnailId")
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "title")
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "subTitle")
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "price")
        doPost(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "content")
    }

    @Test
    fun modifyProduct_returnTrue_success() {
        doPut(body = "{\"product\" : {\"seq\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun modifyProduct_throwException_unauthorized() {
        doAuthenticatedPut(
            randomAlphanumeric(60),
            body = "{\"product\" : {\"seq\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)

    }

    @Test
    fun modifyProduct_throwException_unsuitable() {
        doPut(body = "{\"product\" : {\"seq\":\"1\", \"thumbnailId\":\"1\", \"title\":\"${randomAlphanumeric(31)}\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "title!@#1~30")
        doPut(
            body = "{\"product\" : {\"seq\":\"1\", \"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"${
                randomAlphanumeric(
                    31
                )
            }\", \"price\":\"1000\", \"content\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "subTitle!@#1~30")
        doPut(body = "{\"product\" : {\"seq\":\"1\", \"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"test\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.TYPE_MISMATCH, "price!@#정수")
    }

    @Test
    fun modifyProduct_throwException_invalid() {
        doPut(body = "{\"product\" : {\"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"test\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "seq")
        doPost(body = "{\"product\" : {\"seq\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "thumbnailId")
        doPost(body = "{\"product\" : {\"seq\":\"1\", \"thumbnailId\":\"1\", \"subTitle\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "title")
        doPost(body = "{\"product\" : {\"seq\":\"1\", \"thumbnailId\":\"1\", \"title\":\"test\", \"price\":\"1000\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "subTitle")
        doPost(body = "{\"product\" : {\"seq\":\"1\", \"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"content\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "price")
        doPost(body = "{\"product\" : {\"seq\":\"1\", \"thumbnailId\":\"1\", \"title\":\"test\", \"subTitle\":\"test\", \"price\":\"1000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "content")

    }
}