package com.mahmutalperenunal.scorebook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus
import java.util.UUID

@Entity(
    tableName = "players",
    foreignKeys = [ForeignKey(
        entity = GameEntity::class,
        parentColumns = ["id"],
        childColumns = ["gameId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("gameId")]
)
data class PlayerEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val gameId: String,
    val order: Int,
    val status: PlayerStatus = PlayerStatus.ACTIVE
)