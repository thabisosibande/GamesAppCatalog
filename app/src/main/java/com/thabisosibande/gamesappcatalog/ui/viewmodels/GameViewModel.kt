package com.thabisosibande.gamesappcatalog.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thabisosibande.gamesappcatalog.GameApiService
import com.thabisosibande.gamesappcatalog.data.repository.GameRepository
import com.thabisosibande.gamesappcatalog.data.model.Game
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameViewModel : ViewModel() {

    private val gameRepository: GameRepository = GameRepository(createGameApiService())

    // MutableLiveData to hold the list of games
    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games

    // Variable to hold the total number of games
    private val _totalGamesCount = MutableLiveData<Int>()
    val totalGamesCount: LiveData<Int> get() = _totalGamesCount

    // Function to fetch the games for the specified platform
    fun getGamesForPlatform(platform: String) {
        viewModelScope.launch {
            try {
                val gamesList = gameRepository.getGamesForPlatform(platform)
                _games.postValue(gamesList)

                // Update the total number of games
                _totalGamesCount.postValue(gamesList.size)
            } catch (e: Exception) {
            }
        }
    }

    private fun createGameApiService(): GameApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GameApiService::class.java)
    }
}
