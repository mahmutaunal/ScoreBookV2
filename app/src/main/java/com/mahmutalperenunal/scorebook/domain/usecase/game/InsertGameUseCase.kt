package com.mahmutalperenunal.scorebook.domain.usecase.game

import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity
import com.mahmutalperenunal.scorebook.domain.repository.GameRepository

class InsertGameUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(game: GameEntity) {
        repository.insertGame(game)
    }
}