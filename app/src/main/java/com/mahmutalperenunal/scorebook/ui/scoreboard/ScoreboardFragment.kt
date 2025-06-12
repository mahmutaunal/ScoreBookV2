package com.mahmutalperenunal.scorebook.ui.scoreboard

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.utils.extensions.toMap
import com.mahmutalperenunal.scorebook.databinding.FragmentScoreboardBinding
import com.mahmutalperenunal.scorebook.domain.calculator.ScoreInput
import com.mahmutalperenunal.scorebook.domain.model.PlayerTotalScoreModel
import com.mahmutalperenunal.scorebook.ui.addscore.AddScoreDialogFragment
import com.mahmutalperenunal.scorebook.ui.scoreboard.adapter.PlayerNameAdapter
import com.mahmutalperenunal.scorebook.ui.scoreboard.adapter.PlayerTotalAdapter
import com.mahmutalperenunal.scorebook.ui.scoreboard.adapter.ScoreRowAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class ScoreboardFragment : Fragment(R.layout.fragment_scoreboard) {

    private var _binding: FragmentScoreboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScoreboardViewModel by viewModels()
    private val args: ScoreboardFragmentArgs by navArgs()

    private lateinit var nameAdapter: PlayerNameAdapter
    private lateinit var totalAdapter: PlayerTotalAdapter
    private lateinit var scoreRowAdapter: ScoreRowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        setupRecyclerViews()
        setupButtons()
        observeAddScoreDialogResult()
        observeViewModel()
        viewModel.loadGame(args.gameId)
    }

    private fun setupToolbar() {
        binding.tbHeader.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.tbHeader.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_calculator -> {
                    findNavController().navigate(ScoreboardFragmentDirections.actionScoreboardFragmentToCalculatorFragment())
                    true
                }

                R.id.action_dice -> {
                    showDiceDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun setupRecyclerViews() {
        nameAdapter = PlayerNameAdapter()
        totalAdapter = PlayerTotalAdapter()
        scoreRowAdapter = ScoreRowAdapter()

        binding.rvPlayerNames.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = nameAdapter
        }

        binding.rvPlayerTotals.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = totalAdapter
        }

        binding.rvScoreRows.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scoreRowAdapter
        }
    }

    private fun setupButtons() {
        binding.btnAddScore.setOnClickListener {
            performHapticFeedback()
            openAddScoreDialog()
        }

        binding.btnGameSummary.setOnClickListener {
            performHapticFeedback()
            findNavController().navigate(ScoreboardFragmentDirections.actionScoreboardToSummary(args.gameId))
        }

        binding.btnEndGame.setOnClickListener {
            performHapticFeedback()
            showEndGameConfirmation()
        }
    }

    private fun performHapticFeedback() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            binding.root.performHapticFeedback(android.view.HapticFeedbackConstants.CONFIRM)
        } else {
            binding.root.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)
        }
    }

    private fun openAddScoreDialog() {
        val players = viewModel.players.value
        val settings = viewModel.game.value?.settings?.toMap() ?: emptyMap()

        val dialog = AddScoreDialogFragment.newInstance(ArrayList(players), settings)

        dialog.show(parentFragmentManager, AddScoreDialogFragment::class.java.simpleName)
    }

    private fun showEndGameConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_end_game_title))
            .setMessage(getString(R.string.dialog_end_game_message))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                dialog.dismiss()

                val winnerId = viewModel.getWinnerIdOrNull()
                viewModel.endGame(args.gameId, winnerId)

                findNavController().navigate(ScoreboardFragmentDirections.actionScoreboardToSummary(args.gameId))
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    private fun showDiceDialog() {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_dice_roller, null)

        val diceImage = dialogView.findViewById<ImageView>(R.id.ivDice)
        val resultText = dialogView.findViewById<TextView>(R.id.tvDiceResult)
        val rerollBtn = dialogView.findViewById<MaterialButton>(R.id.btnReroll)
        val closeBtn = dialogView.findViewById<MaterialButton>(R.id.btnClose)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        suspend fun animateAndShowDice() {
            resultText.visibility = View.INVISIBLE

            repeat(10) {
                val resId = getDiceDrawable(Random.nextInt(1, 7))
                diceImage.setImageResource(resId)
                delay(75)
            }
            val value = Random.nextInt(1, 7)
            diceImage.setImageResource(getDiceDrawable(value))
            resultText.text = "$value"

            resultText.alpha = 0f
            resultText.visibility = View.VISIBLE
            resultText.animate()
                .alpha(1f)
                .setDuration(300)
                .start()
        }

        rerollBtn.setOnClickListener {
            lifecycleScope.launch { animateAndShowDice() }
        }

        closeBtn.setOnClickListener { dialog.dismiss() }

        dialog.show()

        lifecycleScope.launch { animateAndShowDice() }
    }

    private fun getDiceDrawable(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun observeAddScoreDialogResult() {
        parentFragmentManager.setFragmentResultListener(
            AddScoreDialogFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val playerScores = bundle.getSerializable(AddScoreDialogFragment.RESULT_PLAYER_SCORES) as? HashMap<String, Int> ?: return@setFragmentResultListener
            val updatedSettings = bundle.getSerializable(AddScoreDialogFragment.RESULT_UPDATED_SETTINGS) as? HashMap<String, Any> ?: return@setFragmentResultListener

            val roundCount = viewModel.scores.value.maxOfOrNull { it.round } ?: 0

            val inputMap = viewModel.players.value.associate { player ->
                val score = playerScores[player.id] ?: 0
                player.id to ScoreInput(score)
            }

            viewModel.addScores(
                gameId = args.gameId,
                round = roundCount + 1,
                inputMap = inputMap
            )

            if (updatedSettings.isNotEmpty()) {
                viewModel.updateGameSettings(args.gameId, updatedSettings)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.collectLatest { isLoading ->
                        /*binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                        binding.contentLayout.visibility = if (isLoading) View.GONE else View.VISIBLE*/
                    }
                }

                launch {
                    viewModel.players.collectLatest { players ->
                        nameAdapter.submitList(players)
                    }
                }

                launch {
                    viewModel.totalScores.collectLatest { totals ->
                        totalAdapter.submitList(
                            viewModel.players.value.map { player ->
                                PlayerTotalScoreModel(
                                    playerId = player.id,
                                    totalScore = totals[player.id] ?: 0
                                )
                            }
                        )
                    }
                }

                launch {
                    viewModel.scores.collectLatest { scores ->
                        val roundCount = scores.maxOfOrNull { it.round } ?: 0
                        scoreRowAdapter.submitScores(roundCount, viewModel.players.value, scores)

                        val gameName = viewModel.game.value?.name ?: requireContext().resources.getString(R.string.game)
                        binding.tbHeader.title = "$gameName / ${roundCount + 1}. ${requireContext().resources.getString(R.string.hand)}"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}