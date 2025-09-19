package com.example.skrabooking.ui.profile

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.skrabooking.R
import com.example.skrabooking.data.SettingsRepository
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font
import com.example.skrabooking.ui.design_system.SearchField
import org.koin.compose.koinInject

@Composable
fun ProfileView(
    modifier: Modifier,
    settingsRepository: SettingsRepository = koinInject()
) {
    val activity = LocalActivity.current as FragmentActivity
    ProfileInternalView(
        modifier = modifier,
        onClickExit = {
            settingsRepository.setIsLoggedIn(false)
            activity.finish()
        }
    )
}

@Composable
private fun ProfileInternalView(modifier: Modifier, onClickExit: () -> Unit) {
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
        Image(
            modifier = Modifier.padding(top = 11.dp),
            painter = painterResource(R.drawable.account_circle),
            contentDescription = null
        )
        EditField(
            modifier = Modifier.padding(top = 16.dp),
            title = "Name:",
            text = "John Doe"
        )
        EditField(
            modifier = Modifier.padding(top = 25.dp),
            title = "E-mail:",
            text = "johndoe123@mail.com"
        )
        EditField(
            modifier = Modifier.padding(top = 25.dp),
            title = "Password:",
            text = "*********"
        )
        EditField(
            modifier = Modifier.padding(top = 25.dp),
            title = "Address:",
            text = "No.23, James Street"
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            ButtonView(text = "Edit", color = Colors.Background.medium, onClick = {})
            Spacer(modifier = Modifier.width(12.dp))
            ButtonView(
                text = "Log out",
                color = Colors.Background.dark,
                onClick = onClickExit
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
private fun EditField(
    modifier: Modifier = Modifier,
    title: String,
    text: String
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Colors.Background.medium)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Colors.Text.light,
                    lineHeight = 32.sp,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font.OpenSansRegular)
                )
            )
            Spacer(Modifier.width(16.dp))
            Spacer(Modifier.weight(1f))
            Text(
                text = text,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Colors.Text.light,
                    lineHeight = 32.sp,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font.OpenSansSemiBold)
                )
            )
        }
    }
}

@Composable
private fun ButtonView(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(containerColor = color),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Colors.Text.light,
                lineHeight = 22.sp,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font.OpenSansSemiBold)
            )
        )
    }
}

@Composable
@Preview
private fun ProfileView_Preview() {
    ProfileInternalView(modifier = Modifier, onClickExit = {})
}