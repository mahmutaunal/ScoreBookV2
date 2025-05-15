package com.mahmutalperenunal.scorebook.domain.usecase.game

import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity
import com.mahmutalperenunal.scorebook.domain.repository.GameRepository
import javax.inject.Inject

class GetAllGamesUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(): List<GameEntity> {
        return repository.getAllGames()
    }
}