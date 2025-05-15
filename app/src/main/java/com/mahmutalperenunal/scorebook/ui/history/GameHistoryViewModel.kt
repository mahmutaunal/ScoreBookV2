package com.mahmutalperenunal.scorebook.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutalperenunal.scorebook.domain.mapper.toModel
import com.mahmutalperenunal.scorebook.domain.model.GameSummaryModel
import com.mahmutalperenunal.scorebook.domain.usecase.game.GetAllGamesUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.game.UpdateFavoriteStatusUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.GetPlayersForGameUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.score.ObserveScoresForGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameHistoryViewModel @Inject constructor(
    private val getAllGames: GetAllGamesUseCase,
    private val getPlayers: GetPlayersForGameUseCase,
    private val observeScores: ObserveScoresForGameUseCase,
    private val updateFavorite: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val _games = MutableStateFlow<List<GameSummaryModel>>(emptyList())
    val games: StateFlow<List<GameSummaryModel>> = _games

    private fun loadGames() {
        viewModelScope.launch {
            val summaries = mutableListOf<GameSummaryModel>()
            val allGames = getAllGames()

            for (game in allGames) {
                val players = getPlayers(game.id).map { it.toModel() }
                val scores = observeScores(game.id).firstOrNull()?.map { it.toModel() } ?: emptyList()
                val totalScores = scores.groupBy { it.playerId }.mapValues { it.value.sumOf { s -> s.score } }
                val winner = players.maxByOrNull { totalScores[it.id] ?: 0 }

                summaries.add(
                    GameSummaryModel(
                        game = game.toModel(),
                        players = players,
                        scores = scores,
                        winner = winner,
                        totalScores = totalScores
                    )
                )
            }

            _games.value = summaries.sortedByDescending { it.game.createdAt }
        }
    }

    fun toggleFavorite(gameId: String, newStatus: Boolean) {
        viewModelScope.launch {
            updateFavorite(gameId, newStatus)
            loadGames()
        }
    }
}