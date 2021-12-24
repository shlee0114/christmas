package com.example.christmas.user

import DefaultTestSetting
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AddressTest : DefaultTestSetting() {
    override val rootPath = "/user"

    @Test
    fun myAddressList_returnAddressList_success() {

    }

    @Test
    fun myAddressList_throwException_unauthorized() {

    }

    @Test
    fun addAddress_returnAddressInfo_success() {

    }

    @Test
    fun addAddress_returnFailMessage_fail() {

    }

    @Test
    fun addAddress_throwException_unauthorized() {

    }

    @Test
    fun addAddress_throwException_unsuitable() {

    }

    @Test
    fun addAddress_throwException_invalid() {

    }

    @Test
    fun modifyAddress_returnTrue_success() {

    }

    @Test
    fun modifyAddress_throwException_unauthorized() {

    }

    @Test
    fun modifyAddress_throwException_unsuitable() {

    }

    @Test
    fun modifyAddress_throwException_invalid() {

    }
}