package com.example.skrabooking.ui.catalog

import androidx.annotation.StringRes

data class CatalogCategory(
    val id: String,
    @StringRes
    val titleRes: Int
)