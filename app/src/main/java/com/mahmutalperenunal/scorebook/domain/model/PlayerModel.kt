package com.mahmutalperenunal.scorebook.domain.model

import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus

data class PlayerModel(
    val id: String,
    val name: String,
    val order: Int,
    val status: PlayerStatus = PlayerStatus.ACTIVE
)