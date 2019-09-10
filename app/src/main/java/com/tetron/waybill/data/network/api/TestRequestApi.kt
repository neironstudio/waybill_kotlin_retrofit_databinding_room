package com.tetron.waybill.data.network.api

import com.tetron.waybill.model.testRequest.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestRequestApi {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/register")
    fun checkPin(@Body register: Register): Call<Any>

}