package com.mahmutalperenunal.scorebook.data.repository

import com.mahmutalperenunal.scorebook.data.local.dao.PlayerDao
import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.domain.repository.PlayerRepository
import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus

class PlayerRepositoryImpl(private val dao: PlayerDao) : PlayerRepository {
    override suspend fun insertPlayers(players: List<PlayerEntity>) = dao.insertPlayers(players)
    override suspend fun getPlayersForGame(gameId: String): List<PlayerEntity> = dao.getPlayersForGame(gameId)
    override suspend fun updatePlayerStatus(playerId: String, status: PlayerStatus) {
        dao.updatePlayerStatus(playerId, status)
    }
}