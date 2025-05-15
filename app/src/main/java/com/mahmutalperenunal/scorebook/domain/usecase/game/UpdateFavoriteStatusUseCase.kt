package com.mahmutalperenunal.scorebook.domain.usecase.game

import com.mahmutalperenunal.scorebook.domain.repository.GameRepository

class UpdateFavoriteStatusUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(gameId: String, isFavorite: Boolean) {
        repository.updateFavoriteStatus(gameId, isFavorite)
    }
}