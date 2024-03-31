package com.cs4520.assignment1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("prod/random/")
    suspend fun getAllData(@Query("page") page: String?): Response<List<ApiProduct>>
}

