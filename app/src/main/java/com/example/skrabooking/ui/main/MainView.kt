package com.example.skrabooking.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skrabooking.R
import com.example.skrabooking.ui.catalog.CatalogView
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font
import com.example.skrabooking.ui.my_books.MyBooksView
import com.example.skrabooking.ui.profile.ProfileView
import com.example.skrabooking.ui.search.SearchView

@Composable
fun MainView() {
    val currentTab = remember { mutableStateOf<Tab>(Tab.Catalog) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(0.5f).fillMaxWidth().background(Colors.Background.light))
            Spacer(modifier = Modifier.weight(0.5f).fillMaxWidth().background(Colors.Background.medium))
        }
        Column(modifier = Modifier.fillMaxSize().navigationBarsPadding().statusBarsPadding()) {
            when(currentTab.value) {
                Tab.Catalog -> {
                    CatalogView(modifier = Modifier.weight(1f))
                }
                Tab.Search -> {
                    SearchView(modifier = Modifier.weight(1f))
                }
                Tab.MyBooks -> {
                    MyBooksView(modifier = Modifier.weight(1f))
                }
                Tab.Profile -> {
                    ProfileView(modifier = Modifier.weight(1f))
                }
            }
            BottomNavBar(
                currentTab = currentTab.value,
                onTabChanged = {
                    if(currentTab == it) return@BottomNavBar
                    currentTab.value = it
                }
            )
        }
    }
}

@Composable
private fun BottomNavBar(modifier: Modifier = Modifier, currentTab: Tab, onTabChanged: (Tab) -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth().background(Colors.Background.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        TabView(
            iconPainter = painterResource(R.drawable.home24),
            title = stringResource(R.string.main_catalog_tab),
            onClick = { onTabChanged(Tab.Catalog) },
            isTabSelected = currentTab == Tab.Catalog
        )
        Spacer(modifier = Modifier.weight(1f))
        TabView(
            iconPainter = painterResource(R.drawable.briefcase24),
            title = stringResource(R.string.main_search_tab),
            onClick = { onTabChanged(Tab.Search) },
            isTabSelected = currentTab == Tab.Search
        )
        Spacer(modifier = Modifier.weight(1f))
        TabView(
            iconPainter = painterResource(R.drawable.heart24),
            title = stringResource(R.string.main_my_books_tab),
            onClick = { onTabChanged(Tab.MyBooks) },
            isTabSelected = currentTab == Tab.MyBooks
        )
        Spacer(modifier = Modifier.weight(1f))
        TabView(
            iconPainter = painterResource(R.drawable.person24),
            title = stringResource(R.string.main_profile_tab),
            onClick = { onTabChanged(Tab.Profile) },
            isTabSelected = currentTab == Tab.Profile
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun TabView(
    iconPainter: Painter,
    isTabSelected: Boolean,
    title: String,
    onClick: () -> Unit
) {
    val tabColor = if(isTabSelected) {
        Colors.Text.light
    } else {
        Colors.Text.dark
    }
    Column(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = iconPainter,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = tabColor)
        )
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = title,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = tabColor,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font.RobotoRegular)
            ),
        )
    }
}

private sealed class Tab {
    data object Catalog : Tab()
    data object Search : Tab()
    data object MyBooks : Tab()
    data object Profile : Tab()
}

@Preview
@Composable
private fun MainView_Preview() {
    MainView()
}