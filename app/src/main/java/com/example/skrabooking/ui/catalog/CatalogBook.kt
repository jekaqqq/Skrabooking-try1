package com.example.skrabooking.ui.catalog

import androidx.annotation.DrawableRes

data class CatalogBook(
    val title: String,
    val author: String,
    val price: String,
    @DrawableRes
    val posterResId: Int
)