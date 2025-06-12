package com.mahmutalperenunal.scorebook.ui.addscore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.databinding.FragmentAddScoreDialogBinding
import com.mahmutalperenunal.scorebook.domain.model.PlayerModel
import com.mahmutalperenunal.scorebook.utils.constants.GameSettingKeys

class AddScoreDialogFragment : DialogFragment() {

    private var _binding: FragmentAddScoreDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var players: List<PlayerModel>
    private lateinit var gameSettings: Map<String, Any>

    private val playerInputs = mutableMapOf<String, TextInputEditText>()
    private val settingInputs = mutableMapOf<String, View>()

    private val settingNameMap = mapOf(
        GameSettingKeys.ALLOW_NEGATIVE to "Negatif Skor İzni",
        GameSettingKeys.END_SCORE to "Oyun Bitiş Skoru",
        GameSettingKeys.ROUND_LIMIT to "El Limiti",
        GameSettingKeys.OKEY_PENALTY_ON_PASS to "Pas Geçme Cezası",
        GameSettingKeys.OKEY_COLOR_MULTIPLIER to "Renkli Taş Çarpanı",
        GameSettingKeys.BATAK_TRUMP_SUIT to "Koz Rengi",
        GameSettingKeys.KING_POINT_MODE to "Puanlama Türü",
        GameSettingKeys.UNO_CARD_LIMIT to "Kart Sayısı Limiti",
        GameSettingKeys.PISTI_BASE_SCORE to "Pişti Puanı",
        GameSettingKeys.POKER_START_CHIPS to "Başlangıç Fişi",
        GameSettingKeys.BLACKJACK_OVER_21_PENALTY to "21'i Aşma Cezası",
        GameSettingKeys.BRIDGE_ROUND_COUNT to "Tur Sayısı",
        GameSettingKeys.PIQUE_INITIAL_CARD_COUNT to "Başlangıç Kart Sayısı"
    )

    private val dropdownOptionsMap = mapOf(
        GameSettingKeys.BATAK_TRUMP_SUIT to listOf("Maça", "Sinek", "Karo", "Kupa"),
        GameSettingKeys.KING_POINT_MODE to listOf("Standard", "Negative", "Ters")
    )

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddScoreDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isCancelable = true

        arguments?.let { args ->
            players = args.getParcelableArrayList(ARG_PLAYERS) ?: emptyList()
            @Suppress("UNCHECKED_CAST")
            gameSettings = args.getSerializable(ARG_GAME_SETTINGS) as? Map<String, Any> ?: emptyMap()
        }

        Log.d("DialogPlayers", players.joinToString { "${it.name}: ${it.id}" })

