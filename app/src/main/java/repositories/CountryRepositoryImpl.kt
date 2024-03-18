package repositories

import com.kodeco.android.countryinfo.models.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CountryRepositoryImpl : CountryRepository {
    // Acts an in-memory cache of countries
    private val countries: MutableList<Country> = mutableListOf()

    // TODO: Initialize the countries by fetching from the API!

    override fun fetchCountries(): Flow<List<Country>> {
        return flowOf(countries)
    }

    override fun getCountry(countryName: String): Country? {
        return countries.find { it.name.common == countryName }
    }
}