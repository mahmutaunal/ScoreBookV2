package com.mahmutalperenunal.scorebook.domain.usecase.player

import com.mahmutalperenunal.scorebook.domain.repository.PlayerRepository
import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus

class UpdatePlayerStatusUseCase(private val repository: PlayerRepository) {
    suspend operator fun invoke(playerId: String, status: PlayerStatus) {
        repository.updatePlayerStatus(playerId, status)
    }
}