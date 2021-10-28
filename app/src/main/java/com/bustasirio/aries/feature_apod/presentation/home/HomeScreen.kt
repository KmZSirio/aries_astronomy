package com.bustasirio.aries.feature_apod.presentation.home

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.bustasirio.aries.Screen
import com.bustasirio.aries.feature_apod.presentation.home.components.ApodListItem
import com.bustasirio.aries.ui.theme.White
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val apodState = viewModel.apodState
    val snackbarErrorState = viewModel.snackbarErrorState

    val apodsState = viewModel.apodsState.value
    val errorState = viewModel.errorState.value
    val isLoading = viewModel.isLoading.value

    val context = LocalContext.current

    // ! 404 Error, no data for this day
    // ! 400 Error, limits surpassed

    val calendar = Calendar.getInstance()

    val minDateCalendar = Calendar.getInstance()
    minDateCalendar.set(1995, 5, 16)

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(context)

    datePickerDialog.datePicker.maxDate = calendar.timeInMillis
    datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .zIndex(1f),
                contentColor = White,
                onClick = {
                    datePickerDialog.show()
                    datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                        date.value = "$year-${month + 1}-$dayOfMonth"
                        viewModel.getApod(date.value)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {

            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(apodsState) { i, apod ->
                    if (i == 0) Spacer(modifier = Modifier.height(10.dp))
                    ApodListItem(
                        apod = apod,
                        onItemClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("apod", it)
                            navController.navigate(Screen.ApodDetailScreen.route)
                        }
                    )
                    if (i == apodsState.size - 1) Spacer(modifier = Modifier.height(10.dp))
                    if (i != apodsState.size - 1) Divider(color = MaterialTheme.colors.onBackground)
                    if (i == apodsState.size - 2 && !isLoading) viewModel.getApods()
                }
            }
            if (errorState.isNotBlank()) Text(
                text = errorState,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
            if (isLoading && viewModel.page == 1) CircularProgressIndicator(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.align(Alignment.Center)
            )
            if (isLoading && viewModel.page > 1) CircularProgressIndicator(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
            )
            if (apodState.value != null) {
                navController.currentBackStackEntry?.savedStateHandle?.set("apod", apodState.value)
                navController.navigate(Screen.ApodDetailScreen.route)
                apodState.value = null
            }
            if (snackbarErrorState.value.isNotBlank()) scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(snackbarErrorState.value)
                snackbarErrorState.value = ""
            }

        }
    }
}