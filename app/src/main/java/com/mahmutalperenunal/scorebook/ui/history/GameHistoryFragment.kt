package com.mahmutalperenunal.scorebook.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahmutalperenunal.scorebook.databinding.FragmentGameHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameHistoryFragment : Fragment() {

    private var _binding: FragmentGameHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameHistoryViewModel by viewModels()
    private lateinit var adapter: GameHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        observeGameHistory()
    }

    private fun setupAdapter() {
        adapter = GameHistoryAdapter(
            onGameClick = { summary ->
                if (summary.game.hasEnded) {
                    val action = GameHistoryFragmentDirections.actionGameHistoryFragmentToGameSummaryFragment(summary.game.id)
                    findNavController().navigate(action)
                } else {
                    val action = GameHistoryFragmentDirections.actionGameHistoryFragmentToScoreboardFragment(summary.game.id)
                    findNavController().navigate(action)
                }
            },
            onToggleFavorite = { summary ->
                viewModel.toggleFavorite(summary.game.id, !summary.game.isFavorite)
            }
        )
        binding.rvGameHistory.adapter = adapter
    }

    private fun observeGameHistory() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.games.collectLatest { summaries ->
                    adapter.submitList(summaries)
                    //binding.tvEmpty.visibility = if (summaries.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}