package com.bustasirio.aries.feature_apod.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.bustasirio.aries.feature_apod.presentation.Screen
import com.bustasirio.aries.feature_apod.presentation.home.components.ApodListItem

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val apodsState = viewModel.apodsState.value
    val errorState = viewModel.errorState.value
    val isLoading = viewModel.isLoading.value

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.background)
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(apodsState) { i, apod ->
                if (i == 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                ApodListItem(
                    apod = apod,
                    onItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("apod", it)
                        navController.navigate(Screen.ApodDetailScreen.route)
                    }
                )
                if (i == apodsState.size - 1) Spacer(modifier = Modifier.height(10.dp))
                if (i != apodsState.size - 1) Divider(
                    color = MaterialTheme.colors.onBackground
                )
                if (i == apodsState.size - 2 && !isLoading) {
                    viewModel.getApods()
                }
            }
        }
        if (errorState.isNotBlank()) {
            Text(
                text = errorState,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}