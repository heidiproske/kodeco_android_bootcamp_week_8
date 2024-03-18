package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import repositories.CountryRepository

class CountryInfoViewModel(
    private val repository: CountryRepository
): ViewModel() {
    // Private mutable state flow for internal use
    private val _uiState = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)

    // Publicly exposed immutable state flow
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()

    init {
        fetchCountries()
    }

    fun refresh() {
        _uiState.value = CountryInfoState.Loading
        fetchCountries()
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
}