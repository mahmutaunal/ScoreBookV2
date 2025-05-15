package com.mahmutalperenunal.scorebook.domain.model

import com.mahmutalperenunal.scorebook.utils.enums.ScoreColor

data class ScoreModel(
    val id: String,
    val round: Int,
    val playerId: String,
    val score: Int,
    val multiplier: Int = 1,
    val color: ScoreColor = ScoreColor.NONE
)