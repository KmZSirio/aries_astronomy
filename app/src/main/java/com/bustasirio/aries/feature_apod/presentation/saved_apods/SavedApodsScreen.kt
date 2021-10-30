package com.bustasirio.aries.feature_apod.presentation.saved_apods

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.bustasirio.aries.R
import com.bustasirio.aries.Screen
import com.bustasirio.aries.feature_apod.presentation.saved_apods.components.OrderSection
import com.bustasirio.aries.feature_apod.presentation.saved_apods.components.SavedApodListItem

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun SavedApodsScreen(
    navController: NavController,
    viewModel: SavedApodsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.saved_apods_title),
                    style = MaterialTheme.typography.h5
                )
                IconButton(
                    onClick = { viewModel.onEvent(SavedApodsEvent.ToggleOrderSection) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    apodOrder = state.apodOrder,
                    onOrderChange = {
                        viewModel.onEvent(SavedApodsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(state.apods) { i, savedApod ->
                    SavedApodListItem(
                        savedApod = savedApod,
                        onItemClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("apod", it)
                            navController.navigate(Screen.ApodDetailScreen.route +
                                "?date=${it.date}")
                        }
                    )
                    if (i == state.apods.size - 1) Spacer(modifier = Modifier.height(10.dp))
                    if (i != state.apods.size - 1) Divider(color = MaterialTheme.colors.onBackground)
                }
            }
        }
    }
}