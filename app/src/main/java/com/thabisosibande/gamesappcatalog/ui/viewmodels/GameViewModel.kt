package com.thabisosibande.gamesappcatalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameViewModel : ViewModel() {

    private val gameRepository: GameRepository = GameRepository(createGameApiService())

    // MutableLiveData to hold the list of games
    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games

    // Function to fetch the games for the specified platform
    fun getGamesForPlatform(platform: String) {
        viewModelScope.launch {
            try {
                val gamesList = gameRepository.getGamesForPlatform(platform)
                _games.postValue(gamesList)
            } catch (e: Exception) {
                // Handle error if the API request was not successful
                // e.g., Show an error message to the user
            }
        }
    }

    // Create the Retrofit instance here (Step 4)
    private fun createGameApiService(): GameApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GameApiService::class.java)
    }
}
