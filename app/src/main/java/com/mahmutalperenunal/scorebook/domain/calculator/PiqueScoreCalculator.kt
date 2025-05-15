package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.utils.enums.ScoreColor

class PiqueScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            var total = input.baseScore
            if (settings.piqueSpecialCards && input.color == ScoreColor.RED) total += 5
            if (settings.piqueDrawPenalty) total -= 3
            ScoreResult(playerId, total)
        }
    }
}