package com.plcoding.roomguideandroid

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Upsert
    suspend fun upsertGame(game: Game)

    @Update
    suspend fun updateGame(game:Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("SELECT * FROM game ORDER BY gameName ASC")
    fun getContactsOrderedByFirstName(): Flow<List<Game>>

    @Query("SELECT * FROM game ORDER BY producer ASC")
    fun getContactsOrderedByLastName(): Flow<List<Game>>

    @Query("SELECT * FROM game ORDER BY rating DESC")
    fun getContactsOrderedByPhoneNumber(): Flow<List<Game>>

    @Query("SELECT * FROM game ORDER BY genre ASC")
    fun getContactsOrderedByGenre(): Flow<List<Game>>
}