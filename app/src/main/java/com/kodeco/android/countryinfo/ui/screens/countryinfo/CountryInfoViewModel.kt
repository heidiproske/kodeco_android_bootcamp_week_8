package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import repositories.CountryRepository

class CountryInfoViewModel(
    private val repository: CountryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()

    private val _counterFlow = MutableStateFlow<Int>(0)
    val counterFlow: StateFlow<Int> = _counterFlow

    init {
        startCounterUpdate()
        fetchCountries()
    }

    fun refresh() {
        _uiState.value = CountryInfoState.Loading
        fetchCountriesWithDelay()
    }

    // TODO: Temporary code to allow us to see "updating" state in a mocked world.
    //  Remove before rolling out to production!
    private fun fetchCountriesWithDelay() {
        viewModelScope.launch {
            delay(500)
            fetchCountries()
        }
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            repository.fetchCountries()
                .catch { e ->
                    _uiState.value = CountryInfoState.Error(e)
                }
                .collect { countries ->
                    _uiState.value = CountryInfoState.Success(countries)
                }
        }
    }
    private fun startCounterUpdate() {
        viewModelScope.launch {
            while (true) {
                delay(1_000L)
                _counterFlow.value += 1
            }
        }
    }
}