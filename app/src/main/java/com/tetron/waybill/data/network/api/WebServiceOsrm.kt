package com.tetron.waybill.data.network.api

import com.tetron.waybill.model.track.RouteOsrm
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServiceOsrm {
    @GET("route/v1/driving/{pointString}")
    fun getLengthFromPointArray(@Path("pointString") pointString: String, @Query("overview") overview: Boolean): Call<RouteOsrm>
}