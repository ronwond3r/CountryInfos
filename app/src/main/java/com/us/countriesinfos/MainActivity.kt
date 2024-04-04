package com.us.countriesinfos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.us.countriesinfos.ui.theme.CountriesInfosTheme

// MainActivity
class MainActivity : ComponentActivity() {
    private val countryViewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountriesInfosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountryList(viewModel = countryViewModel)
                }
            }
        }

        // Initial data loading
        countryViewModel.getCountryList("")
    }
}


// CountryList Composable
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CountryList(viewModel: CountryViewModel) {
    var search by remember { mutableStateOf("") }
    var currentCountry by remember { mutableStateOf<CountryInfo?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            OutlinedTextField(
                value = search,
                onValueChange = { newSearch -> search = newSearch },
                label = { Text("Search") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.getCountryList(search)
                        keyboardController?.hide()
                    }
                )
            )
            Button(
                onClick = {
                    viewModel.getCountryList(search)
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .weight(0.2f)
                    .padding(start = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Search")
            }
        }

        // Observe the countryListResponse and update currentCountry
        DisposableEffect(viewModel.countryListResponse) {
            currentCountry = viewModel.countryListResponse.firstOrNull()

            onDispose {
                // Cleanup if needed
            }
        }

        // Display the current country data or placeholder
        currentCountry?.let { CountryItem(country = it) } ?: PlaceholderCountryItem()

        // Display the rest of the country list when available
        LazyColumn {
            if (viewModel.countryListResponse.isNotEmpty()) {
                items(viewModel.countryListResponse) { country ->
                    CountryItem(country = country)
                }
            } else {
                // Display an alternative view or placeholder when countryListResponse is empty
                item {
                    Text("Country list is empty.")
                    // ... (rest of the placeholder texts)
                }
            }
        }

        // Display error message if there's an error
        if (viewModel.errorResponse.isNotEmpty()) {
            Text(
                text = "Error: ${viewModel.errorResponse}",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


// PlaceholderCountryItem Composable
@Composable
fun PlaceholderCountryItem() {
    // Placeholder data for preview
    val placeholderCountry = CountryInfo(
        name = Name(
            common = "Loading...",
            official = "Loading",
            nativeName = NativeName(
                eng = LanguageNames(common = "Loading...", official = "Loading..."),
                swa = LanguageNames(common = "Loading...", official = "Loading...")
            )
        ),
        flag = "", // You can use a loading animation or placeholder image
        capital = emptyList(),
        currencies = emptyMap(),
        borders = emptyList(),
        region = "Loading...",
        population = 0,
        area = 0,
        languages = emptyMap(), // Add placeholder for languages
        translations = emptyMap() ,// Add placeholder for translations
        flags = Flags(png = "", svg = "", alt = "")
    )

    CountryItem(country = placeholderCountry)
}

// CountryItem Composable
@Composable
fun CountryItem(country: CountryInfo) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val imageUrl = country.flags.png
        Log.d("CountryItem", "Flag URL: $imageUrl") // Log the flag URL for debugging

        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    error(R.drawable.ic_launcher_foreground)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
            ),
            contentDescription = "Flag of ${country.name.common}",
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        )

        Card(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Flag of ${country.flag}")
            Text(text = "Country: ${country.name.common}")
            Text(text = "Official Name: ${country.name.official}")
            Text(text = "Capital: ${country.capital.firstOrNull()}")
            Text(text = "Currency: ${country.currencies.values.firstOrNull()?.name}")
            Text(text = "Borders: ${country.borders.joinToString()}")
            Text(text = "Region: ${country.region}")
            Text(text = "Population: ${country.population}")
            Text(text = "Area: ${country.area}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListPreview() {
    CountriesInfosTheme {
        CountryList(viewModel = CountryViewModel()) // Create an instance of CountryViewModel for preview
    }
}
