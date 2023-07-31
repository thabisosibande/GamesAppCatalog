package com.thabisosibande.gamesappcatalog

class GameRepository(private val gameApiService: GameApiService) {
    suspend fun getGamesForPlatform(platform: String): List<Game> {
        return gameApiService.getGamesForPlatform(platform)
    }
}
