package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.utils.enums.ScoreColor

data class ScoreInput(
    val baseScore: Int,
    val multiplier: Int = 1,
    val color: ScoreColor = ScoreColor.NONE
)