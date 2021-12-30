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
        doPost("/login", "{\"login\" : {\"id\":\"sellertest\",\"password\":\"testest12\"}}")
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", `is`(true)))
            .andExpect(jsonPath("$.data.token").exists())
            .andExpect(jsonPath("$.data.token").isString)
            .andExpect(jsonPath("$.error", `is`(IsNull.nullValue())))
    }

    @Test
    fun login_returnFailMessage_fail() {
        doPost("/login", "{\"login\" : {\"id\":\"sellertest\",\"password\":\"testtest22\"}}")
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
        doPost("/login", "{\"login\" : {\"id\":\"sellertest\"}}")
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "password")
    }

    @Test
    fun register_returnTrue_success() {
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest1\", \"password\":\"test1123!\"}," +
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
            body = "{\"login\" : {\"id\":\"test123!4\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "id!@#숫자, 영어 소문자, 대문자")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(
            body = "{\"login\" : {\"id\":\"test\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "id!@#6~12")
        doPost(
            body = "{\"login\" : {\"id\":\"testtesttest1\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "id!@#6~12")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "password!@#8~16")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test123!test123!1\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "password!@#8~16")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"t\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"testtesttest1\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"t\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"testtesttest1\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "crn!@#000-00-00000")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"testtest.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "email!@#xxx@xxx.xxx")
    }

    @Test
    fun register_throwException_invalid() {
        doPost(
            body = "{\"login\" : {\"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "id")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "password")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "name")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "representativeName")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "crn")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "email")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "address")
        doPost(
            body = "{\"login\" : {\"id\":\"sellertest2\", \"password\":\"test1123!\"}," +
                    " \"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "addressDetail")
    }

    @Test
    fun modifyUser_returnTrue_success() {
        doPut(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
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
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.NOT_AUTHORIZED)
    }

    @Test
    fun modifyUser_throwException_unsuitable() {
        doPost(
            body = "{\"seller\" : {\"name\":\"t\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"seller\" : {\"name\":\"testtesttest1\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "name!@#2~12")
        doPost(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"t\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"testtesttest1\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.OUT_OF_RANGE, "representativeName!@#2~12")
        doPost(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "phoneNumber!@#000-0000-0000")
        doPost(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "crn!@#000-00-00000")
        doPost(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"testtest.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_FORMAT, "email!@#xxx@xxx.xxx")
    }

    @Test
    fun modifyUser_throwException_invalid() {
        doPut(
            body = "{\"seller\" : {\"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "name")
        doPut(
            body = "{\"seller\" : {\"name\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "representativeName")
        doPut(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "phoneNumber")
        doPut(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"email\":\"test@test.com\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "crn")
        doPut(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"address\":\"test\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "email")
        doPut(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"addressDetail\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "address")
        doPut(
            body = "{\"seller\" : {\"name\":\"test\", \"representativeName\":\"test\", \"phoneNumber\":\"000-0000-0000\", \"crn\":\"000-00-00000\", \"email\":\"test@test.com\", \"address\":\"test\"}}"
        )
            .checkIsError(status().isBadRequest, ErrorMessage.INVALID_VALUE, "addressDetail")
    }
}