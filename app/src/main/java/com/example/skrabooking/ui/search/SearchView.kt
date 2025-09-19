package com.example.skrabooking.ui.search

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skrabooking.R
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font
import com.example.skrabooking.ui.design_system.SearchField

@Composable
fun SearchView(modifier: Modifier) {

    val categories = remember {
        listOf(
            Category(titleRes = R.string.search_non_fiction, backgroundResId = R.drawable.nonfictionbg),
            Category(titleRes = R.string.search_classics, backgroundResId = R.drawable.classicsbg),
            Category(titleRes = R.string.search_fantasy, backgroundResId = R.drawable.fantasybg),
            Category(titleRes = R.string.search_young_adult, backgroundResId = R.drawable.youngadultbg),
            Category(titleRes = R.string.search_crime, backgroundResId = R.drawable.crimebg),
            Category(titleRes = R.string.search_horror, backgroundResId = R.drawable.horrorbg),
            Category(titleRes = R.string.search_sci_fi, backgroundResId = R.drawable.scifibg),
            Category(titleRes = R.string.search_drama, backgroundResId = R.drawable.dramabg),
        )
    }

    Column(
        modifier = modifier.background(Colors.Background.light),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchField(
            modifier = Modifier.padding(top = 24.dp),
            text = "",
            isFocusable = false,
            onCrossClicked = {  },
            onTextChanged = {  }
        )
        Column(
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 56.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Colors.Background.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 42.dp),
                text = stringResource(R.string.search_title),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Colors.Text.light,
                    lineHeight = 32.sp,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font.OpenSansSemiBold)
                )
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 columns
                modifier = Modifier.padding(top = 24.dp).fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 26.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(categories) { item ->
                    CategoryItem(item)
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(category: Category) {
    Box(modifier = Modifier.size(width = 150.dp, height = 100.dp)) {
        Image(painter = painterResource(category.backgroundResId), contentDescription = null)
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(category.titleRes),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Colors.Text.light,
                lineHeight = 34.sp,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font.OpenSansSemiBold)
            ),
        )
    }
}

private data class Category(
    @StringRes
    val titleRes: Int,
    @DrawableRes
    val backgroundResId: Int
)

@Preview
@Composable
private fun SearchView_Preview() {
    SearchView(modifier = Modifier)
}