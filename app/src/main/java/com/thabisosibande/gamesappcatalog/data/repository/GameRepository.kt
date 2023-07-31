package com.thabisosibande.gamesappcatalog.data.repository

import com.thabisosibande.gamesappcatalog.GameApiService
import com.thabisosibande.gamesappcatalog.data.model.Game

class GameRepository(private val gameApiService: GameApiService) {
    suspend fun getGamesForPlatform(platform: String): List<Game> {
        return gameApiService.getGamesForPlatform(platform)
    }
}
