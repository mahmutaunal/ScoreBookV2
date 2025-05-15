package com.mahmutalperenunal.scorebook.domain.usecase.game

import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity
import com.mahmutalperenunal.scorebook.domain.repository.GameRepository

class GetGameByIdUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(id: String): GameEntity? {
        return repository.getGameById(id)
    }
}