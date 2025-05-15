package com.mahmutalperenunal.scorebook.ui.setup

import android.content.res.Resources
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.data.local.entity.PlayerEntity
import com.mahmutalperenunal.scorebook.databinding.FragmentGameSetupBinding
import com.mahmutalperenunal.scorebook.utils.enums.SubGameType
import com.mahmutalperenunal.scorebook.utils.enums.MainGameType
import com.mahmutalperenunal.scorebook.utils.generator.GameSettingsGenerator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class GameSetupFragment : Fragment(R.layout.fragment_game_setup) {

    private val viewModel: GameSetupViewModel by viewModels()
    private var _binding: FragmentGameSetupBinding? = null
    private val binding get() = _binding!!

    private val playerInputs = mutableListOf<TextInputEditText>()
    private var selectedMainType: MainGameType? = null
    private var selectedSubGameType: SubGameType? = null
    private var subTypes: List<SubGameType> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMainGameTypeDropdown()
        setupInitialVisibility()
        binding.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnStartGame.setOnClickListener { onStartGameClicked() }
        observeGameCreated()
    }

    private fun setupInitialVisibility() {
        binding.tlGameTypeSub.visibility = View.GONE
        binding.tlPlayerCount.visibility = View.GONE
        binding.tlPlayerName.visibility = View.GONE
        binding.llGameSettings.visibility = View.GONE
    }

    private fun setupMainGameTypeDropdown() {
        val mainTypes = MainGameType.entries.map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, mainTypes)
        binding.actGameTypeMain.setAdapter(adapter)

        binding.actGameTypeMain.setOnItemClickListener { _, _, position, _ ->
            selectedMainType = MainGameType.entries[position]
            binding.tlGameTypeSub.visibility = View.VISIBLE
            setupSubGameTypeDropdown(selectedMainType!!)
        }
    }

    private fun setupSubGameTypeDropdown(mainType: MainGameType) {
        subTypes = SubGameType.entries.filter { it.mainType == mainType }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, subTypes.map { it.displayName })
        binding.actGameTypeSub.setAdapter(adapter)

        binding.actGameTypeSub.setOnItemClickListener { _, _, position, _ ->
            selectedSubGameType = subTypes[position]
            binding.tlPlayerCount.visibility = View.VISIBLE
            binding.tlPlayerName.visibility = View.VISIBLE
            binding.llGameSettings.visibility = View.VISIBLE
            setupPlayerCountDropdown()
            generatePlayerInputs(2)
            generateGameSettings(selectedSubGameType!!)
        }
    }

    private fun setupPlayerCountDropdown() {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, listOf("2", "3", "4"))
        binding.actPlayerCount.setAdapter(adapter)
        binding.actPlayerCount.setText("2", false)
        binding.actPlayerCount.setOnItemClickListener { _, _, position, _ ->
            generatePlayerInputs(position + 2)
        }
    }

    private fun generatePlayerInputs(count: Int) {
        val container = binding.tlPlayerName.parent as ViewGroup

        while (container.childCount > container.indexOfChild(binding.tlPlayerName) + 1) {
            container.removeViewAt(container.childCount - 1)
        }

        playerInputs.clear()

        for (i in 0 until count) {
            val editText = TextInputEditText(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                textSize = 16f
                id = View.generateViewId()
            }

            val textInputLayout = TextInputLayout(requireContext(), null, com.google.android.material.R.style.Widget_Material3_TextInputLayout_OutlinedBox).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    topMargin = 16.dp
                }
                hint = "Player ${i + 1}"
                boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
                endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                addView(editText)
            }

            playerInputs.add(editText)
            container.addView(textInputLayout)
        }

        binding.tlPlayerName.visibility = View.GONE
    }

    private fun generateGameSettings(subGameType: SubGameType) {
        val context = requireContext()
        val container = binding.llGameSettings
        container.removeAllViews()

        GameSettingsGenerator.generate(context, container, subGameType)
    }

    private fun onStartGameClicked() {
        val name = binding.etGameName.text.toString().ifBlank { requireContext().resources.getString(R.string.new_game) }
        val gameId = UUID.randomUUID().toString()

        val players = playerInputs.mapIndexed { i, et ->
            val playerId = UUID.randomUUID().toString()
            val playerName = et.text?.toString()?.ifBlank { "${requireContext().resources.getString(R.string.player)} ${i + 1}" }
            PlayerEntity(id = playerId, name = playerName ?: "${requireContext().resources.getString(R.string.player)} ${i + 1}", gameId = gameId, order = i)
        }

        viewModel.createGame(
            gameId = gameId,
            name = name,
            subGameType = selectedSubGameType!!,
            players = players,
            settings = GameSettingsGenerator.buildSettingsFromViews(binding.llGameSettings)
        )
    }

    private fun observeGameCreated() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.gameId.collectLatest { gameId ->
                gameId?.let {
                    findNavController().navigate(GameSetupFragmentDirections.actionGameSetupToScoreboard(it))
                }
            }
        }
    }

    private val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}