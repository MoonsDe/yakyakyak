package com.example.yakyakyak.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface DrugApi {
    @GET("getDrbEasyDrugList")
    suspend fun searchDrug(
        @Query("serviceKey") serviceKey: String,
        @Query("itemName") itemName: String,
        @Query("type") type: String = "json",
        @Query("numOfRows") numOfRows: Int = 20,
        @Query("pageNo") pageNo: Int = 1
    ): DrugResponse
}

data class DrugResponse(
    val body: DrugBody?
)

data class DrugBody(
    val items: List<DrugItem>?,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class DrugItem(
    @SerializedName("itemName") val name: String?,
    @SerializedName("entpName") val company: String?,
    @SerializedName("efcyQesitm") val efficacy: String?,
    @SerializedName("useMethodQesitm") val usage: String?,
    @SerializedName("atpnWarnQesitm") val warning: String?,
    @SerializedName("atpnQesitm") val caution: String?,
    @SerializedName("seQesitm") val sideEffect: String?,
    @SerializedName("depositMethodQesitm") val storage: String?,
    @SerializedName("itemImage") val imageUrl: String?
)
