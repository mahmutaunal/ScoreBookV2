package com.mahmutalperenunal.scorebook.domain.usecase.score

import com.mahmutalperenunal.scorebook.domain.repository.ScoreRepository

class ObserveScoresForGameUseCase(private val repository: ScoreRepository) {
    operator fun invoke(gameId: String) = repository.observeScoresForGame(gameId)
}