        setupPlayerInputs()
        setupGameSettings()
        setupClicks()
    }

    private fun setupPlayerInputs() {
        players.forEach { player ->
            val inputLayout = TextInputLayout(
                requireContext(),
                null,
                com.google.android.material.R.style.Widget_Material3_TextInputLayout_OutlinedBox
            ).apply {
                hint = player.name
                boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
                endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            }

            val editText = TextInputEditText(requireContext()).apply {
                inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }

            inputLayout.addView(editText)
            binding.llPlayerScoreInputs.addView(inputLayout)
            playerInputs[player.id] = editText
        }
    }

    private fun setupGameSettings() {
        val gameSettings = arguments?.getSerializable(ARG_GAME_SETTINGS) as? HashMap<String, Any> ?: return
        val settingsLayout = binding.llGameSettings

        when (gameSettings["gameType"]) {
            "BATAK" -> {
                settingsLayout.visibility = View.VISIBLE
                addBatakSettings(gameSettings)
            }
            "BRIDGE" -> {
                settingsLayout.visibility = View.VISIBLE
                addBridgeSettings(gameSettings)
            }
            "PIQUE" -> {
                settingsLayout.visibility = View.VISIBLE
                addPiqueSettings(gameSettings)
            }
            "UNO" -> {
                settingsLayout.visibility = View.VISIBLE
                addUnoSettings(gameSettings)
            }
            else -> settingsLayout.visibility = View.GONE
        }
    }

    private fun addBatakSettings(settings: HashMap<String, Any>) {
        val context = requireContext()
        val layout = binding.llGameSettings

        // Auction Required
        addSwitchSetting(
            layout,
            "Auction Required",
            settings["batakAuctionRequired"] as? Boolean ?: false
        ) { isChecked ->
            settings["batakAuctionRequired"] = isChecked
        }

        // Min Auction Score
        addNumberInputSetting(
            layout,
            "Min Auction Score",
            settings["batakMinAuctionScore"] as? Int ?: 0
        ) { value ->
            settings["batakMinAuctionScore"] = value
        }

        // Overbid Penalty
        addSwitchSetting(
            layout,
            "Overbid Penalty",
            settings["batakOverbidPenalty"] as? Boolean ?: false
        ) { isChecked ->
            settings["batakOverbidPenalty"] = isChecked
        }
    }

    private fun addBridgeSettings(settings: HashMap<String, Any>) {
        val context = requireContext()
        val layout = binding.llGameSettings

        // Round Count
        addNumberInputSetting(
            layout,
            "Round Count",
            settings["bridgeRoundCount"] as? Int ?: 1
        ) { value ->
            settings["bridgeRoundCount"] = value
        }

        // Timed Match
        addSwitchSetting(
            layout,
            "Timed Match",
            settings["bridgeTimedMatch"] as? Boolean ?: false
        ) { isChecked ->
            settings["bridgeTimedMatch"] = isChecked
        }

        // Use IMP
        addSwitchSetting(
            layout,
            "Use IMP",
            settings["bridgeUseIMP"] as? Boolean ?: false
        ) { isChecked ->
            settings["bridgeUseIMP"] = isChecked
        }
    }

    private fun addPiqueSettings(settings: HashMap<String, Any>) {
        val context = requireContext()
        val layout = binding.llGameSettings

        // Special Cards
        addSwitchSetting(
            layout,
            "Special Cards",
            settings["piqueSpecialCards"] as? Boolean ?: false
        ) { isChecked ->
            settings["piqueSpecialCards"] = isChecked
        }

        // Draw Penalty
        addSwitchSetting(
            layout,
            "Draw Penalty",
            settings["piqueDrawPenalty"] as? Boolean ?: false
        ) { isChecked ->
            settings["piqueDrawPenalty"] = isChecked
        }
    }

    private fun addUnoSettings(settings: HashMap<String, Any>) {
        val context = requireContext()
        val layout = binding.llGameSettings

        // Special Card Penalty
        addSwitchSetting(
            layout,
            "Special Card Penalty",
            settings["unoSpecialCardPenalty"] as? Boolean ?: false
        ) { isChecked ->
            settings["unoSpecialCardPenalty"] = isChecked
        }

        // Reverse Penalty
        addSwitchSetting(
            layout,
            "Reverse Penalty",
            settings["unoReversePenalty"] as? Boolean ?: false
        ) { isChecked ->
            settings["unoReversePenalty"] = isChecked
        }
    }

    private fun addSwitchSetting(
        parent: ViewGroup,
        title: String,
        initialValue: Boolean,
        onValueChanged: (Boolean) -> Unit
    ) {
        val switch = MaterialSwitch(requireContext()).apply {
            text = title
            isChecked = initialValue
            setOnCheckedChangeListener { _, isChecked ->
                onValueChanged(isChecked)
            }
        }
        parent.addView(switch)
    }

    private fun addNumberInputSetting(
        parent: ViewGroup,
        title: String,
        initialValue: Int,
        onValueChanged: (Int) -> Unit
    ) {
        val inputLayout = TextInputLayout(requireContext()).apply {
            hint = title
        }

        val editText = TextInputEditText(requireContext()).apply {
            setText(initialValue.toString())
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val value = text.toString().toIntOrNull() ?: initialValue
                    onValueChanged(value)
                }
            }
        }

        inputLayout.addView(editText)
        parent.addView(inputLayout)
    }

    private fun setupClicks() {
        binding.btnCancel.setOnClickListener { dismiss() }

        binding.btnSave.setOnClickListener {
            val playerScores = playerInputs.mapValues { entry ->
                entry.value.text.toString().toIntOrNull() ?: 0
            }

            val updatedSettings = settingInputs.mapValues { entry ->
                when (val view = entry.value) {
                    is Switch -> view.isChecked
                    is TextInputEditText -> view.text.toString().toIntOrNull() ?: 0
                    is Spinner -> view.selectedItem?.toString() ?: ""
                    else -> ""
                }
            }

            setFragmentResult(REQUEST_KEY, bundleOf(
                RESULT_PLAYER_SCORES to HashMap(playerScores),
                RESULT_UPDATED_SETTINGS to HashMap(updatedSettings)
            ))

            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_KEY = "AddScoreDialogRequest"
        const val RESULT_PLAYER_SCORES = "resultPlayerScores"
        const val RESULT_UPDATED_SETTINGS = "resultUpdatedSettings"
        private const val ARG_PLAYERS = "players"
        private const val ARG_GAME_SETTINGS = "gameSettings"

        fun newInstance(
            players: ArrayList<PlayerModel>,
            gameSettings: Map<String, Any>
        ): AddScoreDialogFragment {
            return AddScoreDialogFragment().apply {
                arguments = bundleOf(
                    ARG_PLAYERS to players,
                    ARG_GAME_SETTINGS to HashMap(gameSettings)
                )
            }
        }
    }
}