package com.example.skrabooking.ui.my_books.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font

@Composable
fun CategoryChip(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = TextAlign.Center,
        style = TextStyle(
            color = Colors.Text.dark,
            lineHeight = 24.sp,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font.RobotoRegular)
        )
    )
}