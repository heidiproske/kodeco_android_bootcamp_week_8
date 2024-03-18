package com.kodeco.android.countryinfo.sample

import com.kodeco.android.countryinfo.models.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import repositories.CountryRepository

class MockCountryRepository : CountryRepository {
    override fun fetchCountries(): Flow<List<Country>> {
        return flow {
            emit(sampleCountries)
        }
    }

    override fun getCountry(countryName: String): Country? {
        // When needed, can implement mock logic to get a country by its name
        return null
    }
}