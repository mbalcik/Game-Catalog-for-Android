package com.plcoding.roomguideandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.plcoding.roomguideandroid.ui.theme.BGcolor


@Composable
fun EditGameDialog(
    game: Game,
    onEvent: (GameEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var nameVal by remember { mutableStateOf(game.gameName) }
    var producerVal by remember { mutableStateOf(game.producer) }
    var ratingVal by remember { mutableStateOf(game.rating) }
    var genreVal by remember { mutableStateOf(game.genre) }

    onEvent(GameEvent.SetGameName(nameVal))
    onEvent(GameEvent.SetProducer(producerVal))
    onEvent(GameEvent.SetRating(ratingVal))
    onEvent(GameEvent.SetGameGenre(genreVal))

    AlertDialog(
        backgroundColor = BGcolor,
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = {
            onEvent(GameEvent.EditFalse)
            onEvent(GameEvent.SetGameName(""))
            onEvent(GameEvent.SetProducer(""))
            onEvent(GameEvent.SetRating(""))
            onEvent(GameEvent.SetGameGenre(""))
        },
        title = { Text(text = "Game Details", color = Color.White) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column {
                    Text(text = "Name", color = Color.White)
                    TextField(
                        value = nameVal,
                        onValueChange = {
                            nameVal = it
                            onEvent(GameEvent.SetGameName(it))
                        },
                        placeholder = {
                            Text(text = "Game Name")
                        },
                        textStyle = TextStyle(color = Color.White)
                    )
                }
                Column {
                    Text(text = "Producer", color = Color.White)
                    TextField(
                        value = producerVal,
                        onValueChange = {
                            producerVal = it
                            onEvent(GameEvent.SetProducer(it))
                        },
                        placeholder = {
                            Text(text = "Producer")
                        },
                        textStyle = TextStyle(color = Color.White)

                    )
                }
                Column {
                    Text(text = "Rating", color = Color.White)
                    TextField(
                        value = ratingVal,
                        onValueChange = {
                            ratingVal = it
                            onEvent(GameEvent.SetRating(it))
                        },
                        placeholder = {
                            Text(text = "Rating")
                        },
                        textStyle = TextStyle(color = Color.White)
                    )
                }
                Column {
                    Text(text = "Genre", color = Color.White)
                    TextField(
                        value = genreVal,
                        onValueChange = {
                            genreVal = it
                            onEvent(GameEvent.SetGameGenre(it))
                        },
                        placeholder = {
                            Text(text = "Genre")
                        },
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }
        },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(GameEvent.SetID(game.id))
                    onEvent(GameEvent.Update)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}