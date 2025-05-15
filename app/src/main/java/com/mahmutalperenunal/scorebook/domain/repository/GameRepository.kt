package com.mahmutalperenunal.scorebook.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity

interface GameRepository {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGame(game: GameEntity)

    suspend fun getGameById(id: String): GameEntity?
    suspend fun getAllGames(): List<GameEntity>
    suspend fun getFavoriteGames(): List<GameEntity>
    suspend fun updateGame(game: GameEntity)
    suspend fun deleteGame(game: GameEntity)
    suspend fun updateFavoriteStatus(gameId: String, isFavorite: Boolean)
    suspend fun markGameAsEnded(gameId: String)
}