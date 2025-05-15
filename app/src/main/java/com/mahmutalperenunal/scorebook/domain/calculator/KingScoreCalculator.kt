package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings

class KingScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            val modeMultiplier = when (settings.kingPointMode) {
                "Standard" -> 1
                "Negative" -> -1
                "Ters" -> -2
                else -> 1
            }
            val total = input.baseScore * input.multiplier * modeMultiplier
            ScoreResult(playerId, total)
        }
    }
}