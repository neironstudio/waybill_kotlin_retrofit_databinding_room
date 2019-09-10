package com.tetron.waybill.model.model1c

import com.google.gson.annotations.SerializedName

class UserCredentials(_login: String, _password: String) {
    @SerializedName("Login")
    var login: String = _login

    @SerializedName("Password")
    var password: String = _password
}