package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.utils.enums.ScoreColor

class PistiScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            val base = settings.pistiBaseScore ?: 10
            val aceBonus = settings.pistiAceBonus ?: 5
            val doubleBonus = if (settings.pistiDoubleBonus) 20 else 0
            var total = base
            if (input.color == ScoreColor.YELLOW) total += aceBonus
            if (input.multiplier >= 2) total += doubleBonus
            ScoreResult(playerId, total)
        }
    }
}