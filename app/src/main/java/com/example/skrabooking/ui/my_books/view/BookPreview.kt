package com.example.skrabooking.ui.my_books.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skrabooking.R
import com.example.skrabooking.data.entities.Book
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font

@Composable
fun BookPreview(modifier: Modifier = Modifier, book: Book, onClick: (Book) -> Unit) {
    val width = (LocalConfiguration.current.screenWidthDp * 0.42).dp
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Colors.Background.medium)
            .width(width)
            .clickable { onClick(book) }
    ) {
        Image(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)).height(250.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.danko_1),
            contentDescription = null
        )
        Column(modifier = Modifier.padding(start = 13.dp, end = 13.dp, bottom = 5.dp, top = 10.dp)) {
            Text(
                text = book.title,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Colors.Text.light,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font.RobotoBold)
                )
            )
            Text(
                text = book.author + "\nP198.00",
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Colors.Text.lightMedium,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font.AbhayaLibreRegular)
                )
            )
        }
    }
}

@Preview
@Composable
private fun BookPreview_View() {
    BookPreview(book = Book(
        id = 0,
        title = "Данко",
        author = "Горький",
        filePath = "",
        description = "",
        coverResId = R.drawable.danko_1
    ), onClick = {})
}