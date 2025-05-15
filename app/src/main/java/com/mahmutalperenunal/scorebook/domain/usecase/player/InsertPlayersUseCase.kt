package com.mahmutalperenunal.scorebook.domain.usecase.player

import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.domain.repository.PlayerRepository

class InsertPlayersUseCase(private val repository: PlayerRepository) {
    suspend operator fun invoke(players: List<PlayerEntity>) {
        repository.insertPlayers(players)
    }
}