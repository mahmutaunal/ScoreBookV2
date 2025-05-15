package com.mahmutalperenunal.scorebook.di

import android.content.Context
import androidx.room.Room
import com.mahmutalperenunal.scorebook.data.local.ScoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): ScoreDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ScoreDatabase::class.java,
                "score_db"
            ).build()
        }

        @Provides
        fun provideGameDao(db: ScoreDatabase) = db.gameDao()

        @Provides
        fun providePlayerDao(db: ScoreDatabase) = db.playerDao()

        @Provides
        fun provideScoreDao(db: ScoreDatabase) = db.scoreDao()
    }
}