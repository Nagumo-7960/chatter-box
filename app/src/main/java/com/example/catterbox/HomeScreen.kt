package com.example.catterbox

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.catterbox.ui.theme.CatterBoxTheme

@Composable
fun HomeScreen(toChatRoom: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "だれでもルーム")
                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Button(onClick = { toChatRoom() }) {
                        Text(text = "入室する")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    CatterBoxTheme {
        HomeScreen{
            navController.navigate("home")
        }
    }
}