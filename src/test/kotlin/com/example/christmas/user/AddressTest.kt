package com.example.christmas.user

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
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AddressTest : DefaultTestSetting() {
    override val rootPath = "/user/address"

    @Test
    fun myAddressList_returnAddressList_success() {
        doGet()
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data").isArray)
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun myAddressList_throwException_unauthorized() {
        doAuthenticatedGet(randomAlphanumeric(60))
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun addAddress_returnTrue_success() {
        doPost(body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-0000-0000\",\"receiverRequest\":\"test\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun addAddress_throwException_unauthorized() {
        doAuthenticatedPost(randomAlphanumeric(60), body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-0000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun addAddress_throwException_unsuitable() {
        doPost(body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "receiverPhoneNumber!@#000-0000-0000")
        doPost(body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"${randomAlphanumeric(301)}\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "receiverRequest!@#0~300")
    }

    @Test
    fun addAddress_throwException_invalid() {
        doPost(body = "{\"address\" : {\"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "address")
        doPost(body = "{\"address\" : {\"address\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "addressDetail")
        doPost(body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "receiverName")
        doPost(body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "receiverPhoneNumber")
        doPost(body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "receiverRequest")
    }

    @Test
    fun modifyAddress_returnTrue_success() {
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-0000-0000\",\"receiverRequest\":\"test\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun modifyAddress_throwException_unauthorized() {
        doAuthenticatedPut(randomAlphanumeric(60), body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-0000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun modifyAddress_throwException_unsuitable() {
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "receiverPhoneNumber!@#000-0000-0000")
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"${randomAlphanumeric(301)}\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "receiverRequest!@#0~300")
    }

    @Test
    fun modifyAddress_throwException_invalid() {
        doPut(body = "{\"address\" : {\"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-0000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "seq")
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "address")
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "addressDetail")
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverPhoneNumber\":\"000-000-0000\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "receiverName")
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\",\"receiverRequest\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "receiverPhoneNumber")
        doPut(body = "{\"address\" : {\"seq\":\"1\", \"address\":\"testtest\", \"addressDetail\":\"testtest\", \"receiverName\":\"test\", \"receiverPhoneNumber\":\"000-000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "receiverRequest")
    }
}