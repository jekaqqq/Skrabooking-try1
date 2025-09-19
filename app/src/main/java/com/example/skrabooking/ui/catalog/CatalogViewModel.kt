package com.example.skrabooking.ui.catalog

import com.example.skrabooking.R
import com.example.skrabooking.base.BaseViewModel
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import java.util.UUID

class CatalogViewModel : BaseViewModel<CatalogState, CatalogSideEffect, CatalogEvent>(initialState = CatalogState()) {

    init {
        intent {
            reduce {
                state.copy(
                    books = getCatalogBooks(),
                    catalogCategories = listOf(
                        CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_popular),
                        CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_romatic),
                        CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_for_children),
                        CatalogCategory(id = UUID.randomUUID().toString(), titleRes = R.string.catalog_category_horror)
                    )
                )
            }
        }
    }

    override fun dispatch(event: CatalogEvent) {
        when(event) {
            is CatalogEvent.OnCatalogCategoryClicked -> onCatalogCategoryClicked(event.catalogCategory)
            is CatalogEvent.OnSearchTextChanged -> onSearchTextChanged(event.text)
            CatalogEvent.OnSearchTextClearClicked -> onSearchTextClearClicked()
        }
    }

    private fun onSearchTextClearClicked() {
        intent {
            reduce { state.copy(searchText = "", books = getCatalogBooks(), isSearchEnabled = false) }
        }
    }

    private fun onSearchTextChanged(text: String) {
        blockingIntent {
            if (text == "") {
                reduce { state.copy(searchText = "", books = getCatalogBooks()) }
                return@blockingIntent
            }
            val booksFiltered = getCatalogBooks()
                .filter { ((it.title + it.author).lowercase()).contains(text.lowercase()) }
            reduce { state.copy(searchText = text, books = booksFiltered, isSearchEnabled = true) }
        }
    }

    private fun onCatalogCategoryClicked(category: CatalogCategory) {
        intent {
            if (state.selectedCatalogCategory?.id == category.id) {
                reduce { state.copy(selectedCatalogCategory = null) }
                return@intent
            }
            reduce { state.copy(selectedCatalogCategory = category) }
        }
    }

    private fun getCatalogBooks(): List<CatalogBook> {
        return listOf(
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
        )
    }

}