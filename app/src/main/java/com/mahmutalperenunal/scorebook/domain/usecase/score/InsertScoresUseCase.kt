package com.mahmutalperenunal.scorebook.domain.usecase.score

import com.mahmutalperenunal.scorebook.data.local.entity.ScoreEntity
import com.mahmutalperenunal.scorebook.domain.repository.ScoreRepository

class InsertScoresUseCase(private val repository: ScoreRepository) {
    suspend operator fun invoke(scores: List<ScoreEntity>) {
        repository.insertScores(scores)
    }
}