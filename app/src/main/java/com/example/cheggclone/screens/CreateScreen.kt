package com.example.cheggclone.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.cheggclone.ui.theme.DeepOrange
import com.example.cheggclone.ui.theme.LightOrange

enum class CreateState {
    TitleScreen,
    CardScreen
}

@Preview(showBackground = true)
@Composable
fun abc() {
    CreateCardScreen({ }, { })
}

// navController를 사용할 예정이니 navController를 인자로 받아야 함 (상단 왼쪽의 X버튼, 상단 오르쪽의 Next버튼)
@Composable
fun CreateScreen(navController: NavHostController) {

    val (deckTitle, setDeckTitle) = remember { mutableStateOf("") } // Deck title
    val (visibility, setVisibility) = remember { mutableStateOf(true) } // Bottom bar visibility
    val (screenState, setScreenState) = remember { mutableStateOf(CreateState.TitleScreen) }

    when (screenState) {
        CreateState.TitleScreen -> {
            CreateTitleScreen(
                deckTitle,
                setDeckTitle,
                visibility,
                setVisibility,
                navigateBack = { navController.popBackStack() },
                toCardScreen = { setScreenState(CreateState.CardScreen) }
            )
        }
        CreateState.CardScreen -> {
            CreateCardScreen(
                navigateBack = { navController.popBackStack() },
                onDone = { /*TODO*/ }
            )
        }
    }


}

@Composable
fun CreateCardScreen(
    navigateBack: () -> Unit,
    onDone: () -> Unit
) {
    Scaffold(
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
                            "1/10",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    // 작업이 취소되고 이전의 화면으로 이동해야 함(아직 미구현)
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "close CreateScreen"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onDone) {
                        Text(
                            "Done",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .border(2.dp, DeepOrange, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add new card",
                    modifier = Modifier.fillMaxSize(.8f)
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CardItemField()
        }
    }
}

@Composable
fun CreateTitleScreen(
    deckTitle: String,
    setDeckTitle: (String) -> Unit,
    visibility: Boolean,
    setVisibility: (Boolean) -> Unit,
    navigateBack: () -> Unit,
    toCardScreen: () -> Unit
) {
    Scaffold(
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

                navigationIcon = {
                    // 작업이 취소되고 이전의 화면으로 이동해야 함(아직 미구현)
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "close CreateScreen"
                        )
                    }
                },

                actions = {
                    // TextButton 함수는 원래 있는 함수
                    // 작업된 데이터를 저장하고 반영해야 함(아직 미구현)
                    // deck title이 비어있지 않으면 enabled 됨
                    TextButton(onClick = toCardScreen, enabled = deckTitle.isNotBlank()) {
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


@Composable
fun CardItemField() {

    //나중에 밖으로 뺄 것
    val (frontText, setFrontText) = remember {
        mutableStateOf("")
    }

    val (backText, setBackText) = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(.8f)
            .padding(8.dp)
            .border(2.dp, Color.LightGray),
    ) {
        ConstraintLayout {
            val (front, back, delete, divider) = createRefs()
            TextField(
                value = frontText,
                onValueChange = setFrontText,
                modifier = Modifier
                    .constrainAs(front) {
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = MaterialTheme.typography.h6,
                placeholder = {
                    Text(
                        text = "Front",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = DeepOrange,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 2
            )
            Divider(
                modifier = Modifier
                    .constrainAs(divider) {
                        top.linkTo(front.bottom)
                    }
                    .fillMaxWidth()
                    .height(2.dp),
                color = Color.LightGray
            )
            TextField(
                value = backText,
                onValueChange = setBackText,
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(divider.bottom)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                textStyle = MaterialTheme.typography.body1,
                placeholder = {
                    Text(
                        text = "Back",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = DeepOrange,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.constrainAs(delete) {
                    bottom.linkTo(parent.bottom, 10.dp)
                    start.linkTo(parent.start, 8.dp)
                }
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete")
            }
        }
    }
}