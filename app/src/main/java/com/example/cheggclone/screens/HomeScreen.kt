package com.example.cheggclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.cheggclone.models.DECK_ADDED
import com.example.cheggclone.models.DECK_CREATED
import com.example.cheggclone.models.Deck
import com.example.cheggclone.models.SampleDataSet
import com.example.cheggclone.navigation.Screen
import com.example.cheggclone.ui.theme.DeepOrange

// 가장 처음으로 보여지는 화면(start destination)
@Composable
fun HomeScreen(navController: NavHostController) {

    var (selectedFilterIndex, setFilterIndex) = remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "CheggPrep",
                    style = MaterialTheme.typography.h5,
                    color = DeepOrange,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
                FilterSection(selectedFilterIndex, setFilterIndex)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            when (selectedFilterIndex) {
                0 -> SampleDataSet.deckSample.forEach {
                    item {
                        DeckItem(deck = it, modifier = Modifier.padding(bottom = 8.dp)) {
                            /*
                            아래 코드는 DeckItem Composable의 마지막 인자인 onclick 함수
                            DeckItem 클릭 시 인자와 함께 [Screen클래스의 Deck인스턴스의 route + 전달할 인자 route]로 이동
                            전달할 인자는 deckTitle과 cardList의 size
                             */
                            navController.navigate(Screen.Deck.route + "/${it.deckTitle}/${it.cardList.size}")
                        }
                    }
                }
                // bookmarked된 아이템들에 대해서만 적용
                1 -> SampleDataSet.deckSample.filter { it.bookmarked }.forEach {
                    item {
                        DeckItem(deck = it, modifier = Modifier.padding(bottom = 8.dp)) {
                            navController.navigate(Screen.Deck.route + "/${it.deckTitle}/${it.cardList.size}")
                        }
                    }
                }
                2 -> SampleDataSet.deckSample.filter { it.deckType == DECK_CREATED }.forEach {
                    item {
                        DeckItem(deck = it, modifier = Modifier.padding(bottom = 8.dp)) {
                            navController.navigate(Screen.Deck.route + "/${it.deckTitle}/${it.cardList.size}")
                        }
                    }
                }
            }
            item { MakeMyDeck(onClick = { navController.navigate(Screen.Create.route) }) }
        }
    }
}

@Composable
fun FilterSection(selectedFilterIndex: Int, setIndex: (Int) -> Unit) {
    Row() {
        // filter index가 자신의 index일 경우 true를 넘겨줘서 filter의 색을 바꿈
        FilterText("All", selectedFilterIndex == 0) { setIndex(0) }
        Spacer(modifier = Modifier.width(8.dp))
        FilterText("Bookmarks", selectedFilterIndex == 1) { setIndex(1) }
        Spacer(modifier = Modifier.width(8.dp))
        FilterText("Created", selectedFilterIndex == 2) { setIndex(2) }
    }
}

// 세 개의 필터 전부가 아닌 하나의 필터에 대한 composable
@Composable
fun FilterText(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(enabled = !selected, onClick = onClick)
            .background(color = if (selected) Color.LightGray else Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

// 하나의 Deck에 대한 layout
@Composable
fun DeckItem(deck: Deck, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.LightGray
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = deck.deckTitle,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = deck.cardList.size.toString() +
                        if (deck.cardList.size > 1) " Cards" else " Card",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            when (deck.deckType) {
                // deckType이 0일 때
                DECK_CREATED -> {
                    if (deck.shared) {
                        Icon(
                            imageVector = Icons.Default.Visibility, // 공유 deck일 경우 아이콘
                            contentDescription = "shared",
                            tint = Color.Gray
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff, // 공유 deck이 아닐 경우 아이콘
                            contentDescription = "not shared",
                            tint = Color.Gray
                        )
                    }
                }
                // deckType이 1일 때
                DECK_ADDED -> {
                    if (deck.bookmarked) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "bookmark",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

// 모든 Deck 밑에 들어가는 요소
@Composable
fun MakeMyDeck(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.LightGray
            )
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Text(
            text = "Make your own cards",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "It's easy to create your own flashcard deck -for free.",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Default.NoteAdd,
                contentDescription = "bookmark",
                tint = Color.Blue
            )
            Text(
                text = "Get started",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}


