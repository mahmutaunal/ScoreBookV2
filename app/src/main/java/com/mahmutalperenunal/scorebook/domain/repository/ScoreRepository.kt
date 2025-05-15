package com.mahmutalperenunal.scorebook.domain.repository

import com.mahmutalperenunal.scorebook.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    suspend fun insertScores(scores: List<ScoreEntity>)
    fun observeScoresForGame(gameId: String): Flow<List<ScoreEntity>>
    fun observeScoresForPlayer(gameId: String, playerId: String): Flow<List<ScoreEntity>>
}