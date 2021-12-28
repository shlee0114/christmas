package com.example.christmas.user

import DefaultTestSetting
import com.example.christmas.utils.ErrorMessage
import org.apache.commons.lang3.RandomStringUtils
import org.hamcrest.Matchers
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CardTest : DefaultTestSetting() {
    override val rootPath = "/user/card"

    @Test
    fun myCardList_returnCardList_success() {
        doGet()
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.`is`(IsNull.nullValue())))
    }

    @Test
    fun myCardList_throwException_unauthorized() {
        doAuthenticatedGet(randomAlphanumeric(60))
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun addCard_returnTrue_success() {
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.`is`(IsNull.nullValue())))
    }

    @Test
    fun addCard_throwException_unauthorized() {
        doAuthenticatedPost(randomAlphanumeric(60), body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun addCard_throwException_unsuitable() {
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"00000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardNum!@#12")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardNum!@#12")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardAvailableDate!@#4")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"00000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardAvailableDate!@#4")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"000\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardPw!@#2")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"0\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardPw!@#2")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"00\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardCvc!@#3")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"0000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardCvc!@#3")
    }

    @Test
    fun addCard_throwException_invalid() {
        doPost(body = "{\"card\" : {\"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardType")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardNum")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardAvailableDate")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardPw")
        doPost(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardCvc")
    }

    @Test
    fun modifyCard_returnTrue_success() {
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.`is`(IsNull.nullValue())))
    }

    @Test
    fun modifyCard_throwException_unauthorized() {
        doAuthenticatedPut(randomAlphanumeric(60), body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.`is`(IsNull.nullValue())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.`is`(IsNull.nullValue())))
    }

    @Test
    fun modifyCard_throwException_unsuitable() {
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"00000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardNum!@#12")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardNum!@#12")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardAvailableDate!@#4")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"00000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardAvailableDate!@#4")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"000\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardPw!@#2")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"0\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardPw!@#2")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"00\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardCvc!@#3")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"0000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "cardCvc!@#3")
    }

    @Test
    fun modifyCard_throwException_invalid() {
        doPut(body = "{\"card\" : {\"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "seq")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardType")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardNum")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardPw\":\"00\",\"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardAvailableDate")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardCvc\":\"000\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardPw")
        doPut(body = "{\"card\" : {\"seq\":\"1\", \"cardType\":\"01\", \"cardNum\":\"0000000000000000\", \"cardAvailableDate\":\"0000\", \"cardPw\":\"00\"}}")
            .checkIsError(MockMvcResultMatchers.status().isBadRequest, ErrorMessage.INVALID_VALUE, "cardCvc")

    }
}