package com.plcoding.roomguideandroid

sealed interface GameEvent {
    object SaveGame : GameEvent
    object Update : GameEvent
    data class SetID(val gameID: Int) : GameEvent
    data class SetGameName(val gameName: String) : GameEvent
    data class SetProducer(val producer: String) : GameEvent
    data class SetRating(val rating: String) : GameEvent
    data class SetGameGenre(val genre: String) : GameEvent

    object ShowDialog : GameEvent
    object HideDialog : GameEvent
    object EditTrue : GameEvent
    object EditFalse : GameEvent
    object isDeleted : GameEvent
    object Exists : GameEvent
    data class SortGames(val sortType: SortType) : GameEvent
    data class DeleteGame(val game: Game) : GameEvent
}