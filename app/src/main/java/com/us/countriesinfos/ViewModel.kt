package com.us.countriesinfos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class CountryViewModel : ViewModel() {
    private val _countryListResponse = mutableStateOf<List<CountryInfo>>(emptyList())
    val countryListResponse: List<CountryInfo> by _countryListResponse

    private val _errorResponse = mutableStateOf("")
    val errorResponse: String by _errorResponse




    fun getCountryList(search: String) {
        viewModelScope.launch {
            try {
                val apiService = ApiService.getInstance()
                val countriesList = apiService.getCountries(search)
                _countryListResponse.value = countriesList
            } catch (e: Exception) {
                _errorResponse.value = "Error: ${e.message}"
                e.printStackTrace()
            }
        }
    }
}
