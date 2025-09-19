package com.example.skrabooking.ui.design_system

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skrabooking.R

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    text: String,
    isFocusable: Boolean,
    onTextChanged: (String) -> Unit,
    onCrossClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var textField by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(text.length, text.length)
            )
        )
    }
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Colors.Background.medium)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Colors.Background.light)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp),
                painter = painterResource(R.drawable.search),
                contentDescription = null
            )
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp)
                    .focusRequester(focusRequester),
                value = textField,
                enabled = isFocusable,
                onValueChange = {
                    textField = it
                    onTextChanged.invoke(it.text)
                },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search),
                            color = Colors.Text.medium,
                            style = TextStyle(
                                color = Colors.Text.medium,
                                lineHeight = 24.sp,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font.RobotoRegular)
                            )
                        )
                    }
                    innerTextField()
                },
                textStyle = TextStyle(
                    color = Colors.Text.dark,
                    lineHeight = 24.sp,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font.RobotoRegular)
                )
            )
            if (text.isNotEmpty()) {
                Image(
                    modifier = Modifier.clickable {
                        onCrossClicked()
                        focusManager.clearFocus()
                    },
                    painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                    colorFilter = ColorFilter.tint(Colors.Text.medium),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchField_Preview() {
    SearchField(text = "", isFocusable = false, onTextChanged = {}, onCrossClicked = {})
}