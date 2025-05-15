package com.mahmutalperenunal.scorebook.domain.mapper

import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity
import com.mahmutalperenunal.scorebook.domain.model.GameModel

fun GameEntity.toModel(): GameModel {
    return GameModel(
        id = id,
        name = name,
        subGameType = subGameType,
        isFavorite = isFavorite,
        createdAt = createdAt,
        settings = settings,
        winnerPlayerId = winnerPlayerId,
        playerCount = playerCount,
        hasEnded = hasEnded
    )
}

fun GameModel.toEntity(): GameEntity {
    return GameEntity(
        id = id,
        name = name,
        subGameType = subGameType,
        isFavorite = isFavorite,
        createdAt = createdAt,
        settings = settings,
        winnerPlayerId = winnerPlayerId,
        playerCount = playerCount,
        hasEnded = hasEnded
    )
}