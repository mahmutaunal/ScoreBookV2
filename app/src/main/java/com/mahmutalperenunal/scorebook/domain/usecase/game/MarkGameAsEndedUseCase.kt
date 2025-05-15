package com.mahmutalperenunal.scorebook.domain.usecase.game

import com.mahmutalperenunal.scorebook.domain.repository.GameRepository

class MarkGameAsEndedUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(gameId: String) {
        repository.markGameAsEnded(gameId)
    }
}