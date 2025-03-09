package com.plcoding.roomguideandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.roomguideandroid.ui.theme.BGcolor
import com.plcoding.roomguideandroid.ui.theme.Beige
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.text.TextStyle

@Composable
fun GameScreen(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    var searchedText by remember {
        mutableStateOf("")
    }

    var selectedGame by remember {
        mutableStateOf(Game("", "", "", ""))
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(GameEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
    ) { _ ->
        if (state.isAddingContact) {
            AddContactDialog(state = state, onEvent = onEvent)
        }

        if (state.isEditingContact) {
            EditGameDialog(game = selectedGame, onEvent = onEvent)
        }

        if (state.isDeleted) {
            DeletedPopup(
                onEvent = onEvent,
                game = selectedGame
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(BGcolor),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            item {
                Text(
                    text = "GamerChronicles",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Beige,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically
                ) {
                    TextField(
                        value = searchedText,
                        onValueChange = { newText ->
                            searchedText = newText
                        },
                        label = { Text("Search", color = Color.White) },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = Color.White)

                    )
                    IconButton(onClick = {
                        searchedText = ""

                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Beige
                        )
                    }
                }
            }

            val filteredGames = state.games.filter { game ->
                game.gameName.contains(searchedText, ignoreCase = true) ||
                        game.genre.contains(searchedText, ignoreCase = true) ||
                        game.producer.contains(searchedText, ignoreCase = true)
            }

            item {
                var expanded by remember {
                    mutableStateOf(false)
                }
                var selectedText by remember {
                    mutableStateOf(value = "NAME")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Edit",
                            tint = Beige
                        )
                        Text(text = selectedText,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.clickable { expanded = true })
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        SortType.values().forEach { sortType ->
                            DropdownMenuItem(onClick = {
                                selectedText = sortType.name
                                expanded = false
                                onEvent(GameEvent.SortGames(sortType))
                            }) {
                                Text(text = sortType.name)
                            }
                        }
                    }
                }
            }


            items(filteredGames) { game ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Text(
                                text = "${game.gameName} ",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Text(
                                text = "${game.genre} ",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                        Text(
                            text = "${game.producer} ",
                            color = Color.White,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp
                        )
                        Row {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color.Yellow
                            )
                            Text(
                                text = game.rating,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                    Column {
                        IconButton(onClick = {
                            onEvent(GameEvent.DeleteGame(game))
                            onEvent(GameEvent.isDeleted)
                            selectedGame = game
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete contact",
                                tint = Beige
                            )
                        }
                        IconButton(onClick = {
                            selectedGame = game
                            onEvent(GameEvent.EditTrue)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Beige
                            )
                        }
                    }
                }
            }
        }
    }
}