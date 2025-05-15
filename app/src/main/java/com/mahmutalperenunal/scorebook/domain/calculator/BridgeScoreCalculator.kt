package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings

class BridgeScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            val roundCount = settings.bridgeRoundCount ?: 1
            val timedBonus = if (settings.bridgeTimedMatch) 5 else 0
            val impMultiplier = if (settings.bridgeUseIMP) 2 else 1
            val total = input.baseScore * impMultiplier + timedBonus * roundCount
            ScoreResult(playerId, total)
        }
    }
}