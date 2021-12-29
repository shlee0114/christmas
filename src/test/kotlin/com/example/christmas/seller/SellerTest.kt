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
class SellerTest : DefaultTestSetting() {
    override val rootPath = "/seller"

    @Test
    fun login_returnToken_success() {
        doPost("/login", "{\"user\" : {\"id\":\"testtest\",\"password\":\"testest12\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.token").exists())
            .andExpect(jsonPath("$.data.token").isString)
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun login_returnFailMessage_fail() {
        doPost("/login", "{\"user\" : {\"id\":\"testtest\",\"password\":\"testtest22\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(false)))
            .andExpect(jsonPath("$.data.token").doesNotExist())
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun login_throwException_invalid() {
        doPost("/login", "{\"user\" : {\"password\":\"testtest22\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "id")
        doPost("/login", "{\"user\" : {\"id\":\"testtest\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "password")
    }

    @Test
    fun register_returnTrue_success() {
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data", `is`(IsNull.nullValue())))
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun register_throwException_unsuitable() {
        doPost(
            body = "{\"user\" : {\"id\":\"test123!4\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "id!@#숫자, 영어 소문자, 대문자")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(
            body = "{\"user\" : {\"id\":\"test\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "id!@#6~12")
        doPost(
            body = "{\"user\" : {\"id\":\"testtesttest1\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "id!@#6~12")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "password!@#8~16")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test123!test123!1\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "password!@#8~16")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123\", \"userName\":\"t\", \"phoneNumber\":\"000-0000-0000\"," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "userName!@#2~12")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123\", \"userName\":\"testetstestsetest\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "userName!@#2~12")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"t\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"testtesttest1\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"t\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"testtesttest1\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "crn!@#000-00-00000")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"testtest.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "email!@#xxx@xxx.xxx")
    }

    @Test
    fun register_throwException_invalid() {
        doPost(
            body = "{\"user\" : {\"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "id")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "password")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "userName")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "name")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "representativeName")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "crn")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "email")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "address")
        doPost(
            body = "{\"user\" : {\"id\":\"test1234\", \"password\":\"test1123!\", \"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "addressDetail")
    }

    @Test
    fun modifyUser_returnTrue_success() {
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
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
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun modifyUser_throwException_unsuitable() {
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(
            body = "{\"user\" : {\"userName\":\"t\", \"phoneNumber\":\"000-0000-0000\"," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "userName!@#2~12")
        doPost(
            body = "{\"user\" : {\"userName\":\"testetstestsetest\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "userName!@#2~12")
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"t\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"testtesttest1\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"t\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"testtesttest1\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "crn!@#000-00-00000")
        doPost(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"testtest.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "email!@#xxx@xxx.xxx")
    }

    @Test
    fun modifyUser_throwException_invalid() {
        doPut(
            body = "{\"user\" : {\"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "userName")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "name")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "representativeName")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "crn")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "email")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "address")
        doPut(
            body = "{\"user\" : {\"userName\":\"test\", \"phoneNumber\":\"000-0000-0000\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "addressDetail")
    }
}