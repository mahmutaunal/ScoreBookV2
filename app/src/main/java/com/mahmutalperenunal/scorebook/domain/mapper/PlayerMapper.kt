package com.mahmutalperenunal.scorebook.domain.mapper

import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.domain.model.PlayerModel

fun PlayerEntity.toModel(): PlayerModel {
    return PlayerModel(
        id = id,
        name = name,
        order = order,
        status = status
    )
}

fun PlayerModel.toEntity(gameId: String): PlayerEntity {
    return PlayerEntity(
        id = id,
        name = name,
        gameId = gameId,
        order = order,
        status = status
    )
}