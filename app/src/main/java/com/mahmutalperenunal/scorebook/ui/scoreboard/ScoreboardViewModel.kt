package com.mahmutalperenunal.scorebook.ui.scoreboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutalperenunal.scorebook.domain.calculator.ScoreCalculatorFactory
import com.mahmutalperenunal.scorebook.domain.calculator.ScoreInput
import com.mahmutalperenunal.scorebook.utils.extensions.copyWith
import com.mahmutalperenunal.scorebook.domain.mapper.toEntity
import com.mahmutalperenunal.scorebook.domain.mapper.toModel
import com.mahmutalperenunal.scorebook.domain.model.GameModel
import com.mahmutalperenunal.scorebook.domain.model.PlayerModel
import com.mahmutalperenunal.scorebook.domain.model.ScoreModel
import com.mahmutalperenunal.scorebook.domain.usecase.game.GetGameByIdUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.game.InsertGameUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.game.MarkGameAsEndedUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.GetPlayersForGameUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.UpdatePlayerStatusUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.score.InsertScoresUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.score.ObserveScoresForGameUseCase
import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ScoreboardViewModel @Inject constructor(
    private val getGameById: GetGameByIdUseCase,
    private val getPlayers: GetPlayersForGameUseCase,
    private val observeScores: ObserveScoresForGameUseCase,
    private val insertScores: InsertScoresUseCase,
    private val markGameAsEnded: MarkGameAsEndedUseCase,
    private val updatePlayerStatus: UpdatePlayerStatusUseCase,
    private val insertGameUseCase: InsertGameUseCase,
) : ViewModel() {

    private val _game = MutableStateFlow<GameModel?>(null)
    val game: StateFlow<GameModel?> = _game

    private val _players = MutableStateFlow<List<PlayerModel>>(emptyList())
    val players: StateFlow<List<PlayerModel>> = _players

    private val _scores = MutableStateFlow<List<ScoreModel>>(emptyList())
    val scores: StateFlow<List<ScoreModel>> = _scores

    private val _totalScores = MutableStateFlow<Map<String, Int>>(emptyMap())
    val totalScores: StateFlow<Map<String, Int>> = _totalScores

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadGame(gameId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val gameEntity = getGameById(gameId) ?: return@launch
                _game.value = gameEntity.toModel()
                _players.value = getPlayers(gameId).map { it.toModel() }
                observeScores(gameId).collectLatest { scores ->
                    _scores.value = scores.map { it.toModel() }
                    _totalScores.value = calculateTotals(_scores.value)
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun calculateTotals(scores: List<ScoreModel>): Map<String, Int> {
        return scores.groupBy { it.playerId }
            .mapValues { entry -> entry.value.sumOf { it.score } }
    }

    fun addScores(gameId: String, round: Int, inputMap: Map<String, ScoreInput>) {
        if (_players.value.isEmpty()) {
            Log.e("AddScores", "Player list is empty, cannot add scores")
            return
        }

        val calculator = _game.value?.subGameType?.let { ScoreCalculatorFactory.getCalculator(it) } ?: return
        val results = calculator.calculateScores(inputMap, _game.value!!.settings)

        val scoreEntities = results.map { result ->
            ScoreModel(
                id = UUID.randomUUID().toString(),
                round = round,
                playerId = result.playerId,
                score = result.totalScore
            ).toEntity(gameId)
        }

        viewModelScope.launch {
            insertScores(scoreEntities)
        }

        Log.d("AddScores", "GameID: $gameId")
        inputMap.forEach { (playerId, input) ->
            Log.d("AddScores", "PlayerID: $playerId, Score: ${input.baseScore}")
        }

        _players.value.forEach {
            Log.d("AddScores", "Loaded Player: ${it.name} (${it.id})")
        }
    }

    fun endGame(gameId: String, winnerId: String?) {
        viewModelScope.launch {
            markGameAsEnded(gameId)
            winnerId?.let {
                updatePlayerStatus(it, PlayerStatus.WINNER)
            }
        }
    }

    fun getWinnerIdOrNull(): String? {
        val scores = _totalScores.value
        val maxScore = scores.values.maxOrNull() ?: return null
        val topScorers = scores.filterValues { it == maxScore }.keys

        return if (topScorers.size == 1) topScorers.first() else null
    }

    fun updateGameSettings(gameId: String, updatedSettings: Map<String, Any>) {
        viewModelScope.launch {
            _game.value?.let { currentGame ->
                val newSettings = currentGame.settings.copyWith(updatedSettings)
                val updatedGame = currentGame.copy(settings = newSettings)
                // Re-save updated GameEntity
                insertGameUseCase(updatedGame.toEntity())
                _game.value = updatedGame
            }
        }
    }
}