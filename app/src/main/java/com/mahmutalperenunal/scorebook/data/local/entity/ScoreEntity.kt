package com.mahmutalperenunal.scorebook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mahmutalperenunal.scorebook.utils.enums.ScoreColor

@Entity(
    tableName = "scores",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gameId"), Index("playerId")]
)
data class ScoreEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val gameId: String,
    val playerId: String,
    val round: Int,
    val score: Int,
    val multiplier: Int = 1,
    val color: ScoreColor = ScoreColor.NONE
)