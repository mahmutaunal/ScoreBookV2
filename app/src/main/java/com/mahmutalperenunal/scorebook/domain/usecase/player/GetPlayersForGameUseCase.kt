package com.mahmutalperenunal.scorebook.domain.usecase.player

import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.domain.repository.PlayerRepository

class GetPlayersForGameUseCase(private val repository: PlayerRepository) {
    suspend operator fun invoke(gameId: String): List<PlayerEntity> {
        return repository.getPlayersForGame(gameId)
    }
}