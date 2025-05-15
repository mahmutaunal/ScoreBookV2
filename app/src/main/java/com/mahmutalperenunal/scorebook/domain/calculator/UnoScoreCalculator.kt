package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings

class UnoScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            val penalty = if (settings.unoSpecialCardPenalty && input.baseScore < 0) 10 else 0
            val reversePenalty = if (settings.unoReversePenalty) 5 else 0
            val total = input.baseScore * input.multiplier - penalty - reversePenalty
            ScoreResult(playerId, total)
        }
    }
}