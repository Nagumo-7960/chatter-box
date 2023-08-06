package com.example.catterbox

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.catterbox.database.model.UserEntity
import com.example.catterbox.ui.theme.CatterBoxTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(toHome: () -> Unit) {
    var userName by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                value = userName,
                onValueChange = { userName = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                modifier = Modifier
                    .height(36.dp)
                    .width(220.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(50))
                    .padding(top = 8.dp, start = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (userName.isNotBlank()) {
                    GlobalScope.launch {
                        val user = UserEntity(
                            id = 0, name = userName
                        )
                        ChatApplication.userDao.create(user)
                    }
                    toHome()
                } else {
                    Toast.makeText(context, "名前を入力してください", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("ログイン")
            }
        }
    }
}

//fun isLoggedIn(): Boolean {
//    val userDao = ChatApplication.userDao
//    val users = userDao.getAll()
//    if (users != null){
//        return true
//    }
//        return false
//}

@Preview
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    CatterBoxTheme {
        LoginScreen(
            toHome = { navController.navigate("home") },
        )
    }
}