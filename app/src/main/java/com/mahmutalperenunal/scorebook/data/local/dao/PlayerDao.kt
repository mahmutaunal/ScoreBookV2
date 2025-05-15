package com.mahmutalperenunal.scorebook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayers(players: List<PlayerEntity>)

    @Query("SELECT * FROM players WHERE gameId = :gameId")
    suspend fun getPlayersForGame(gameId: String): List<PlayerEntity>

    @Query("UPDATE players SET status = :status WHERE id = :playerId")
    suspend fun updatePlayerStatus(playerId: String, status: PlayerStatus)
}