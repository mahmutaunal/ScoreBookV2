package com.mahmutalperenunal.scorebook.di

import com.mahmutalperenunal.scorebook.data.local.dao.GameDao
import com.mahmutalperenunal.scorebook.data.local.dao.PlayerDao
import com.mahmutalperenunal.scorebook.data.local.dao.ScoreDao
import com.mahmutalperenunal.scorebook.data.repository.GameRepositoryImpl
import com.mahmutalperenunal.scorebook.data.repository.PlayerRepositoryImpl
import com.mahmutalperenunal.scorebook.data.repository.ScoreRepositoryImpl
import com.mahmutalperenunal.scorebook.domain.repository.GameRepository
import com.mahmutalperenunal.scorebook.domain.repository.PlayerRepository
import com.mahmutalperenunal.scorebook.domain.repository.ScoreRepository
import com.mahmutalperenunal.scorebook.domain.usecase.game.GetGameByIdUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.game.InsertGameUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.game.MarkGameAsEndedUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.game.UpdateFavoriteStatusUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.GetPlayersForGameUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.InsertPlayersUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.player.UpdatePlayerStatusUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.score.InsertScoresUseCase
import com.mahmutalperenunal.scorebook.domain.usecase.score.ObserveScoresForGameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGameRepository(dao: GameDao): GameRepository = GameRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePlayerRepository(dao: PlayerDao): PlayerRepository = PlayerRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideScoreRepository(dao: ScoreDao): ScoreRepository = ScoreRepositoryImpl(dao)

    @Provides
    fun provideGetGameByIdUseCase(repository: GameRepository) = GetGameByIdUseCase(repository)

    @Provides
    fun provideInsertGameUseCase(repository: GameRepository) = InsertGameUseCase(repository)

    @Provides
    fun provideMarkGameAsEndedUseCase(repository: GameRepository) = MarkGameAsEndedUseCase(repository)

    @Provides
    fun provideUpdateFavoriteStatusUseCase(repository: GameRepository) = UpdateFavoriteStatusUseCase(repository)

    @Provides
    fun provideGetPlayersForGameUseCase(repository: PlayerRepository) = GetPlayersForGameUseCase(repository)

    @Provides
    fun provideInsertPlayersUseCase(repository: PlayerRepository) = InsertPlayersUseCase(repository)

    @Provides
    fun provideUpdatePlayerStatusUseCase(repository: PlayerRepository) = UpdatePlayerStatusUseCase(repository)

    @Provides
    fun provideInsertScoresUseCase(repository: ScoreRepository) = InsertScoresUseCase(repository)

    @Provides
    fun provideObserveScoresForGameUseCase(repository: ScoreRepository) = ObserveScoresForGameUseCase(repository)
}