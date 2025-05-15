package com.mahmutalperenunal.scorebook.data.local.entity

import androidx.room.*
import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettingsConverter
import com.mahmutalperenunal.scorebook.utils.enums.SubGameType
import java.util.UUID

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val subGameType: SubGameType,
    val createdAt: Long = System.currentTimeMillis(),
    val winnerPlayerId: String? = null,
    val isFavorite: Boolean = false,

    val playerCount: Int = 0,
    val hasEnded: Boolean = false,

    @TypeConverters(GameSettingsConverter::class)
    val settings: GameSettings = GameSettings()
)