package com.plcoding.roomguideandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddContactDialog(
    state: GameState,
    onEvent: (GameEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(GameEvent.HideDialog)
        },
        title = { Text(text = "Add Game") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.gameName,
                    onValueChange = {
                        onEvent(GameEvent.SetGameName(it))
                    },
                    placeholder = {
                        Text(text = "Game Name")
                    }
                )
                TextField(
                    value = state.producer,
                    onValueChange = {
                        onEvent(GameEvent.SetProducer(it))
                    },
                    placeholder = {
                        Text(text = "Game Producer")
                    }
                )
                TextField(
                    value = state.rating,
                    onValueChange = {
                        onEvent(GameEvent.SetRating(it))
                    },
                    placeholder = {
                        Text(text = "Game Rating")
                    }
                )
                TextField(
                    value = state.gameGenre,
                    onValueChange = {
                        onEvent(GameEvent.SetGameGenre(it))
                    },
                    placeholder = {
                        Text(text = "Game Genre")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(GameEvent.SaveGame)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}