package com.plcoding.roomguideandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModel(
    private val dao: GameDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.NAME -> dao.getContactsOrderedByFirstName()
                SortType.PRODUCER -> dao.getContactsOrderedByLastName()
                SortType.GENRE -> dao.getContactsOrderedByGenre()
                SortType.RATING -> dao.getContactsOrderedByPhoneNumber()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(GameState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            games = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GameState())

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.DeleteGame -> {
                viewModelScope.launch {
                    dao.deleteGame(event.game)
                }
            }

            GameEvent.isDeleted -> {
                _state.update {
                    it.copy(
                        isDeleted = true
                    )
                }
            }

            GameEvent.Exists -> {
                _state.update {
                    it.copy(
                        isDeleted = false
                    )
                }
            }

            GameEvent.EditTrue -> {
                _state.update {
                    it.copy(
                        isEditingContact = true
                    )
                }
            }

            GameEvent.EditFalse -> {
                _state.update {
                    it.copy(
                        isEditingContact = false
                    )
                }
            }

            GameEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }
            }

            GameEvent.SaveGame -> {
                val firstName = state.value.gameName
                val lastName = state.value.producer
                val phoneNumber = state.value.rating
                val gameGenre = state.value.gameGenre

                if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || gameGenre.isBlank()) {
                    return
                }

                val game = Game(
                    gameName = firstName,
                    producer = lastName,
                    rating = phoneNumber,
                    genre = gameGenre
                )

                viewModelScope.launch {
                    dao.upsertGame(game)
                }
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        gameName = "",
                        producer = "",
                        rating = "",
                        gameGenre = ""
                    )
                }
            }

            GameEvent.Update -> {
                val name = state.value.gameName
                val producer = state.value.producer
                val rating = state.value.rating
                val gameGenre = state.value.gameGenre
                val gameID = state.value.id

                if (name.isBlank() || producer.isBlank() || rating.isBlank() || gameGenre.isBlank()) {
                    return
                }

                val game = Game(
                    id = gameID,
                    gameName = name,
                    producer = producer,
                    rating = rating,
                    genre = gameGenre
                )

                viewModelScope.launch {
                    dao.updateGame(game)
                }
                _state.update {
                    it.copy(
                        isEditingContact = false,
                        isAddingContact = false,
                        id = 0,
                        gameName = "",
                        producer = "",
                        rating = "",
                        gameGenre = ""
                    )
                }
            }

            is GameEvent.SetID -> {
                _state.update {
                    it.copy(
                        id = event.gameID
                    )
                }
            }

            is GameEvent.SetGameName -> {
                _state.update {
                    it.copy(
                        gameName = event.gameName
                    )
                }
            }

            is GameEvent.SetProducer -> {
                _state.update {
                    it.copy(
                        producer = event.producer
                    )
                }
            }

            is GameEvent.SetRating -> {
                _state.update {
                    it.copy(
                        rating = event.rating
                    )
                }
            }

            is GameEvent.SetGameGenre -> {
                _state.update {
                    it.copy(
                        gameGenre = event.genre
                    )
                }
            }

            GameEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }

            is GameEvent.SortGames -> {
                _sortType.value = event.sortType
            }
        }
    }
}