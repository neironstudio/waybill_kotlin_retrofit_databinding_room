package com.tetron.waybill.model.model1c.response

import com.google.gson.annotations.SerializedName

class Response1C<T> {
    @SerializedName("ResultCode")
    var resultCode: Int? = null

    @SerializedName("ResultData")
    var resultData: T? = null

    @SerializedName("ErrorMessages")
    var errorMessages: List<ErrorMessages>? = null

    fun isSuccessful(): Boolean {
        val code = resultCode ?: 0
        return code == 0
    }

    fun getErrorMessages(): String {
        var text = ""
        for (msg in errorMessages.orEmpty())
            text += "code: " + msg.errorCode + ", msg: " + msg.errorMessage + "\n"

        return text
    }
}