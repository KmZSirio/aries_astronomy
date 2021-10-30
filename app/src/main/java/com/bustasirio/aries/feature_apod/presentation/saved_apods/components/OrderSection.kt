package com.bustasirio.aries.feature_apod.presentation.saved_apods.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bustasirio.aries.feature_apod.domain.util.ApodOrder
import com.bustasirio.aries.feature_apod.domain.util.OrderType
import com.bustasirio.aries.R

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    apodOrder: ApodOrder = ApodOrder.SaveDate(OrderType.Descending),
    onOrderChange: (ApodOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.title),
                selected = apodOrder is ApodOrder.Title,
                onSelect = { onOrderChange(ApodOrder.Title(apodOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.save_date),
                selected = apodOrder is ApodOrder.SaveDate,
                onSelect = { onOrderChange(ApodOrder.SaveDate(apodOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.publish_date),
                selected = apodOrder is ApodOrder.PublishDate,
                onSelect = { onOrderChange(ApodOrder.PublishDate(apodOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = apodOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(apodOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = apodOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(apodOrder.copy(OrderType.Descending)) }
            )
        }
    }
}