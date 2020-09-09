package com.paystack.checkout.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun apiComponent(): ApiComponent = ApiModule

interface ApiComponent {
    val loggingInterceptor: HttpLoggingInterceptor
    val okHttpClient: OkHttpClient
    val moshi: Moshi
    val retrofit: Retrofit
    val paystackApi: PaystackApi
}

object ApiModule : ApiComponent {
    override val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    override val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    override val moshi = Moshi.Builder().build()

    override val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("http://api.paystack.co")
        .build()

    override val paystackApi = retrofit.create(PaystackApi::class.java)
}