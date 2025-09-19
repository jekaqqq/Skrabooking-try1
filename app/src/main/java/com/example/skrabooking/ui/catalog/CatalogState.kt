package com.example.skrabooking.ui.catalog

data class CatalogState(
    val books: List<CatalogBook> = listOf(),
    val catalogCategories: List<CatalogCategory> = listOf(),
    val selectedCatalogCategory: CatalogCategory? = null,
    val searchText: String = "",
    val isSearchEnabled: Boolean = false
)