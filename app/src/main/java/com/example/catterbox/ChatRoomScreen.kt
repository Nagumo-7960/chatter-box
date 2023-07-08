package com.example.catterbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.catterbox.ui.theme.CatterBoxTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatRoomScreen(toHome: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
                ){
            Button(onClick = { toHome()}) {
                Text(text = "退室")
            }
        }
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    text = ""

                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(color = LightGray, shape = RoundedCornerShape(50))
                    .padding(top = 8.dp, start = 16.dp)
            )

        }
    }
}

@Preview
@Composable
fun PreviewChatRoomScreen() {
    val navController = rememberNavController()
    CatterBoxTheme {
        ChatRoomScreen{
            navController.navigate("home")
        }
    }
}