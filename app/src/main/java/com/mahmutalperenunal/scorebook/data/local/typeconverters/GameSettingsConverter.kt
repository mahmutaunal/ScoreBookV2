package com.mahmutalperenunal.scorebook.data.local.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson

class GameSettingsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromGameSettings(settings: GameSettings): String = gson.toJson(settings)

    @TypeConverter
    fun toGameSettings(json: String): GameSettings = gson.fromJson(json, GameSettings::class.java)
}