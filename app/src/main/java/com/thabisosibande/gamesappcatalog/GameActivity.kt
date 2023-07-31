package com.thabisosibande.gamesappcatalog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thabisosibande.gamesappcatalog.ui.adapters.GameAdapter
import com.thabisosibande.gamesappcatalog.ui.viewmodels.GameViewModel

class GameActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var gameAdapter: GameAdapter

    // layouts
    private lateinit var loadingLayout: RelativeLayout
    private lateinit var contentLayout: RelativeLayout

    // ui elements
    private lateinit var totalGamesTextView: TextView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingLayout = findViewById(R.id.loadingLayout)
        contentLayout = findViewById(R.id.contentLayout)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        totalGamesTextView = findViewById(R.id.runningTotalText)
        searchView = findViewById(R.id.searchView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Set up the RecyclerView with the custom adapter
        gameAdapter = GameAdapter(emptyList()) // Pass an empty list initially
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = gameAdapter

        val fetchGamesButton = findViewById<Button>(R.id.myButton)
        fetchGamesButton.setOnClickListener {
            loadingLayout.visibility = View.VISIBLE
            fetchGamesButton.visibility = View.GONE

            // Fetch games for the specified platform (e.g., "pc") when the button is clicked
            gameViewModel.getGamesForPlatform("pc")
        }

        // Observe the LiveData from the ViewModel
        gameViewModel.games.observe(this, Observer { games ->
            // Update the adapter with the new list of games
            gameAdapter.updateData(games)

            loadingLayout.visibility = View.GONE
            contentLayout.visibility = View.VISIBLE
        })

        // Observe the totalGamesCount LiveData to update the TextView
        gameViewModel.totalGamesCount.observe(this, Observer { count ->
            // Update the totalGamesTextView with the count of games
            totalGamesTextView.text = "Total Games: $count"
        })

        // Set up the search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                gameAdapter.filter(newText)
                return true
            }
        })
    }
}

