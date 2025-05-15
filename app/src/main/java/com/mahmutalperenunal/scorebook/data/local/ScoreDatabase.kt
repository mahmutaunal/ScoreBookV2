package com.mahmutalperenunal.scorebook.data.local

import android.content.Context
import androidx.room.*
import com.mahmutalperenunal.scorebook.data.local.dao.GameDao
import com.mahmutalperenunal.scorebook.data.local.dao.PlayerDao
import com.mahmutalperenunal.scorebook.data.local.dao.ScoreDao
import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity
import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.data.local.entity.ScoreEntity
import com.mahmutalperenunal.scorebook.data.local.typeconverters.EnumConverters
import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettingsConverter

@Database(
    entities = [GameEntity::class, PlayerEntity::class, ScoreEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    EnumConverters::class,
    GameSettingsConverter::class
)
abstract class ScoreDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun playerDao(): PlayerDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile private var INSTANCE: ScoreDatabase? = null

        fun getInstance(context: Context): ScoreDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "score_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}