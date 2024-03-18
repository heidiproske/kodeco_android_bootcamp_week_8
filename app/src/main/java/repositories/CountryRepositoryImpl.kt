package repositories

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.CountryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepositoryImpl(
    private val service: CountryService
) : CountryRepository {
    // Acts an in-memory cache of countries
    private val countries: MutableList<Country> = mutableListOf()

    //  Fetch the list of Country objects from the API service.
    override fun fetchCountries(): Flow<List<Country>> {
        return flow {
            // If countries are already cached, emit them
            if (countries.isNotEmpty()) {
                emit(countries)
            }

            // Fetch countries from the network
            val countriesResponse = service.getAllCountries()
            if (countriesResponse.isSuccessful) {
                val fetchedCountries = countriesResponse.body()!!
                // Update the in-memory cache
                countries.clear()
                countries.addAll(fetchedCountries)
                emit(fetchedCountries)
            } else {
                throw Throwable("Request failed: ${countriesResponse.message()}")
            }
        }
    }

    override fun getCountry(countryName: String): Country? {
        return countries.find { it.name.common == countryName }
    }
}