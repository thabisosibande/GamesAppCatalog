package com.thabisosibande.gamesappcatalog

import com.thabisosibande.gamesappcatalog.data.model.Game
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiService {
    @GET("games")
    suspend fun getGamesForPlatform(@Query("platform") platform: String): List<Game>
}
