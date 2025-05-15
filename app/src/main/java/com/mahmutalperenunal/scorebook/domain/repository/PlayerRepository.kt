package com.mahmutalperenunal.scorebook.domain.repository

import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus

interface PlayerRepository {
    suspend fun insertPlayers(players: List<PlayerEntity>)
    suspend fun getPlayersForGame(gameId: String): List<PlayerEntity>
    suspend fun updatePlayerStatus(playerId: String, status: PlayerStatus)
}