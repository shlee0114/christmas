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
class SellerTest : DefaultTestSetting() {
    override val rootPath = "/seller"

    @Test
    fun login_returnToken_success() {

    }

    @Test
    fun login_returnFailMessage_fail() {

    }

    @Test
    fun login_throwException_unsuitable() {

    }

    @Test
    fun login_throwException_invalid() {

    }

    @Test
    fun register_returnUserInfo_success() {

    }

    @Test
    fun register_returnFailMessage_fail() {

    }

    @Test
    fun register_throwException_unsuitable() {

    }

    @Test
    fun register_throwException_invalid() {

    }

    @Test
    fun modifyUser_returnTrue_success() {

    }

    @Test
    fun modifyUser_throwException_unauthorized() {

    }

    @Test
    fun modifyUser_throwException_unsuitable() {

    }

    @Test
    fun modifyUser_throwException_invalid() {

    }
}