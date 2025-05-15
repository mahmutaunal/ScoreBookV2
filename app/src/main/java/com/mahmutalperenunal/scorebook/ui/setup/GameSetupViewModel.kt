package com.mahmutalperenunal.scorebook.ui.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity
import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.domain.usecase.game.InsertGameUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.InsertPlayersUseCase
import com.mahmutalperenunal.scorebook.utils.enums.SubGameType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSetupViewModel @Inject constructor(
    private val insertGameUseCase: InsertGameUseCase,
    private val insertPlayersUseCase: InsertPlayersUseCase
) : ViewModel() {

    private val _gameId = MutableStateFlow<String?>(null)
    val gameId: StateFlow<String?> = _gameId

    fun createGame(
        gameId: String,
        name: String,
        subGameType: SubGameType,
        players: List<PlayerEntity>,
        settings: GameSettings
    ) {
        viewModelScope.launch {
            val game = GameEntity(
                id = gameId,
                name = name,
                subGameType = subGameType,
                settings = settings
            )

            insertGameUseCase(game)
            insertPlayersUseCase(players)

            _gameId.value = gameId
        }
    }
}