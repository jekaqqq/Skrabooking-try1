package com.example.skrabooking.ui.catalog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
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
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import java.util.UUID

@Composable
fun CatalogView(
    modifier: Modifier,
    viewModel: CatalogViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()
    CatalogInternalView(modifier = modifier, state = state, onEvent = { viewModel.dispatch(it) })
}

@Composable
private fun CatalogInternalView(
    modifier: Modifier,
    state: CatalogState,
    onEvent: (CatalogEvent) -> Unit
) {
    Column(
        modifier = modifier.background(Colors.Background.light),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchField(
            modifier = Modifier.padding(top = 24.dp),
            text = state.searchText,
            isFocusable = true,
            onCrossClicked = { onEvent(CatalogEvent.OnSearchTextClearClicked) },
            onTextChanged = { onEvent(CatalogEvent.OnSearchTextChanged(it)) }
        )
        CategoriesGroup(
            modifier = Modifier.padding(top = 14.dp),
            categories = state.catalogCategories,
            onCatalogCategoryClicked = { onEvent(CatalogEvent.OnCatalogCategoryClicked(it)) },
            selectedCategory = state.selectedCatalogCategory
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.books) { item ->
                CatalogItem(
                    title = item.title,
                    author = item.author,
                    price = item.price,
                    posterResId = item.posterResId
                )
            }
        }
    }
}

@Composable
private fun CategoriesGroup(
    modifier: Modifier,
    selectedCategory: CatalogCategory?,
    categories: List<CatalogCategory>,
    onCatalogCategoryClicked: (CatalogCategory) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        categories.forEach {
            CategoryChip(
                catalogCategory = it,
                onClick = { _ -> onCatalogCategoryClicked(it) },
                isSelected = it.id == selectedCategory?.id
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
private fun CategoryChip(
    modifier: Modifier = Modifier,
    catalogCategory: CatalogCategory,
    isSelected: Boolean,
    onClick: (CatalogCategory) -> Unit
) {
    val backgroundColor = if (isSelected) {
        Colors.Background.dark
    } else {
        Colors.Background.medium
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Colors.Background.dark, shape = RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable { onClick(catalogCategory) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = (8.5).dp),
            text = stringResource(catalogCategory.titleRes),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Colors.Text.light,
                lineHeight = 21.sp,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font.KantumruyRegular)
            )
        )
    }
}

@Composable
private fun CatalogItem(
    modifier: Modifier = Modifier,
    title: String,
    author: String,
    price: String,
    @DrawableRes
    posterResId: Int
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Colors.Background.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(width = 153.dp, height = 176.dp)
                .padding(top = 12.dp),
            painter = painterResource(posterResId),
            contentDescription = null
        )
        Row {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = title,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Colors.Text.light,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font.DMSerifDisplayRegular)
                ),
            )
            Spacer(Modifier.weight(1f))
        }
        Row {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = author,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Colors.Text.lightMedium,
                    lineHeight = 21.sp,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font.KantumruyRegular)
                ),
            )
            Spacer(Modifier.weight(1f))
        }
        Row(modifier = Modifier.padding(12.dp)) {
            Text(
                text = price,
                style = TextStyle(
                    color = Colors.Text.dark,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font.DMSerifDisplayRegular)
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.height(28.dp),
                colors = ButtonDefaults.buttonColors().copy(containerColor = Colors.Background.dark),
                shape = RectangleShape,
                onClick = {}
            ) {
                Text(
                    text = "Buy",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Colors.Text.light,
                        lineHeight = 20.sp,
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font.KantumruyRegular)
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun CatalogView_Preview() {
    CatalogInternalView(
        state = CatalogState(
            books = listOf(
                CatalogBook(
                    title = "The Republic",
                    author = "By Plato",
                    price = "₹285",
                    posterResId = R.drawable.republic_poster
                ),
                CatalogBook(
                    title = "Ancient World",
                    author = "By Susan Wise Bauer",
                    price = "₹2,598",
                    posterResId = R.drawable.ancient_world_poster
                ),
                CatalogBook(
                    title = "Allegory of Cave",
                    author = "By Plato",
                    price = "₹549",
                    posterResId = R.drawable.allegory_of_cave_poster
                ),
                CatalogBook(
                    title = "Homeric Hymns",
                    author = "By Michael Crudden",
                    price = "₹757",
                    posterResId = R.drawable.homeric_hymns_poster
                )
            ),
            catalogCategories = listOf(
                CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_popular),
                CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_romatic),
                CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_for_children),
                CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_horror)
            )
        ),
        onEvent = {},
        modifier = Modifier
    )
}