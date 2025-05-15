package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings

class BatakScoreCalculator : ScoreCalculator {
    override fun calculateScores(
        inputs: Map<String, ScoreInput>,
        settings: GameSettings
    ): List<ScoreResult> {
        return inputs.map { (playerId, input) ->
            var total = input.baseScore * input.multiplier
            if (settings.batakAuctionRequired && input.baseScore < (settings.batakMinAuctionScore
                    ?: 0)
            ) {
                total -= 10
            }
            if (settings.batakOverbidPenalty && input.baseScore > 13) {
                total -= 5
            }
            ScoreResult(playerId, total)
        }
    }
}