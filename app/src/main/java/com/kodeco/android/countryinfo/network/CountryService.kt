package com.kodeco.android.countryinfo.network

import com.kodeco.android.countryinfo.models.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("all")
    suspend fun getAllCountries(): Response<List<Country>>
}
