package com.bustasirio.aries.feature_navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bustasirio.aries.ui.theme.DarkBlue
import com.bustasirio.aries.ui.theme.Primary
import com.bustasirio.aries.ui.theme.PrimaryDarker

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (selected) Spacer(
                            modifier = Modifier
                                .background(MaterialTheme.colors.surface)
                                .height(5.dp)
                        )

                        if (!selected) Spacer(
                            modifier = Modifier
                                .background(MaterialTheme.colors.surface)
                                .height(5.dp)
                        )

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )

                        if (!selected) Spacer(
                            modifier = Modifier
                                .background(MaterialTheme.colors.surface)
                                .height(5.dp)
                        )

                        if (selected) Text(
                            text = item.name,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )

                        if (selected) Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(MaterialTheme.colors.secondary)
                        )
                    }
                }
            )
        }
    }
}