package com.tetron.waybill.di

import android.content.Context
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.R
import com.tetron.waybill.data.network.api.WebServiceWayBillServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AppModuleWebServiceWayBillServer(context_: Context) {

    var context: Context = context_

    fun provideWayBillServer(): WebServiceWayBillServer {

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val builder = originalRequest
                    .newBuilder()
                    .header("cache-control", "no-cache")
                    .header(
                        context.getString(R.string.AUT_HEADER_NAME_WAY_BILL_SERVER),
                        context.getString(R.string.VALUE_HEADER_WAY_BILL_SERVER)
                    )
                val newRequest = builder.build()
                chain.proceed(newRequest)
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.API_BASE_URL_WAYBILL))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(WebServiceWayBillServer::class.java)
    }

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

    }
}
