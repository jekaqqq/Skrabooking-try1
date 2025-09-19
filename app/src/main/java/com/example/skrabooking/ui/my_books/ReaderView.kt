package com.example.skrabooking.ui.my_books

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skrabooking.R
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font
import com.example.skrabooking.ui.theme.reader.ReaderViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import kotlin.math.absoluteValue

@Composable
fun ReaderView(
    modifier: Modifier,
    bookId: Int
) {
    val context = LocalContext.current
    val readerViewModel: ReaderViewModel = koinViewModel { parametersOf(bookId) }
    var swipeDebounceJob = remember<Job?> { null }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val bookContent by readerViewModel.bookContent.observeAsState()
    val currentPage by readerViewModel.currentPage.observeAsState()
    val progressPercent by readerViewModel.progressPercent.observeAsState()
    var backgroundResId by remember { mutableStateOf<Int?>(null) }
    var audioPlayerManager = koinInject<AudioPlayerManager>()

    Box(modifier = modifier.background(Colors.Background.light)) {
        backgroundResId?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(it.absoluteValue),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
        Column(modifier = Modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    when {
                        dragAmount > 0 -> {
                            scope.launch {
                                swipeDebounceJob?.cancelAndJoin()
                                swipeDebounceJob = scope.launch {
                                    delay(200)
                                    readerViewModel.previousPage()
                                    scrollState.scrollTo(0)
                                }
                            }
                        }
                        dragAmount < 0 -> {
                            scope.launch {
                                swipeDebounceJob?.cancelAndJoin()
                                swipeDebounceJob = scope.launch {
                                    delay(200)
                                    readerViewModel.nextPage()
                                    scrollState.scrollTo(0)
                                }
                            }
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val screenWidth = size.width
                    // Левая треть экрана - предыдущая страница
                    if (offset.x < screenWidth / 3) {
                        scope.launch {
                            readerViewModel.previousPage()
                            scrollState.scrollTo(0)
                        }
                    }
                    // Правая треть экрана - следующая страница
                    else if (offset.x > screenWidth * 2 / 3) {
                        scope.launch {
                            readerViewModel.nextPage()
                            scrollState.scrollTo(0)
                        }
                    }
                }
            }
            .verticalScroll(scrollState)
        ) {
            Text(
                modifier = Modifier.padding(top = 6.dp, start = 16.dp, end = 16.dp),
                text = bookContent.orEmpty(),
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Color(0xFF000000),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font.RobotoRegular)
                )
            )
            Spacer(Modifier.height(64.dp))
        }
        Image(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            painter = painterResource(R.drawable.glow),
            contentDescription = null
        )
    }
    DisposableEffect(progressPercent) {
        backgroundResId = when(currentPage) {
            0 -> R.drawable.danko_1
            1 -> R.drawable.danko_2
            2 -> R.drawable.danko_3
            3 -> R.drawable.danko_4
            4 -> R.drawable.danko_5
            5 -> R.drawable.danko_6
            6 -> R.drawable.danko_7
            7 -> R.drawable.danko_8
            else -> null
        }
        if ((progressPercent ?: 0) >= 88){
            backgroundResId = R.drawable.danko_8
        }
        var trackResId = when(currentPage) {
            0 -> R.raw.danko1
            1 -> R.raw.danko2
            2 -> R.raw.danko3
            3 -> R.raw.danko4
            4 -> R.raw.danko5
            5 -> R.raw.danko6
            6 -> R.raw.danko7
            7 -> R.raw.danko8
            else -> null
        }
        if ((progressPercent ?: 0) >= 88){
            trackResId = R.raw.danko8
        }
        if (trackResId != null) {
            audioPlayerManager.playMusic(trackResId)
        }
        onDispose {
            audioPlayerManager.resetMusic()
        }
    }
}