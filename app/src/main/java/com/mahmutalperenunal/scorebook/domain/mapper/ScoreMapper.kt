package com.mahmutalperenunal.scorebook.domain.mapper

import com.mahmutalperenunal.scorebook.data.local.entity.ScoreEntity
import com.mahmutalperenunal.scorebook.domain.model.ScoreModel

fun ScoreEntity.toModel(): ScoreModel {
    return ScoreModel(
        id = id,
        round = round,
        playerId = playerId,
        score = score,
        multiplier = multiplier,
        color = color
    )
}

fun ScoreModel.toEntity(gameId: String): ScoreEntity {
    return ScoreEntity(
        id = id,
        gameId = gameId,
        playerId = playerId,
        round = round,
        score = score,
        multiplier = multiplier,
        color = color
    )
}