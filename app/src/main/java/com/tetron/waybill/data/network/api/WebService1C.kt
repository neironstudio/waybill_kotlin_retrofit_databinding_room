package com.tetron.waybill.data.network.api

import com.tetron.waybill.model.model1c.*
import com.tetron.waybill.model.model1c.response.Response1C
import retrofit2.Response
import retrofit2.http.*

interface WebService1C {

    @GET("/RT83_ATP_SRT_TestWS/hs/RTDriver/Waybill/{docId}")
    suspend fun getOrderItemResponse(@Path("docId") docId: String): Response<Response1C<WaybillDoc>>

    @POST("/RT83_ATP_SRT_TestWS/hs/RTDriver/AuthUser")
    suspend fun authUser(@Body user: UserCredentials): Response<Response1C<UserInfo>>

    @POST("/RT83_ATP_SRT_TestWS/hs/RTDriver/DeviceToken")
    suspend fun deviceToken(@Body deviceToken: DeviceToken): Response<Response1C<Unit>>

    @POST("/RT83_ATP_SRT_TestWS/hs/RTDriver/Waybill/Print")
    suspend fun printDoc(@Body printRequest: PrintRequest):Response<Response1C<Unit>>
}