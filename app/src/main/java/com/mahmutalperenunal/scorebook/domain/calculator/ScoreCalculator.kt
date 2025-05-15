package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings

interface ScoreCalculator {
    fun calculateScores(
        inputs: Map<String, ScoreInput>,
        settings: GameSettings
    ): List<ScoreResult>
}