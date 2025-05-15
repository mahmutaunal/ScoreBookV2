package com.mahmutalperenunal.scorebook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmutalperenunal.scorebook.data.local.entity.ScoreEntity

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertScores(scores: List<ScoreEntity>)

    @Query("SELECT * FROM scores WHERE gameId = :gameId ORDER BY round ASC")
    fun observeScoresForGame(gameId: String): kotlinx.coroutines.flow.Flow<List<ScoreEntity>>

    @Query("SELECT * FROM scores WHERE gameId = :gameId AND playerId = :playerId ORDER BY round ASC")
    fun observeScoresForPlayer(gameId: String, playerId: String): kotlinx.coroutines.flow.Flow<List<ScoreEntity>>
}