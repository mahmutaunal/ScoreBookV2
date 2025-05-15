package com.mahmutalperenunal.scorebook.ui.summary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.mahmutalperenunal.scorebook.MainActivity
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.databinding.FragmentGameSummaryBinding
import com.mahmutalperenunal.scorebook.domain.model.GameSummaryModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameSummaryFragment : Fragment(R.layout.fragment_game_summary) {

    private var _binding: FragmentGameSummaryBinding? = null
    private val binding get() = _binding!!

    private val args: GameSummaryFragmentArgs by navArgs()
    private val viewModel: GameSummaryViewModel by viewModels()

    private val tileColors = listOf(
        R.color.score_tile_blue,
        R.color.score_tile_orange,
        R.color.score_tile_green,
        R.color.score_tile_purple,
        R.color.score_tile_pink,
        R.color.score_tile_yellow,
        R.color.score_tile_mint,
        R.color.score_tile_gray
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupClicks()
        observeSummary()
        viewModel.loadSummary(args.gameId)
    }

    private fun observeSummary() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.summary.collectLatest { it?.let(::displaySummary) }
            }
        }
    }

    private fun displaySummary(summary: GameSummaryModel) = with(binding) {
        tvWinnerName.text = summary.winner?.name ?: getString(R.string.no_winner)
        tvWinnerLabel.text = getString(R.string.winner)
        llScoreList.removeAllViews()

        summary.players
            .sortedByDescending { summary.totalScores[it.id] ?: 0 }
            .forEachIndexed { index, player ->
                val score = summary.totalScores[player.id] ?: 0
                llScoreList.addView(createScoreRow(player.name, score, tileColors[index % tileColors.size]))
            }
    }

    private fun createScoreRow(name: String, score: Int, colorResId: Int): LinearLayout = LinearLayout(requireContext()).apply {
        orientation = LinearLayout.HORIZONTAL
        setPadding(32, 24, 32, 24)
        background = ContextCompat.getDrawable(context, colorResId)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 8, 0, 8) }

        addView(TextView(context).apply {
            text = name
            textSize = 16f
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        })

        addView(TextView(context).apply {
            text = score.toString()
            textSize = 16f
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        })
    }

    private fun setupClicks() {
        binding.btnFinishGame.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        binding.btnRestartGame.setOnClickListener {
            // TODO: Yeni bir oyun başlatmak için Home’a yönlendirme yapılabilir.
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        binding.btnShare.setOnClickListener {
            viewModel.summary.value?.let { summary ->
                val winner = summary.winner?.name ?: "-"
                val totalScores = summary.players.joinToString("\n") {
                    "${it.name}: ${summary.totalScores[it.id] ?: 0}"
                }
                val textToShare = "${getString(R.string.winner)}: $winner\n\n$totalScores"
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textToShare)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, getString(R.string.share)))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}