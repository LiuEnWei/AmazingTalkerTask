package com.wayne.amazingtalkertask.di

import com.wayne.amazingtalkertask.Constant
import com.wayne.amazingtalkertask.data.remote.AmazingTalkerService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { createOkHttpClient() }
    single { createAmazingTalkerService(get()) }
}

fun createOkHttpClient() : OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

fun createAmazingTalkerService(okHttpClient: OkHttpClient): AmazingTalkerService {
    val retrofit =  Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constant.AMAZING_TALKER_URL)
        .build()
    return retrofit.create(AmazingTalkerService::class.java)
}
