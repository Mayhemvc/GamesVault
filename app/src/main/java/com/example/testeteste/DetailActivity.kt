package com.example.testeteste

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.testeteste.databinding.ActivityDetailBinding
import com.example.testeteste.databinding.BadgeLayoutBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GAME_ID = "game_id"
    }

    private lateinit var binding: ActivityDetailBinding

    private var currentGame: GameDetail = defaultGameDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.gameDetailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val gameId = intent.getStringExtra(EXTRA_GAME_ID)
        Log.d("DetailActivityTag", "Game ID recebido: $gameId")

        currentGame = gamesMockDetails[gameId] ?: defaultGameDetail

        populateGameData()

        binding.progressBackButton.setOnClickListener {
            currentGame.getPreviousHour()?.let {
                currentGame = it
                updateProgressViews()
            }
        }

        binding.progressNextButton.setOnClickListener {
            currentGame.getNextHour()?.let {
                currentGame = it
                updateProgressViews()
            }
        }

        setupBadges()

        ViewCompat.setOnApplyWindowInsetsListener(binding.gameDetailToolbar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBarInsets.top)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun populateGameData() {
        binding.gameCoverImage.setImageResource(currentGame.coverResId)
        binding.gameTitle.text = currentGame.title
        binding.synopsisTextView.text = currentGame.synopsis
        binding.developerTextView.text = currentGame.developer
        binding.publisherTextView.text = currentGame.publisher
        binding.releaseYearTextView.text = currentGame.releaseYear
        binding.genresTextView.text = currentGame.genres.joinToString(", ")

        updateProgressViews()
    }

    private fun updateProgressViews() {
        binding.hoursProgressTextView.text =
            getString(R.string.hours_progress, currentGame.currentHours, currentGame.totalHours)
        binding.hoursProgressPercentage.text = "${currentGame.progressPercentage}%"
        binding.hoursProgressIndicator.progress = currentGame.progressPercentage
    }

    private fun setupBadges() {
        val badgesList = listOf(
            "Jogo",
            currentGame.genres.firstOrNull() ?: "Geral",
            "${currentGame.totalHours}h campanha",
            "★ ${currentGame.rating}"
        )

        badgesList.take(2).forEach { text ->
            addBadgeView(binding.badgesRow1, text)
        }

        badgesList.drop(2).forEach { text ->
            addBadgeView(binding.badgesRow2, text)
        }
    }

    private fun addBadgeView(row: LinearLayout, text: String) {
        val badgeBinding = BadgeLayoutBinding.inflate(layoutInflater, row, false)
        badgeBinding.badgeText.text = text
        row.addView(badgeBinding.root)
    }
}

data class GameDetail(
    val id: String,
    val title: String,
    val synopsis: String,
    val developer: String,
    val publisher: String,
    val releaseYear: String,
    val currentHours: Int = 0,
    val totalHours: Int,
    val genres: List<String>,
    val rating: String = "9.5",
    @androidx.annotation.DrawableRes val coverResId: Int
) {
    init {
        require(totalHours > 0) { "totalHours deve ser maior que 0" }
        require(currentHours in 0..totalHours) { "currentHours deve estar entre 0 e totalHours" }
    }

    val progressPercentage: Int
        get() = ((currentHours.toFloat() / totalHours) * 100).toInt()


    fun getPreviousHour(): GameDetail? {
        return try {
            copy(currentHours = currentHours - 1)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    fun getNextHour(): GameDetail? {
        return try {
            copy(currentHours = currentHours + 1)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}

val defaultGameDetail = GameDetail(
    id = "1",
    title = "Life is Strange",
    synopsis = "Siga a história de Max Caulfield, uma estudante de fotografia que descobre ser capaz de rebobinar o tempo ao salvar a melhor amiga, Chloe Price. Ao usar o poder, ela salva a garota e descobre que ela é sua amiga de infância, Chloe Price. Cada escolha feita por Max altera o presente e o futuro, gerando consequências imprevisíveis um efeito borboleta.",
    developer = "DONTNOD Entertainment",
    publisher = "Square Enix",
    releaseYear = "2015",
    currentHours = 2,
    totalHours = 19,
    genres = listOf("Indie", "Ação", "Manipulação do tempo"),
    rating = "8.5",
    coverResId = R.drawable.cover_life_is_strange
)

val gamesMockDetails = mapOf(
    "1" to defaultGameDetail,
    "2" to GameDetail(
        id = "2",
        title = "Elden Ring",
        synopsis = "Levante-se, Maculado, e seja guiado pela graça para brandir o poder do Anel Prístino e se tornar um Lorde Prístino nas Terras Intermédias.",
        developer = "FromSoftware",
        publisher = "Bandai Namco",
        releaseYear = "2022",
        currentHours = 120,
        totalHours = 150,
        genres = listOf("Souls-like", "RPG", "Ação"),
        rating = "9.6",
        coverResId = R.drawable.cover_elden_ring
    ),
    "3" to GameDetail(
        id = "3",
        title = "Cyberpunk 2077",
        synopsis = "Night City muda qualquer um. Entre na pele de V, um mercenário fora da lei em busca de um implante único que carrega a chave para a imortalidade.",
        developer = "CD Projekt Red",
        publisher = "CD Projekt",
        releaseYear = "2020",
        currentHours = 15,
        totalHours = 60,
        genres = listOf("RPG", "Sci-Fi", "Ação"),
        rating = "8.9",
        coverResId = R.drawable.cover_cyberpunk_2077
    ),
    "4" to GameDetail(
        id = "4",
        title = "Hollow Knight",
        synopsis = "Desça às profundezas de um reino subterrâneo esquecido de insetos e heróis arruinados. Explore cavernas sinuosas e enfrente criaturas corrompidas.",
        developer = "Team Cherry",
        publisher = "Team Cherry",
        releaseYear = "2017",
        currentHours = 45,
        totalHours = 70,
        genres = listOf("Metroidvania", "Indie", "Ação"),
        rating = "9.5",
        coverResId = R.drawable.cover_hollow_knight
    ),
    "5" to GameDetail(
        id = "5",
        title = "Deadlock",
        synopsis = "O jogo se passa em uma versão alternativa de uma cidade inspirada na Nova York do século XX, com uma estética noir e elementos de ocultismo e fantasia urbana.",
        developer = "Valve",
        publisher = "Valve",
        releaseYear = "2024",
        currentHours = 10,
        totalHours = 1000,
        genres = listOf("MOBA", "Competitivo", "Estratégia"),
        rating = "9.4",
        coverResId = R.drawable.cover_deadlock
    ),
    "6" to GameDetail(
        id = "6",
        title = "Red Dead Redemption 2",
        synopsis = "Estados Unidos, 1899. O fim da era do Velho Oeste começou. Arthur Morgan e a gangue Van der Linde são forçados a fugir, roubar e lutar para sobreviver.",
        developer = "Rockstar Studios",
        publisher = "Rockstar Games",
        releaseYear = "2018",
        currentHours = 85,
        totalHours = 120,
        genres = listOf("Mundo Aberto", "Aventura", "Faroeste"),
        rating = "9.8",
        coverResId = R.drawable.cover_rdr2
    )
)
