package com.example.cheggclone.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.cheggclone.models.Deck
import com.example.cheggclone.models.SampleDataSet
import java.util.*

class CheggViewModel : ViewModel() {
    var myDeckList = mutableStateListOf<Deck>()
        private set

    var totalDeckList = mutableStateListOf<Deck>()
        private set

    var searchScreenState = mutableStateOf(SearchState.ButtonScreen)
        private set

    var queryString = mutableStateOf("")
        private set

    fun setQueryString(query: String) {
        queryString.value = query
    }

    fun toButtonScreen() {
        searchScreenState.value = SearchState.ButtonScreen
    }

    fun toQueryScreen() {
        searchScreenState.value = SearchState.QueryScreen
    }

    fun toResultScreen() {
        searchScreenState.value = SearchState.ResultScreen
    }

    fun getQueryResult() =
         totalDeckList.filter{ deck ->
            deck.deckTitle.lowercase(Locale.getDefault()).contains(queryString.value.lowercase())
        }.toMutableStateList()


    init {
        myDeckList = SampleDataSet.myDeckSample.toMutableStateList()
        totalDeckList = SampleDataSet.totalDeckSample.toMutableStateList()
    }
}