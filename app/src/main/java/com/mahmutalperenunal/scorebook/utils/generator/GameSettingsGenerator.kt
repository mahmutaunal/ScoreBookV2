package com.mahmutalperenunal.scorebook.utils.generator

import android.content.Context
import android.text.InputType
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.materialswitch.MaterialSwitch
import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.utils.constants.GameSettingKeys
import com.mahmutalperenunal.scorebook.utils.enums.SubGameType

object GameSettingsGenerator {

    fun generate(
        context: Context,
        container: LinearLayout,
        subGameType: SubGameType
    ) {
        container.removeAllViews()

        fun addSwitch(key: String, label: String, default: Boolean = false) {
            val switch = MaterialSwitch(context).apply {
                text = label
                isChecked = default
                tag = key
            }
            container.addView(switch)
        }

        fun addNumberInput(key: String, label: String) {
            val layout = TextInputLayout(
                context,
                null,
                com.google.android.material.R.style.Widget_Material3_TextInputLayout_OutlinedBox
            ).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    topMargin = 16
                }
                hint = label
                boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
                endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            }

            val input = TextInputEditText(context).apply {
                inputType = InputType.TYPE_CLASS_NUMBER
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                tag = key
                textSize = 16f
                setPadding(32, 24, 32, 24)
            }

