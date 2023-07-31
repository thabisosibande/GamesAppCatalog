package com.thabisosibande.gamesappcatalog.ui.adapters
import com.thabisosibande.gamesappcatalog.R
import com.thabisosibande.gamesappcatalog.data.model.Game
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class GameAdapter(private var gameList: List<Game>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var filteredGameList: List<Game> = gameList

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameImage: ImageView = itemView.findViewById(R.id.gameImage)
        val gameName: TextView = itemView.findViewById(R.id.gameName)
        val gameGenre: TextView = itemView.findViewById(R.id.gameGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game_card, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredGameList.size
    }

    // Function to update the list of games and apply filtering
    fun filter(query: String) {
        filteredGameList = if (query.isBlank()) {
            gameList
        } else {
            gameList.filter { game ->
                game.title.contains(query, ignoreCase = true) ||
                        game.genre.contains(query, ignoreCase = true)
                // Add more conditions here to filter based on other fields if needed
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = filteredGameList[position]

        // Bind game data to views
        holder.gameName.text = game.title
        holder.gameGenre.text = game.genre

        // Load game thumbnail image using Glide (or any other image loading library you prefer)
        Glide.with(holder.itemView)
            .load(game.thumbnail)
            .centerCrop()
            .into(holder.gameImage)
    }

    fun updateData(newGameList: List<Game>) {
        gameList = newGameList
        filter("") // Reset the filter with an empty query to update filteredGameList
        notifyDataSetChanged()
    }
}