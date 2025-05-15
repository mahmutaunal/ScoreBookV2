package com.mahmutalperenunal.scorebook.ui.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutalperenunal.scorebook.domain.mapper.toModel
import com.mahmutalperenunal.scorebook.domain.model.GameModel
import com.mahmutalperenunal.scorebook.domain.model.GameSummaryModel
import com.mahmutalperenunal.scorebook.domain.model.PlayerModel
import com.mahmutalperenunal.scorebook.domain.usecase.game.GetGameByIdUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.game.UpdateFavoriteStatusUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.GetPlayersForGameUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.score.ObserveScoresForGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSummaryViewModel @Inject constructor(
    private val getGameById: GetGameByIdUseCase,
    private val getPlayers: GetPlayersForGameUseCase,
    private val observeScores: ObserveScoresForGameUseCase,
    private val updateFavorite: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val _summary = MutableStateFlow<GameSummaryModel?>(null)
    val summary: StateFlow<GameSummaryModel?> = _summary

    private var currentGameId: String? = null
    private var scoresJob: Job? = null
    private var scoresScope: CoroutineScope? = null

    fun loadSummary(gameId: String) {
        if (gameId == currentGameId) return

        currentGameId = gameId
        scoresJob?.cancel()
        scoresScope?.cancel()

        viewModelScope.launch {
            val gameEntity = getGameById(gameId) ?: return@launch
            val game = gameEntity.toModel()
            val players = getPlayers(gameId).map { it.toModel() }

            scoresScope = CoroutineScope(viewModelScope.coroutineContext)
            scoresJob = scoresScope?.launch {
                collectScoresAndBuildSummary(gameId, game, players)
            }
        }
    }

    private suspend fun collectScoresAndBuildSummary(
        gameId: String,
        game: GameModel,
        players: List<PlayerModel>
    ) {
        observeScores(gameId).collectLatest { scoreEntities ->
            val scores = scoreEntities.map { it.toModel() }
            val totals = scores.groupBy { it.playerId }.mapValues { it.value.sumOf { s -> s.score } }
            val winner = players.maxByOrNull { totals[it.id] ?: 0 }

            _summary.value = GameSummaryModel(
                game = game,
                players = players,
                scores = scores,
                winner = winner,
                totalScores = totals
            )
        }
    }

    fun toggleFavorite() {
        val current = _summary.value ?: return
        val newStatus = !current.game.isFavorite

        viewModelScope.launch {
            updateFavorite(current.game.id, newStatus)
            _summary.value = current.copy(game = current.game.copy(isFavorite = newStatus))
        }
    }

    override fun onCleared() {
        super.onCleared()
        scoresScope?.cancel()
    }
}