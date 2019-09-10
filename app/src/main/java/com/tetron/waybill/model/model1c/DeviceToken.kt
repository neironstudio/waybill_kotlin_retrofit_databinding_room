package com.tetron.waybill.model.model1c

import com.google.gson.annotations.SerializedName

class DeviceToken(_userId: String, _newToken: String, _oldToken: String) {
    @SerializedName("UserId")
    var userId: String = _userId

    @SerializedName("Token")
    var newToken: String = _newToken

    @SerializedName("OldToken")
    var oldToken: String = _oldToken
}