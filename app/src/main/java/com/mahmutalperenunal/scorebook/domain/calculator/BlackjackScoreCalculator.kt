package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings

class BlackjackScoreCalculator : ScoreCalculator {
    override fun calculateScores(inputs: Map<String, ScoreInput>, settings: GameSettings): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            val max = 21
            val overPenalty = settings.blackjackOver21Penalty ?: 10
            val value = if (settings.blackjackAceAsEleven && input.baseScore == 1) 11 else input.baseScore
            val total = if (value > max) -overPenalty else value * input.multiplier
            ScoreResult(playerId, total)
        }
    }
}