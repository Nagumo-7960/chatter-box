package com.example.catterbox.presentation.screen.chatroom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.catterbox.ChatApplication
import com.example.catterbox.data.database.ChatDatabase
import com.example.catterbox.data.database.model.MessageEntity
import com.example.catterbox.presentation.screen.ui.theme.CatterBoxTheme

@Composable
fun ChatRoomScreen(
    toHome: () -> Unit,
    viewModel: ChatRoomViewModel = hiltViewModel()
) {
    val allMessages by viewModel.messageList.collectAsStateWithLifecycle()
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    ChatRoomContent(
        toHome = toHome,
        allMessages = allMessages,
        text = text,
        onValueChange = { newText ->
            text = newText
        },
        onImeAction = {
            viewModel.sendMessage(text, context)
            text = ""
        }
    )
}

@Composable
fun ChatRoomContent(
    toHome: () -> Unit,
    allMessages: List<MessageEntity>,
    text: String = "",
    onValueChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Row {
                    Button(onClick = { toHome() }) {
                        Text(text = "退室")
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 56.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                state = scrollState
            ) {
                items(allMessages) { message ->
                    ChatMessageSection(userName = message.post_user_name, message = message.message_content)
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomStart),
        ) {
            inputTextField(
                text = text,
                    onValueChange = { newText ->
                        onValueChange(newText)
                    },
                onImeAction = {
                    onImeAction()
                }
            )
        }

        LaunchedEffect(allMessages) {
            scrollState.scrollToItem(allMessages.size)
        }

    }
}

@Composable
fun ChatMessageSection(
    userName: String,
    message: String
){
    // ChatContentで実装していた名前とメッセージの表示を切り分ける
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Cyan, RoundedCornerShape(20))
                    .size(65.dp)
            ){
                Image(
                    painter = painterResource(id = com.example.catterbox.R.drawable.ic_launcher_foreground),
                    contentDescription = "icon"
                )
            }
            Text(
                text = userName,
                modifier = Modifier
                    .width(80.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Box (modifier = Modifier.padding(end = 8.dp)){
            Box(modifier = Modifier.offset(x = -12.dp, y = 8.dp)){
                BalloonComponent()
            }
            Text(
                text = message,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(
                        color = Color(0xFF9BFF9F),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(8.dp)

            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun inputTextField(
    text: String,
    onValueChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = text,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onImeAction()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(color = LightGray, shape = RoundedCornerShape(50))
            .padding(top = 8.dp, start = 16.dp)
    )
}

@Composable
fun BalloonComponent(){
    Box (){
        Canvas (
            modifier = Modifier.size(25.dp)
        ){
            // 三角形の描画
            val path = Path()
            path.moveTo(size.width.toFloat(), 0F)
            path.lineTo(size.width.toFloat(), size.height.toFloat())
            path.lineTo(0F, size.height.toFloat())

            drawPath(
                path = path,
                brush = SolidColor(Color(0xFF9BFF9F))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatRoomScreen() {
    // MessageEntityのダミーデータのリストを作成
    val messageList = listOf(
        MessageEntity(
            id = 0,
            post_user_name = "猫",
            message_content = "みんな、最近何かおもしろいことがあった？",
            created_at = System.currentTimeMillis(),
            room_id = 0
        ),
        MessageEntity(
            id = 1,
            post_user_name = "犬",
            message_content = "実は、昨日、飼っているペットの猫がピアノを演奏しているような真似をしてたの！",
            created_at = System.currentTimeMillis(),
            room_id = 0
        ),
        MessageEntity(
            id = 2,
            post_user_name = "虎",
            message_content = "それはすごいね、花子。でも、俺の犬はたいてい自分の尾を追いかけてるだけさ。",
            created_at = System.currentTimeMillis(),
            room_id = 0
        ),
        MessageEntity(
            id = 3,
            post_user_name = "ドラゴン",
            message_content = "健太くん、それってちょっと変かも（笑）。私の鳥は毎朝アラームクロックの代わりに騒ぎたててるよ。",
            created_at = System.currentTimeMillis(),
            room_id = 0
        ),
        MessageEntity(
            id = 4,
            post_user_name = "猫",
            message_content = "おはよう",
            created_at = System.currentTimeMillis(),
            room_id = 0
        )
    )
    val navController = rememberNavController()
    ChatApplication.chatDatabase = Room.databaseBuilder(
        LocalContext.current,
        ChatDatabase::class.java,
        "chat-database"
    ).build()
    CatterBoxTheme {
        ChatRoomContent(
            toHome = { navController.navigate("home") },
            allMessages = messageList,
            text = "気づいたら猫になってたんだー",
            onValueChange = {},
            onImeAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatMessageSection() {
    CatterBoxTheme {
        Column {
            ChatMessageSection(
                userName = "猫",
                message = "おはよう"
            )
            ChatMessageSection(
                userName = "犬",
                message = "おはよう。今日あったことなんだけどさ、話していい？話すね？僕は朝起きたら犬になっていたんだ。しかも、僕の妹も犬になっていた。"
            )
            ChatMessageSection(
                userName = "虎",
                message = "おはよう"
            )
            ChatMessageSection(
                userName = "ドラゴンウーマン牛川",
                message = "おはよう"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBalloonComponent() {
    CatterBoxTheme {
        BalloonComponent()
    }
}