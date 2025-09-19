package com.example.skrabooking.ui.my_books

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skrabooking.R
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font
import com.example.skrabooking.ui.design_system.SearchField
import com.example.skrabooking.ui.my_books.view.BookPreview
import com.example.skrabooking.ui.my_books.view.CategoryChip
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MyBooksView(modifier: Modifier, viewModel: MyBooksViewModel = koinViewModel()) {
    val state by viewModel.collectAsState()
    when(state.screenState) {
        State.BOOKS -> {
            MyBooksViewInternal(modifier = modifier, state = state, onEvent = { viewModel.dispatch(it) })
        }
        State.READ -> {
            ReaderView(modifier = modifier, state.selectedBookId)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.dispatch(MyBooksEvent.OnFetchData)
    }
}

@Composable
private fun MyBooksViewInternal(modifier: Modifier, state: MyBooksState, onEvent: (MyBooksEvent) -> Unit) {
    val categories = remember {
        listOf("Новеллы", "Романы", "Повести", "Рассказы")
    }
    Column(
        modifier = modifier.background(Colors.Background.light).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchField(
            modifier = Modifier.padding(top = 24.dp),
            text = "",
            isFocusable = false,
            onCrossClicked = { },
            onTextChanged = { }
        )
        Row(modifier = Modifier.padding(top = 20.dp).fillMaxWidth().horizontalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.width(16.dp))
            categories.forEach {
                CategoryChip(text = it)
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
        Row(modifier = Modifier.padding(top = 18.dp).fillMaxWidth().horizontalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.width(16.dp))
            state.books.forEach {
                BookPreview(book = it, onClick = { book -> onEvent(MyBooksEvent.OnBookClicked(book)) })
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        Text(
            modifier = Modifier.padding(top = 28.dp, start = 16.dp),
            text = "New Arrivals",
            textAlign = TextAlign.Start,
            style = TextStyle(
                color = Colors.Text.dark,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font.RobotoBold)
            )
        )
        Row(modifier = Modifier.padding(top = 20.dp).fillMaxWidth().horizontalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(R.drawable.arrival_1),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(6.dp))
            Image(
                painter = painterResource(R.drawable.arrival_2),
                contentDescription = null
            )
        }
        Spacer(Modifier.height(64.dp))
    }
}

