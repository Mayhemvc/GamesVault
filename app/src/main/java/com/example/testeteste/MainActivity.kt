package com.example.testeteste

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testeteste.databinding.ActivityMainBinding
import com.example.testeteste.databinding.ItemGameBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listView.adapter = GameListAdapter(gameItems)
        binding.listView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

class GameListAdapter(val list: List<GameListItem>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
        val context: Context = binding.root.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.gameCover.setImageResource(item.coverResId)
        holder.binding.title.text = item.title

        holder.binding.badge.badgeText.text = item.genre

        holder.binding.hoursPlayed.text =
            holder.context.getString(R.string.hours_played, item.hoursPlayed)

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_GAME_ID, item.id)
            holder.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = list.size
}

data class GameListItem(
    val id: String,
    val title: String,
    val genre: String,
    val hoursPlayed: Int,
    @androidx.annotation.DrawableRes val coverResId: Int
)

val gameItems: List<GameListItem> = listOf(
    GameListItem("1", "Life is Strange", "Indie", 2, R.drawable.cover_life_is_strange),
    GameListItem("2", "Elden Ring", "Souls-like", 120, R.drawable.cover_elden_ring),
    GameListItem("3", "Cyberpunk 2077", "RPG", 15, R.drawable.cover_cyberpunk_2077),
    GameListItem("4", "Hollow Knight", "Metroidvania", 45, R.drawable.cover_hollow_knight),
    GameListItem("5", "Deadlock", "MOBA", 10, R.drawable.cover_deadlock),
    GameListItem("6", "Red Dead Redemption 2", "Mundo Aberto", 85, R.drawable.cover_rdr2)
)
