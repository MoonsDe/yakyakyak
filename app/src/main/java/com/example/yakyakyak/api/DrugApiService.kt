package com.example.yakyakyak.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DrugApiService {
    // data.go.kr에서 발급받은 API 키를 여기에 입력하세요
    // https://www.data.go.kr/data/15075057/openapi.do
    const val API_KEY = "09dde6f352c1753c38e3b483a80294dcad6f951408b11c1bd1898e241789725d"

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    val api: DrugApi = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/1471000/DrbEasyDrugInfoService/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DrugApi::class.java)
}
