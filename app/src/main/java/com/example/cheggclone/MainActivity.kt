package com.example.cheggclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cheggclone.navigation.BottomNavigationBar
import com.example.cheggclone.navigation.Screen
import com.example.cheggclone.screens.*
import com.example.cheggclone.ui.theme.CheggCloneTheme
import com.example.cheggclone.viewmodel.CheggViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheggCloneTheme {
                /*
                navContoller는 가장 밖에 선언되어 있음
                => 다른 하위 함수들에 대해서 인자로 활용할 수 있음
                => state hoisting
                 */

                /*
                navController 선언 - 주된 목적은 keep track of backstate state
                onCreate() 밖에서 lateinit var로 선언할 수 있음
                */
                val navController = rememberNavController()

                val cheggViewModel : CheggViewModel = viewModel()

                /*
                Boolean타입의 bottomBarShown 변수 선언, 기본값은 true
                BottomBar가 보이지 않는 스크린이 있기 때문에 선언
                 */
                val (bottomBarShown, showBottomBar) = remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        if(bottomBarShown) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) {

                    /*
                    NavHost 선언 - 주된 목적은 define navigation graph
                    startDestination을 통해 처음 보여질 화면을 선택할 수 있음
                    Screen.Home.route - Screen 클래스의 Home 인스턴스의 route(navigation.kt에 선언)
                     */
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        /*
                        Screen 클래스의 Home 인스턴스의 route를 가진 컴포저블 또는 스크린
                        화면의 BottomBar는 보이게 설정
                         */
                        composable(Screen.Home.route) {
                            showBottomBar(true)
                            HomeScreen(navController, cheggViewModel)
                        }
                        composable(Screen.Search.route) {
                            showBottomBar(true)
                            SearchScreen(navController, cheggViewModel)
                        }

                        /*
                        Screen 클래스의 Create 인스턴스의 route를 가진 컴포저블 또는 스크린
                        화면의 BottomBar는 보이지 않도록 설정
                        showBottomBar(false)선언 시 recomposition이 되면서 Scaffold의 조건문이 다시 작동
                        => false가 되었기 때문에 BottomBar는 보이지 않음
                         */
                        composable(Screen.Create.route) {
                            showBottomBar(false)
                            CreateScreen(navController, cheggViewModel)
                        }
                        composable(Screen.More.route) {
                            showBottomBar(true)
                            MoreScreen(navController, cheggViewModel)
                        }
                        composable(Screen.Deck.route + "/{deckTitle}/{cardsNum}") { backStackEntry ->
                            val deckTitle = backStackEntry.arguments?.getString("deckTitle") ?: "invalid card"
                            val cardNum = backStackEntry.arguments?.getString("cardsNum")?.toInt() ?: 0
                            showBottomBar(false) // BottomBar 보이지 않도록 설정
                            DeckScreen(navController = navController, title = deckTitle, cardsNum = cardNum, cheggViewModel)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DeckInSubject() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.LightGray
            )
            .clickable {

            }
            .padding(16.dp)) {
        Text(
            text = "recursion",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "8 Cards",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}

@Composable
fun StudyGuide() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.LightGray
            )
            .clickable {

            }
            .padding(16.dp)) {
        Text(
            text = "c-plus-plus",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "12 Decks · 207 Cards",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}



@Composable
fun MyDeckItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.LightGray
            )
            .clickable {

            }
            .padding(16.dp)) {
        Text(
            text = "recursion",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "11 Cards",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Icon(
                imageVector = Icons.Default.VisibilityOff,
                contentDescription = "visibility_off",
                tint = Color.Gray
            )
        }

    }
}


@Composable
fun sample() {
    var sizeState by remember { mutableStateOf(200.dp) }
    val size by animateDpAsState(
        targetValue = sizeState,
        animationSpec = tween(
            durationMillis = 3000,
            delayMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
    Box(modifier = Modifier
        .size(size)
        .background(Color.Red),
        contentAlignment = Alignment.Center) {
        Button(onClick = {
            sizeState += 50.dp
        }) {
            Text("Increase Size")
        }
    }
}
