package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.utils.enums.ScoreColor

class OkeyScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            val base = input.baseScore
            val multiplier = input.multiplier
            val colorBonus = when (input.color) {
                ScoreColor.RED -> settings.okeyColorMultiplier ?: 2
                ScoreColor.BLUE -> (settings.okeyColorMultiplier ?: 2) + 1
                ScoreColor.YELLOW -> (settings.okeyColorMultiplier ?: 2) + 2
                ScoreColor.BLACK -> (settings.okeyColorMultiplier ?: 2) + 3
                else -> 1
            }

            val pairBonus = settings.okeyPairBonus ?: 0
            val score = (base + pairBonus) * multiplier * colorBonus

            ScoreResult(playerId, score)
        }
    }
}