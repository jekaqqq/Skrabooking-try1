package com.example.skrabooking.ui.catalog

sealed class CatalogEvent {
    data class OnSearchTextChanged(val text: String) : CatalogEvent()
    data object OnSearchTextClearClicked : CatalogEvent()
    data class OnCatalogCategoryClicked(val catalogCategory: CatalogCategory): CatalogEvent()
}