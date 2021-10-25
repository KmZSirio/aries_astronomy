package com.bustasirio.aries.feature_apod.presentation.apod_detail

import android.util.Log
import com.bustasirio.aries.feature_apod.domain.model.Apod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@Composable
fun ApodDetailScreen(
    navController: NavController,
    apod: Apod
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.background)
    ) {
        Text(
            text = apod.title,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.Center)
        )
    }
}