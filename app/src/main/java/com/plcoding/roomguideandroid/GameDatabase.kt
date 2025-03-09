package com.plcoding.roomguideandroid

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Game::class],
    version = 3
)
abstract class GameDatabase: RoomDatabase() {

    abstract val dao: GameDao
}