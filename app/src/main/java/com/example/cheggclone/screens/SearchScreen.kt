package com.example.cheggclone.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cheggclone.models.DECK_ADDED
import com.example.cheggclone.models.DECK_CREATED
import com.example.cheggclone.models.Deck
import com.example.cheggclone.navigation.Screen
import com.example.cheggclone.ui.theme.DeepOrange
import com.example.cheggclone.viewmodel.CheggViewModel
import com.example.cheggclone.viewmodel.SearchState

@Composable
fun SearchScreen(navController: NavHostController, viewModel: CheggViewModel) {
    when (viewModel.searchScreenState.value) {
        SearchState.ButtonScreen -> {
            SearchButtonScreen {
                if(viewModel.queryString.value.isNotBlank()) {
                    viewModel.toResultScreen()
                } else {
                    viewModel.toQueryScreen()
                }
            }
        }

        SearchState.QueryScreen -> {
            SearchQueryScreen(
                queryString = viewModel.queryString.value,
                setQueryString = viewModel::setQueryString,
                toButtonScreen = viewModel::toButtonScreen,
                toResultScreen = viewModel::toResultScreen
            )
        }

        SearchState.ResultScreen -> {
            SearchResultScreen(
                queryString = viewModel.queryString.value,
                setQueryString = viewModel::setQueryString,
                getQueryResult = viewModel::getQueryResult,
                toButtonScreen = viewModel::toButtonScreen,
                toDeckScreen = {
                    navController.navigate(Screen.Deck.route + "/${it.deckTitle}/${it.cardList.size}")
                }
            )
        }
    }

}


@Composable
fun SearchResultScreen(
    queryString: String,
    setQueryString: (String) -> Unit,
    getQueryResult: () -> List<Deck>,
    toButtonScreen: () -> Unit,
    toDeckScreen: (Deck) -> Unit
) {
    val (queryResult, setQueryResult) = remember { mutableStateOf(getQueryResult()) }

    Scaffold(
        topBar = {
            SearchTopBar(
                queryString = queryString,
                setQueryString = setQueryString,
                onBackButtonClick = toButtonScreen,
                onSearchKey = {
                    setQueryResult(getQueryResult())
                }
            )
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            queryResult.forEach{ deck ->
                item {
                    DeckInResult(
                        deck = deck,
                        modifier = Modifier.padding(bottom = 8.dp),
                        onClick = toDeckScreen
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun DeckInResult(
    deck: Deck,
    modifier: Modifier = Modifier,
    onClick: (Deck) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.LightGray
            )
            .clickable(onClick = { onClick(deck) })
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
        }
    }
}

@Composable
fun SearchQueryScreen(
    queryString: String,
    setQueryString: (String) -> Unit,
    toButtonScreen: () -> Unit,
    toResultScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                queryString = queryString,
                setQueryString = setQueryString,
                onBackButtonClick = toButtonScreen,
                onSearchKey = toResultScreen
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.25f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "what are you learning today?",
                style = MaterialTheme.typography.body1,
                fontSize = 20.sp,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun SearchButtonScreen(onButtonClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Find flashcards",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        ) {
            FindFlashCards(onClick = onButtonClick)
            Spacer(modifier = Modifier.height(24.dp))
            Divider(
                Modifier
                    .fillMaxWidth(.15f)
                    .height(4.dp), color = DeepOrange
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Choose your subject",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Jump into studying with free flashcards that are right for you",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))
            repeat(7) {
                SubjectItem()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Flash Card 검색창
@Composable
fun FindFlashCards(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .border(1.dp, Color.LightGray, CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "search flashcards",
            tint = Color.Gray
        )
        Text(
            text = " Find flashcards",
            color = Color.Gray,
            style = MaterialTheme.typography.body1
        )
    }
}

// Flack Card 하나의 요소 composable
@Composable
fun SubjectItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(size = 8.dp),
                width = 2.dp,
                color = Color.LightGray
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Computer,
            contentDescription = "bookmark",
            tint = DeepOrange,
            modifier = Modifier.size(36.dp)
        )
        Text(
            text = "  Computer Science",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun SearchTopBar(
    queryString: String,
    setQueryString: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    onSearchKey: () -> Unit
) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "navigate back"
                )
            }
        },
        title = {
            TextField(
                value = queryString,
                onValueChange = setQueryString,
                placeholder = {
                    Text(
                        text = "Find flashcards",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = DeepOrange,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent

                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchKey()
                    }
                ),
            )
        },
        actions = {
            if (queryString.isNotBlank()) {
                IconButton(onClick = { setQueryString("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "delete")
                }
            }
        }
    )
}