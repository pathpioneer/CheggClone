package com.example.cheggclone.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cheggclone.ui.theme.DeepOrange
import com.example.cheggclone.ui.theme.LightOrange

// navController를 사용할 예정이니 navController를 인자로 받아야 함 (상단 왼쪽의 X버튼, 상단 오르쪽의 Next버튼)
@Composable
fun CreateScreen(navController: NavHostController) {

    val (deckTitle, setDeckTitle) = remember { mutableStateOf("") } // Deck title
    val (visibility, setVisibility) = remember { mutableStateOf(true) } // Bottom bar visibility

    Scaffold(
        // Top Bar에는 세 개의 아이템을 배치
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Create new deck",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                // TopAppBar에 아이콘 추가 (왼쪽)
                navigationIcon = {
                    // 작업이 취소되고 이전의 화면으로 이동해야 함(아직 미구현)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "close CreateScreen"
                        )
                    }
                },
                // TopAppBar에 아이콘 추가 (오른쪽)
                actions = {
                    // TextButton 함수는 원래 있는 함수
                    // 작업된 데이터를 저장하고 반영해야 함(아직 미구현)
                    // deck title이 비어있지 않으면 enabled 됨
                    TextButton(onClick = { /*TODO*/ }, enabled = deckTitle.isNotBlank() ) {
                        Text(
                            "Next",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )

        }
    ) {
        // 화면의 절반을 사용해서 그 밑부분에 요소들 배치
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            DeckTitleTextField(deckTitle, setDeckTitle)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Visible to everyone",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = visibility,
                    onCheckedChange = setVisibility,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DeepOrange,
                        checkedTrackColor = LightOrange
                    )
                )
            }
            Text("Other Students can find, view, and study\nthis deck")
        }
    }
}

// Text Field
@Composable
fun DeckTitleTextField(text: String, setText: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = setText,
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.h4,
        placeholder = {     // TextField hint
            Text(
                text = " Untitled deck",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold,
                color = Color.LightGray
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = DeepOrange,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray
        ),
        maxLines = 2
    )
}
