package com.example.catterbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatRoomScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusRequester.requestFocus() },
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()) {
            BasicTextField(
                value = text,
                onValueChange = {text = it},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    text = ""

                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(color = LightGray, shape = RoundedCornerShape(50))
                    .padding(top=8.dp,start=16.dp))

        }
    }
}

@Preview
@Composable
fun PreviewChatRoomScreen() {
    ChatRoomScreen()
}