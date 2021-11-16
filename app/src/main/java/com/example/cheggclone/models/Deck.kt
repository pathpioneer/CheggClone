package com.example.cheggclone.models

import com.example.cheggclone.models.Card

// Deck(card set)에 대한 정보를 담은 data class
data class Deck(
    val deckType: Int,
    val deckTitle: String,
    val shared: Boolean,
    val bookmarked: Boolean,
    val cardList: List<Card>
)

const val DECK_CREATED = 0
const val DECK_ADDED = 1