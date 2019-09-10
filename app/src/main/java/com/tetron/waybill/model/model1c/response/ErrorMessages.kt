package com.tetron.waybill.model.model1c.response

import com.google.gson.annotations.SerializedName

class ErrorMessages {

    @SerializedName("ErrorCode")
    var errorCode: Int? = null

    @SerializedName("ErrorMessage")
    var errorMessage: String? = null

}