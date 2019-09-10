package com.tetron.waybill.data.network

import com.tetron.waybill.BuildConfig
import com.tetron.waybill.data.network.api.TestRequestApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://reqres.in"

private val testRequestClient by lazy { buildClient() }
val testRequestApi: TestRequestApi by lazy { testRequestClient.create(TestRequestApi::class.java) }

private fun buildClient(): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(buildHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun buildHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor())
    .build()

private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}