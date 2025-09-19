package com.example.skrabooking.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skrabooking.R
import com.example.skrabooking.ui.design_system.Colors
import com.example.skrabooking.ui.design_system.Font

@Composable
fun LoginView(
    onClickLogin: () -> Unit
) {
    Column(Modifier.fillMaxSize().background(Colors.Background.light)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(
                modifier = Modifier.padding(top = 24.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.login_title),
            style = TextStyle(
                color = Colors.Text.dark,
                lineHeight = 40.sp,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font.RobotoRegular)
            ),
            textAlign = TextAlign.Center
        )
        AvatarsView(modifier = Modifier.padding(top = 56.dp, start = 32.dp, end = 32.dp))
        Spacer(modifier = Modifier.weight(1f))
        ButtonsView(onClickLogin = onClickLogin)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ButtonsView(onClickLogin: () -> Unit) {
    ButtonView(
        title = stringResource(R.string.login_sign_up),
        color = Colors.Background.dark,
        onClick = {}
    )
    ButtonView(
        modifier = Modifier.padding(top = 30.dp),
        title = stringResource(R.string.login_sign_in),
        color = Colors.Background.medium,
        onClick = onClickLogin,
    )
}

@Composable
private fun AvatarsView(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Image(painter = painterResource(R.drawable.avatar_1), contentDescription = null)
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(R.drawable.avatar_2), contentDescription = null)
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(R.drawable.avatar_3), contentDescription = null)
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(R.drawable.avatar_4), contentDescription = null)
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(R.drawable.avatar_5), contentDescription = null)
    }
}

@Composable
private fun ButtonView(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    color: Color
) {
    Button(
        modifier = modifier
            .height(52.dp)
            .padding(horizontal = 29.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = color
        ),
        shape = RectangleShape
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = TextStyle(
                color = Colors.Text.light,
                lineHeight = 32.sp,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font.RobotoRegular)
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun LoginView_Preview() {
    LoginView(onClickLogin = {})
}