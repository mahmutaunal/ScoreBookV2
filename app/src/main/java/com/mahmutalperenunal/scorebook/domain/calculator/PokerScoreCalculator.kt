package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings

class PokerScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            var total = input.baseScore * input.multiplier
            if (!settings.pokerAllowAllIn && input.baseScore > (settings.pokerStartChips ?: 0)) {
                total = 0
            }
            ScoreResult(playerId, total)
        }
    }
}