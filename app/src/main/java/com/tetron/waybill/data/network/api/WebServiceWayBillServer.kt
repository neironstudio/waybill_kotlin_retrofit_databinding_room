package com.tetron.waybill.data.network.api

import com.tetron.waybill.model.track.Route
import com.tetron.waybill.model.track.WayPathOrder
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WebServiceWayBillServer {
    @POST("doc_order")
    fun postOrder(@Body wayPathOrder: WayPathOrder): Call<Any>

    @POST("route")
    fun postRoute(@Body route: Route): Call<Any>
}