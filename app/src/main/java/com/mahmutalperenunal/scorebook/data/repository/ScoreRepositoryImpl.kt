package com.mahmutalperenunal.scorebook.data.repository

import com.mahmutalperenunal.scorebook.data.local.dao.ScoreDao
import com.mahmutalperenunal.scorebook.data.local.entity.ScoreEntity
import com.mahmutalperenunal.scorebook.domain.repository.ScoreRepository

class ScoreRepositoryImpl(private val dao: ScoreDao) : ScoreRepository {
    override suspend fun insertScores(scores: List<ScoreEntity>) = dao.insertScores(scores)
    override fun observeScoresForGame(gameId: String) = dao.observeScoresForGame(gameId)
    override fun observeScoresForPlayer(gameId: String, playerId: String) = dao.observeScoresForPlayer(gameId, playerId)
}