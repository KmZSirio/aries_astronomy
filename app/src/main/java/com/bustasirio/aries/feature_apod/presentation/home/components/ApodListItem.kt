package com.bustasirio.aries.feature_apod.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.unit.dp
import com.bustasirio.aries.feature_apod.domain.model.Apod
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.bustasirio.aries.R
import com.bustasirio.aries.common.formatDate

@ExperimentalCoilApi
@Composable
fun ApodListItem(
    apod: Apod,
    onItemClick: (Apod) -> Unit
) {

    val isImage = apod.mediaType == "image"

    val img = rememberImagePainter(
        if (isImage) apod.url
        else apod.thumbnailUrl,
        builder = {
            crossfade(true)
        }
    )
    val painterState = img.state

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(apod) }
            .padding(vertical = 10.dp)
            .background(MaterialTheme.colors.background),
        horizontalAlignment = CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = apod.title,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .weight(7f)
                    .align(CenterVertically)
            )

            Text(
                text = formatDate(apod.date),
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.secondaryVariant,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(2f)
                    .align(CenterVertically)
            )
        }

        when (painterState) {
            is ImagePainter.State.Loading -> {
                Box(modifier = Modifier
                    .requiredHeight(300.dp)
                ) {
                    LinearProgressIndicator(
                        Modifier.align(Center)
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
                    painter = img,
                    contentDescription = "Astronomic picture of the day",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .requiredHeight(300.dp)
                )
            }
        }

        if (!apod.copyright.isNullOrEmpty()) {
            Text(
                text = "?? ${apod.copyright.trim()}",
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = 5.dp)
            )
        }
    }
}