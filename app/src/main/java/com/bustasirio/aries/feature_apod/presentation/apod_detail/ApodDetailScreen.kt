package com.bustasirio.aries.feature_apod.presentation.apod_detail

import androidx.compose.foundation.*
import com.bustasirio.aries.feature_apod.domain.model.Apod

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.bustasirio.aries.R
import com.bustasirio.aries.ui.theme.DarkInformation
import com.bustasirio.aries.ui.theme.LightInformation
import com.bustasirio.aries.ui.theme.NotSavedGrey
import com.bustasirio.aries.ui.theme.SavedRed
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@ExperimentalCoilApi
@Composable
fun ApodDetailScreen(
    apod: Apod,
    viewModel: ApodDetailViewModel = hiltViewModel()
) {

    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val isImage = apod.mediaType == "image"

    val img = rememberImagePainter(
        if (isImage) apod.url
        else apod.thumbnailUrl,
        builder = {
            crossfade(true)
        }
    )
    val painterState = img.state

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val savedState = viewModel.savedState

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .clip(RectangleShape)
                .verticalScroll(scrollState)
        ) {

            Text(
                text = apod.title,
                style = MaterialTheme.typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
            )

            Text(
                text = apod.date,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .align(Alignment.End)
            )

            when (painterState) {
                is ImagePainter.State.Loading -> {
                    Box(modifier = Modifier
                        .requiredHeight(300.dp)
                        .align(CenterHorizontally)
                    ) {
                        LinearProgressIndicator(
                            Modifier.align(Alignment.Center)
                        )
                    }
                }
                is ImagePainter.State.Error -> {
                    Icon(
                        imageVector = Icons.Default.Error,
                        tint = MaterialTheme.colors.error,
                        contentDescription = stringResource(R.string.error)
                    )
                }
                else -> {
                    Image(
                        modifier = Modifier
                            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                            .padding(vertical = 5.dp)
                            .zIndex(1f)
                            .graphicsLayer(
                                scaleX = maxOf(1f, minOf(3f, zoom)),
                                scaleY = maxOf(1f, minOf(3f, zoom))
                            )
                            .pointerInput(Unit) {
                                detectTransformGestures(
                                    onGesture = { _, pan, gestureZoom, _ ->

                                        zoom *= gestureZoom
                                        if (zoom < 1f) zoom = 1f

                                        val x = pan.x * zoom
                                        val y = pan.y * zoom

                                        if (zoom < 1.2f) {
                                            offsetX = 0f
                                            offsetY = 0f
                                        } else {
                                            offsetX += (x * cos(0f) - y * sin(0f))
                                            offsetY += (x * sin(0f) + y * cos(0f))
                                        }
                                    }
                                )
                            }
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        zoom = 1f
                                        offsetX = 0f
                                        offsetY = 0f
                                    }
                                )
                            }
                            .fillMaxWidth()
                            .requiredHeightIn(if (isImage) 150.dp else 280.dp, 700.dp),
                        contentDescription = "Astronomic picture of the day",
                        contentScale = ContentScale.Fit,
                        painter = img
                    )
                }
            }

            if (!apod.copyright.isNullOrEmpty()) {
                Text(
                    text = "© ${apod.copyright.trim()}",
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(vertical = 5.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.FileDownload,
                        tint = if (isSystemInDarkTheme()) DarkInformation else LightInformation,
                        contentDescription = stringResource(R.string.download),
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconToggleButton(
                    checked = savedState.value,
                    onCheckedChange = {
                        savedState.value = it
                        if (it) scope.launch { viewModel.insertApod(apod) }
                        else scope.launch { viewModel.deleteApod() }
                    }
                ) {
                    if (savedState.value) Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = SavedRed,
                        contentDescription = stringResource(R.string.saved),
                        modifier = Modifier.size(30.dp)
                    )
                    else Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        tint = NotSavedGrey,
                        contentDescription = stringResource(R.string.not_saved),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Text(
                text = stringResource(R.string.explanation),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(top = 10.dp, start = 15.dp, end = 15.dp)
            )

            Text(
                text = apod.explanation,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}