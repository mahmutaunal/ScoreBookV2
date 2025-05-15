package com.mahmutalperenunal.scorebook.data.local.typeconverters

import androidx.room.TypeConverters

@TypeConverters(GameSettingsConverter::class)
data class GameSettings(
    val allowNegative: Boolean = true,
    val endScore: Int? = null,
    val roundLimit: Int? = null,

    // Okey
    val okeyColorMultiplier: Int? = null,
    val okeyPenaltyOnPass: Int? = null,
    val okeyPairBonus: Int? = null,
    val okeyDoubleOpeningScore: Int? = null,
    val okeyLuckyCardEnabled: Boolean = false,
    val okeyLuckyCardMultiplier: Int? = null,
    val okeyLiveTimerSeconds: Int? = null,
    val okeyAutoSort: Boolean = false,

    // Uno
    val unoCardLimit: Int? = null,
    val unoSpecialCardPenalty: Boolean = false,
    val unoReversePenalty: Boolean = false,

    // King
    val kingForceDifferentRules: Boolean = false,
    val kingPointMode: String? = null,
    val kingFinishScore: Int? = null,

    // Batak
    val batakAuctionRequired: Boolean = false,
    val batakMinAuctionScore: Int? = null,
    val batakTrumpSuit: String? = null,
    val batakOverbidPenalty: Boolean = false,

    // Pişti
    val pistiBaseScore: Int? = null,
    val pistiAceBonus: Int? = null,
    val pistiDoubleBonus: Boolean = false,

    // Poker
    val pokerStartChips: Int? = null,
    val pokerMinBet: Int? = null,
    val pokerAllowAllIn: Boolean = true,

    // Blackjack
    val blackjackOver21Penalty: Int? = null,
    val blackjackAceAsEleven: Boolean = true,

    // Bridge
    val bridgeUseIMP: Boolean = false,
    val bridgeTimed: Boolean = false,
    val bridgeRoundCount: Int? = null,
    val bridgeTimedMatch: Boolean = false,

    // Pique
    val piqueSpecialCards: Boolean = false,
    val piqueDrawPenalty: Boolean = false,
    val piqueInitialCardCount: Int? = null
)