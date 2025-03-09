package com.plcoding.roomguideandroid

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    val gameName: String,
    val producer: String,
    val rating: String,
    val genre: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
