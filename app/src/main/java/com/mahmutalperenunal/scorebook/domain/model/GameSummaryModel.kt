package com.mahmutalperenunal.scorebook.domain.model

data class GameSummaryModel(
    val game: GameModel,
    val players: List<PlayerModel>,
    val scores: List<ScoreModel>,
    val winner: PlayerModel?,
    val totalScores: Map<String, Int> = emptyMap()
)