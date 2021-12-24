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
class CardTest : DefaultTestSetting() {
    override val rootPath = "/user"

    @Test
    fun myCardList_returnCardList_success() {

    }

    @Test
    fun myCardList_throwException_unauthorized() {

    }

    @Test
    fun addCard_returnCardInfo_success() {

    }

    @Test
    fun addCard_returnFailMessage_fail() {

    }

    @Test
    fun addCard_throwException_unauthorized() {

    }

    @Test
    fun addCard_throwException_unsuitable() {

    }

    @Test
    fun addCard_throwException_invalid() {

    }

    @Test
    fun modifyCard_returnTrue_success() {

    }

    @Test
    fun modifyCard_throwException_unauthorized() {

    }

    @Test
    fun modifyCard_throwException_unsuitable() {

    }

    @Test
    fun modifyCard_throwException_invalid() {

    }
}