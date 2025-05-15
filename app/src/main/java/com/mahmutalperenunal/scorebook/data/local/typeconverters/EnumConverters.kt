package com.mahmutalperenunal.scorebook.data.local.typeconverters

import androidx.room.TypeConverter
import com.mahmutalperenunal.scorebook.utils.enums.SubGameType
import com.mahmutalperenunal.scorebook.utils.enums.PlayerStatus
import com.mahmutalperenunal.scorebook.utils.enums.ScoreColor

class EnumConverters {

    @TypeConverter
    fun fromGameType(type: SubGameType): String = type.name

    @TypeConverter
    fun toGameType(name: String): SubGameType = SubGameType.valueOf(name)

    @TypeConverter
    fun fromScoreColor(color: ScoreColor): String = color.name

    @TypeConverter
    fun toScoreColor(name: String): ScoreColor = ScoreColor.valueOf(name)

    @TypeConverter
    fun fromPlayerStatus(status: PlayerStatus): String = status.name

    @TypeConverter
    fun toPlayerStatus(name: String): PlayerStatus = PlayerStatus.valueOf(name)
}