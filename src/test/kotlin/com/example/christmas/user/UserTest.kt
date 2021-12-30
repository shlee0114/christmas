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
class UserTest : DefaultTestSetting() {
    override val rootPath = "/user"

    @Test
    fun login_returnToken_success() {
        doPost("/login", "{\"login\" : {\"id\":\"usertest\",\"password\":\"testest12\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.token").exists())
            .andExpect(jsonPath("$.data.token").isString)
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun login_returnFailMessage_fail() {
        doPost("/login", "{\"login\" : {\"id\":\"usertest\",\"password\":\"testtest22\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(false)))
            .andExpect(jsonPath("$.data.token").doesNotExist())
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun login_throwException_invalid() {
        doPost("/login", "{\"login\" : {\"password\":\"testtest22\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "id")
        doPost("/login", "{\"login\" : {\"id\":\"usertest\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "password")
    }

    @Test
    fun register_returnTrue_success() {
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test1123!\"}," +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun register_throwException_unsuitable() {
        doPost(body = "{\"login\" : {\"id\":\"test123!4\", \"password\":\"test1123!\"}," +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "id!@#숫자, 영어 소문자, 대문자")
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test1123!\"}," +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(body = "{\"login\" : {\"id\":\"test\", \"password\":\"test1123!\"}," +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "id!@#6~12")
        doPost(body = "{\"login\" : {\"id\":\"testtesttest1\", \"password\":\"test1123!\"}, " +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "id!@#6~12")
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test123!\"}," +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "password!@#8~16")
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test123!test123!1\"}, " +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "password!@#8~16")
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test1123\"}, " +
                "\"user\" : {\"name\":\"t\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test1123\"}, " +
                "\"user\" : {\"name\":\"testetstestsetest\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
    }

    @Test
    fun register_throwException_invalid() {
        doPost(body = "{\"login\" : {\"password\":\"test1123!\"}, " +
                "\"user\" : \"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "id")
        doPost(body = "{\"login\" : {\"id\":\"test1234\"}, " +
                "\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "password")
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test1123!\"}, " +
                "\"user\" : {\"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "name")
        doPost(body = "{\"login\" : {\"id\":\"test1234\", \"password\":\"test1123!\"}, " +
                "\"user\" : {\"name\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
    }

    @Test
    fun modifyUser_returnTrue_success() {
        doPut(body = "{\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun modifyUser_throwException_unauthorized() {
        doAuthenticatedPut(
            randomAlphanumeric(60),
            body = "{\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun modifyUser_throwException_unsuitable() {
        doPut(body = "{\"user\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPut(body = "{\"user\" : {\"name\":\"t\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPut(body = "{\"user\" : {\"name\":\"testetstestsetest\", \"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
    }

    @Test
    fun modifyUser_throwException_invalid() {
        doPut(body = "{\"user\" : {\"phoneNumber\":\"000-0000-0000\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "name")
        doPut(body = "{\"user\" : {\"name\":\"test\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
    }
}