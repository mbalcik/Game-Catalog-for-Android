package com.plcoding.roomguideandroid

data class GameState(
    val games: List<Game> = emptyList(),
    val gameName: String = "",
    val producer: String = "",
    val rating: String = "",
    val gameGenre: String = "",
    val isAddingContact: Boolean = false,
    val isEditingContact: Boolean = false,
    val isDeleted : Boolean = false,
    val sortType: SortType = SortType.NAME,
    val id : Int = 0
)
