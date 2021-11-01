package com.example.cheggclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cheggclone.models.Card
import com.example.cheggclone.ui.theme.DeepOrange

/*
Bottom Bar navigation 화면이 아닌
HomeScreen에서 Deck set 하나를 클릭했을 때 나오는 화면
Bottom Bar는 보이지 않도록 해야 함
*/
@Composable
fun DeckScreen(navController: NavController, title: String, cardsNum: Int) {
    Scaffold(topBar = {
        TopAppBar(
            elevation = 0.dp, // 그림자 효과
            backgroundColor = Color.White,
            title = { Text(title) },
            // 상단 왼쪽 [<-] 버튼
            navigationIcon = {
                // [<-] 버튼 클릭 시 뒤로가기 구현
                // stack에서 가장 위의 화면을 가져와서 현재의 화면에 보여줌(pop)
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }
            },
            // 상단 오른쪽 공유버튼, 옵션버튼
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "share")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more")
                }
            }
        )

    }, bottomBar = {
        // Bottom Bar가 투명해 보이지 않도록 color를 white로 설정
        Column(modifier = Modifier.background(Color.White)) {
            Divider(modifier = Modifier.height(2.dp), color = Color.LightGray)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                        .background(DeepOrange)
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Practice all cards",
                        color = Color.White,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }) {
        // top bar, bottom bar를 제외한 내용물들에 대한 선언
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                // 카드의 개수가 1개가 넘으면 Cards로, 1개일 경우 Card로 표기
                text = cardsNum.toString() + if (cardsNum > 1) "Cards" else "Card",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            repeat(5) {
                CardItem(card = Card("aaaaaaaaaa", "bbbbbbbbbbb"))
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

// 하나의 Card Item Composable
@Composable
fun CardItem(card: Card) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Color.LightGray)
    ) {
        Text(
            text = card.front,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.ExtraBold
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp), color = Color.LightGray
        )
        Text(
            text = card.back,
            modifier = Modifier.padding(16.dp),
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
    }
}
