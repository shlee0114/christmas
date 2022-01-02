package com.example.christmas.utils

enum class ErrorMessage {
    INVALID_VALUE {
        override fun getMessage(additionalMessage: String) =
            "$additionalMessage 값이 존재하지 않습니다."
    },
    INVALID_FORMAT {
        override fun getMessage(additionalMessage: String) =
            "[${additionalMessage.split("!@#")[0]}]은 [${additionalMessage.split("!@#")[1]}] 형식만 입력 가능합니다."
    },
    OUT_OF_RANGE {
        override fun getMessage(additionalMessage: String) =
            "[${additionalMessage.split("!@#")[0]}]의 허용 범위는 ${additionalMessage.split("!@#")[1]}입니다."
    },
    TYPE_MISMATCH {
        override fun getMessage(additionalMessage: String) =
            "${additionalMessage.split("!@#")[0]}에는 ${additionalMessage.split("!@#")[1]}형만 입력 가능합니다."
    },
    NOT_AUTHORIZED {
        override fun getMessage(additionalMessage: String) =
            "TOKEN 값이 올바르지 않습니다."
    },
    NOT_FOUND {
        override fun getMessage(additionalMessage: String) =
            "[${additionalMessage}]값을 찾을 수 없습니다."
    },
    NONE {
        override fun getMessage(additionalMessage: String) = ""
    };

    abstract fun getMessage(additionalMessage: String = ""): String

    companion object {
        fun getErrorMessage(message: String): ApiUtils {
            val messageEncoding = message.split("!@#")
            val errorCode =
                try {
                    valueOf(messageEncoding[0])
                } catch (e: Exception) {
                    NONE
                }

            return ApiUtils(
                false,
                error = ApiUtils.ErrorUtils(
                    errorCode.name,
                    if (messageEncoding.size == 1) {
                        messageEncoding[0]
                    } else {
                        errorCode.getMessage(messageEncoding[1])
                    }
                )
            )
        }
    }
}