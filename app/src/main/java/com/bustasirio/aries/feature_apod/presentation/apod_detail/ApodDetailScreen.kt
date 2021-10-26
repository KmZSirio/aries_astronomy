package com.bustasirio.aries.feature_apod.presentation.apod_detail

import androidx.compose.foundation.Image
import com.bustasirio.aries.feature_apod.domain.model.Apod

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@ExperimentalCoilApi
@Composable
fun ApodDetailScreen(
    apod: Apod
) {

    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val isImage = apod.mediaType == "image"

    val img = rememberImagePainter(
        if (isImage) apod.url
        else apod.thumbnailUrl
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
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

        Text(
            text = "Explanation",
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
        Spacer(modifier = Modifier.height(20.dp))
    }
}