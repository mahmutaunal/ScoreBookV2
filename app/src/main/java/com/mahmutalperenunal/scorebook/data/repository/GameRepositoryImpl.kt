package com.mahmutalperenunal.scorebook.data.repository

import com.mahmutalperenunal.scorebook.data.local.dao.GameDao
import com.mahmutalperenunal.scorebook.data.local.entity.GameEntity
import com.mahmutalperenunal.scorebook.domain.repository.GameRepository

class GameRepositoryImpl(private val dao: GameDao) : GameRepository {
    override suspend fun insertGame(game: GameEntity) = dao.insert(game)
    override suspend fun getGameById(id: String): GameEntity? = dao.getGameById(id)
    override suspend fun getAllGames(): List<GameEntity> = dao.getAllGames()
    override suspend fun getFavoriteGames(): List<GameEntity> = dao.getFavoriteGames()
    override suspend fun updateGame(game: GameEntity) = dao.update(game)
    override suspend fun deleteGame(game: GameEntity) = dao.delete(game)

    override suspend fun updateFavoriteStatus(gameId: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(gameId, isFavorite)
    }

    override suspend fun markGameAsEnded(gameId: String) {
        dao.markGameAsEnded(gameId)
    }
}