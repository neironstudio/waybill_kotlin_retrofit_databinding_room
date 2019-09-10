package com.tetron.waybill.di

import android.content.Context
import com.tetron.waybill.BuildConfig
import com.tetron.waybill.R
import com.tetron.waybill.data.network.api.WebService1C
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AppModuleWebService(context_: Context) {

    var context: Context = context_

    fun provideWebService1C(): WebService1C {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val builder = originalRequest
                    .newBuilder()
                    .header(
                        "Authorization",
                        Credentials.basic(
                            context.getString(R.string.API_USER_NAME1C),
                            context.getString(R.string.API_USER_PASSWORD1C)
                        )
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
            .baseUrl(context.getString(R.string.API_BASE_URL_1C))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(WebService1C::class.java)
    }

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

}