            layout.addView(input)
            container.addView(layout)
        }

        fun addDropdown(key: String, label: String, options: List<String>) {
            val labelView = TextView(context).apply {
                text = label
                textSize = 16f
                setPadding(0, 24, 0, 8)
            }

            val spinner = Spinner(context).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, options)
                tag = key
            }

            container.addView(labelView)
            container.addView(spinner)
        }

        when (subGameType) {
            SubGameType.OKEY_101 -> {
                addSwitch(GameSettingKeys.ALLOW_NEGATIVE, "Negatif skora izin verilsin mi?", true)
                addSwitch(GameSettingKeys.OKEY_AUTO_SORT, "Otomatik sıralama aktif olsun mu?")
                addNumberInput(GameSettingKeys.ROUND_LIMIT, "El sınırı (örn. 20)")
                addNumberInput(GameSettingKeys.OKEY_DOUBLE_OPENING_SCORE, "101 cezası puanı")
                addNumberInput(GameSettingKeys.OKEY_PENALTY_ON_PASS, "Pas geçme cezası")
            }

            SubGameType.OKEY_RENKLI -> {
                addNumberInput(GameSettingKeys.OKEY_COLOR_MULTIPLIER, "Renkli taş çarpanı")
                addNumberInput(GameSettingKeys.ROUND_LIMIT, "El limiti")
                addSwitch(GameSettingKeys.OKEY_COLOR_MATCH_REQUIRED, "Renk eşleşmesi zorunlu mu?")
            }

            SubGameType.OKEY_ELMALI -> {
                addNumberInput(GameSettingKeys.OKEY_PENALTY_ON_PASS, "Elma cezası puanı")
                addDropdown(GameSettingKeys.OKEY_COLOR_MULTIPLIER, "Renkli taş çarpanı", listOf("2", "3", "4"))
                addNumberInput(GameSettingKeys.ROUND_LIMIT, "El limiti")
            }

            SubGameType.OKEY_NORMAL -> {
                addNumberInput(GameSettingKeys.END_SCORE, "Toplam hedef puan (örn. 1010)")
                addSwitch(GameSettingKeys.ALLOW_NEGATIVE, "Negatif puanlara izin verilsin mi?", true)
            }

            SubGameType.OKEY_CIFT -> {
                addNumberInput(GameSettingKeys.OKEY_DOUBLE_OPENING_SCORE, "Çift açma puanı")
                addNumberInput(GameSettingKeys.OKEY_PAIR_BONUS, "Çift bitirme bonusu")
            }

            SubGameType.OKEY_SANS -> {
                addSwitch(GameSettingKeys.OKEY_LUCKY_CARD_ENABLED, "Her turda şans kartı verilsin mi?")
                addNumberInput(GameSettingKeys.OKEY_LUCKY_CARD_MULTIPLIER, "Şans kartı çarpanı")
            }

            SubGameType.OKEY_CANLI -> {
                addNumberInput(GameSettingKeys.OKEY_LIVE_TIMER_SECONDS, "Her elde maksimum süre (saniye)")
                addSwitch(GameSettingKeys.OKEY_LIVE_TRACKING, "Gerçek zamanlı takip aktif mi?")
            }

            SubGameType.UNO -> {
                addNumberInput(GameSettingKeys.UNO_CARD_LIMIT, "Kart sayısı limiti")
                addSwitch(GameSettingKeys.UNO_SPECIAL_CARD_PENALTY, "Özel kart cezası aktif mi?")
                addSwitch(GameSettingKeys.UNO_REVERSE_PENALTY, "Ters/Durdur kartları ceza versin mi?")
            }

            SubGameType.KING -> {
                addSwitch(GameSettingKeys.KING_FORCE_DIFFERENT_RULES, "Her el farklı kural zorunlu mu?")
                addDropdown(GameSettingKeys.KING_POINT_MODE, "Puanlama tipi", listOf("Standard", "Negative", "Ters"))
                addNumberInput(GameSettingKeys.KING_FINISH_SCORE, "Bitirme puanı")
            }

            SubGameType.BATAK -> {
                addSwitch(GameSettingKeys.BATAK_AUCTION_REQUIRED, "İhale zorunlu mu?")
                addDropdown(GameSettingKeys.BATAK_TRUMP_SUIT, "Koz rengi", listOf("Maça", "Sinek", "Karo", "Kupa"))
                addSwitch(GameSettingKeys.BATAK_PASS_ALLOWED, "Geçiş hakkı tanınsın mı?")
            }

            SubGameType.BATAK_IHALELI -> {
                addNumberInput(GameSettingKeys.BATAK_MIN_AUCTION_SCORE, "Minimum ihale puanı")
                addSwitch(GameSettingKeys.BATAK_OVERBID_PENALTY, "Aşırı teklif cezası aktif mi?")
            }

            SubGameType.BATAK_KOZLU -> {
                addDropdown(GameSettingKeys.BATAK_TRUMP_SUIT, "Sabit koz rengi", listOf("Maça", "Sinek", "Karo", "Kupa"))
                addNumberInput(GameSettingKeys.BATAK_MIN_AUCTION_SCORE, "Koz kazanmak için minimum puan")
            }

            SubGameType.PISTI -> {
                addNumberInput(GameSettingKeys.PISTI_BASE_SCORE, "Pişti puanı")
                addNumberInput(GameSettingKeys.PISTI_ACE_BONUS, "As ile pişti puanı")
            }

            SubGameType.PISTI_DOUBLE -> {
                addNumberInput(GameSettingKeys.PISTI_BASE_SCORE, "Pişti puanı")
                addNumberInput(GameSettingKeys.PISTI_ACE_BONUS, "As ile pişti puanı")
                addSwitch(GameSettingKeys.PISTI_DOUBLE_BONUS, "Çift pişti bonusu aktif mi?")
            }

            SubGameType.POKER, SubGameType.POKER_TEXAS, SubGameType.POKER_OMAHA -> {
                addNumberInput(GameSettingKeys.POKER_START_CHIPS, "Başlangıç jetonu")
                addNumberInput(GameSettingKeys.POKER_MIN_BET, "Minimum bahis")
                addSwitch(GameSettingKeys.POKER_ALLOW_ALL_IN, "All-in aktif mi?")
            }

            SubGameType.BLACKJACK -> {
                addNumberInput(GameSettingKeys.BLACKJACK_OVER_21_PENALTY, "21 geçme cezası")
                addSwitch(GameSettingKeys.BLACKJACK_ACE_AS_ELEVEN, "As 11 mi sayılmalı?", true)
            }

            SubGameType.BRIDGE -> {
                addSwitch(GameSettingKeys.BRIDGE_USE_IMP, "IMP puanlama kullanılsın mı?", true)
                addSwitch(GameSettingKeys.BRIDGE_TIMED_MATCH, "Zamana karşı oynansın mı?")
                addNumberInput(GameSettingKeys.BRIDGE_ROUND_COUNT, "Tur sayısı")
            }

            SubGameType.PIQUE -> {
                addSwitch(GameSettingKeys.PIQUE_SPECIAL_CARDS, "2, 5, 8 özel kart olarak oynansın mı?", true)
                addSwitch(GameSettingKeys.PIQUE_DRAW_PENALTY, "Çekme cezası aktif mi?")
                addNumberInput(GameSettingKeys.PIQUE_INITIAL_CARD_COUNT, "Başlangıç kart sayısı")
            }

            SubGameType.CUSTOM -> {}
        }
    }

    fun buildSettingsFromViews(container: ViewGroup): GameSettings {
        val map = mutableMapOf<String, Any>()

        for (i in 0 until container.childCount) {
            when (val view = container.getChildAt(i)) {
                is Switch -> {
                    (view.tag as? String)?.let { map[it] = view.isChecked }
                }
                is TextInputLayout -> {
                    val editText = view.editText
                    val tag = editText?.tag as? String
                    val value = editText?.text?.toString()?.toIntOrNull()
                    if (tag != null && value != null) map[tag] = value
                }
                is Spinner -> {
                    (view.tag as? String)?.let { key ->
                        (view.selectedItem as? String)?.let { selected ->
                            map[key] = selected
                        }
                    }
                }
            }
        }

        return GameSettings(
            allowNegative = map[GameSettingKeys.ALLOW_NEGATIVE] as? Boolean ?: true,
            endScore = map[GameSettingKeys.END_SCORE] as? Int,
            roundLimit = map[GameSettingKeys.ROUND_LIMIT] as? Int,

            okeyColorMultiplier = map[GameSettingKeys.OKEY_COLOR_MULTIPLIER] as? Int,
            okeyPenaltyOnPass = map[GameSettingKeys.OKEY_PENALTY_ON_PASS] as? Int,
            okeyPairBonus = map[GameSettingKeys.OKEY_PAIR_BONUS] as? Int,
            okeyDoubleOpeningScore = map[GameSettingKeys.OKEY_DOUBLE_OPENING_SCORE] as? Int,
            okeyLuckyCardEnabled = map[GameSettingKeys.OKEY_LUCKY_CARD_ENABLED] as? Boolean ?: false,
            okeyLuckyCardMultiplier = map[GameSettingKeys.OKEY_LUCKY_CARD_MULTIPLIER] as? Int,
            okeyLiveTimerSeconds = map[GameSettingKeys.OKEY_LIVE_TIMER_SECONDS] as? Int,
            okeyAutoSort = map[GameSettingKeys.OKEY_AUTO_SORT] as? Boolean ?: false,

            unoCardLimit = map[GameSettingKeys.UNO_CARD_LIMIT] as? Int,
            unoSpecialCardPenalty = map[GameSettingKeys.UNO_SPECIAL_CARD_PENALTY] as? Boolean ?: false,
            unoReversePenalty = map[GameSettingKeys.UNO_REVERSE_PENALTY] as? Boolean ?: false,

            kingForceDifferentRules = map[GameSettingKeys.KING_FORCE_DIFFERENT_RULES] as? Boolean ?: false,
            kingPointMode = map[GameSettingKeys.KING_POINT_MODE] as? String,
            kingFinishScore = map[GameSettingKeys.KING_FINISH_SCORE] as? Int,

            batakAuctionRequired = map[GameSettingKeys.BATAK_AUCTION_REQUIRED] as? Boolean ?: false,
            batakMinAuctionScore = map[GameSettingKeys.BATAK_MIN_AUCTION_SCORE] as? Int,
            batakTrumpSuit = map[GameSettingKeys.BATAK_TRUMP_SUIT] as? String,
            batakOverbidPenalty = map[GameSettingKeys.BATAK_OVERBID_PENALTY] as? Boolean ?: false,

            pistiBaseScore = map[GameSettingKeys.PISTI_BASE_SCORE] as? Int,
            pistiAceBonus = map[GameSettingKeys.PISTI_ACE_BONUS] as? Int,
            pistiDoubleBonus = map[GameSettingKeys.PISTI_DOUBLE_BONUS] as? Boolean ?: false,

            pokerStartChips = map[GameSettingKeys.POKER_START_CHIPS] as? Int,
            pokerMinBet = map[GameSettingKeys.POKER_MIN_BET] as? Int,
            pokerAllowAllIn = map[GameSettingKeys.POKER_ALLOW_ALL_IN] as? Boolean ?: true,

            blackjackOver21Penalty = map[GameSettingKeys.BLACKJACK_OVER_21_PENALTY] as? Int,
            blackjackAceAsEleven = map[GameSettingKeys.BLACKJACK_ACE_AS_ELEVEN] as? Boolean ?: true,

            bridgeUseIMP = map[GameSettingKeys.BRIDGE_USE_IMP] as? Boolean ?: false,
            bridgeTimedMatch = map[GameSettingKeys.BRIDGE_TIMED_MATCH] as? Boolean ?: false,
            bridgeRoundCount = map[GameSettingKeys.BRIDGE_ROUND_COUNT] as? Int,

            piqueSpecialCards = map[GameSettingKeys.PIQUE_SPECIAL_CARDS] as? Boolean ?: false,
            piqueDrawPenalty = map[GameSettingKeys.PIQUE_DRAW_PENALTY] as? Boolean ?: false,
            piqueInitialCardCount = map[GameSettingKeys.PIQUE_INITIAL_CARD_COUNT] as? Int
        )
    }
}