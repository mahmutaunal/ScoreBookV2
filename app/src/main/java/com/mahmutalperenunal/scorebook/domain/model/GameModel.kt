package com.mahmutalperenunal.scorebook.domain.model

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.utils.enums.SubGameType

data class GameModel(
    val id: String,
    val name: String,
    val subGameType: SubGameType,
    val isFavorite: Boolean,
    val createdAt: Long,
    val settings: GameSettings,
    val winnerPlayerId: String? = null,
    val playerCount: Int = 0,
    val hasEnded: Boolean = false
)