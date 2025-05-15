package com.mahmutalperenunal.scorebook.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity)
    @Update
    suspend fun update(game: GameEntity)
    @Delete
    suspend fun delete(game: GameEntity)

    @Query("SELECT * FROM games ORDER BY createdAt DESC")
    suspend fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM games WHERE id = :id")
    suspend fun getGameById(id: String): GameEntity?

    @Query("SELECT * FROM games WHERE isFavorite = 1")
    suspend fun getFavoriteGames(): List<GameEntity>

    @Query("UPDATE games SET isFavorite = :isFavorite WHERE id = :gameId")
    suspend fun updateFavoriteStatus(gameId: String, isFavorite: Boolean)

    @Query("UPDATE games SET hasEnded = 1 WHERE id = :gameId")
    suspend fun markGameAsEnded(gameId: String)
}