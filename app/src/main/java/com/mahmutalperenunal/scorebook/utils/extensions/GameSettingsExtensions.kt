package com.mahmutalperenunal.scorebook.utils.extensions

import com.mahmutalperenunal.scorebook.data.local.typeconverters.GameSettings
import com.mahmutalperenunal.scorebook.utils.constants.GameSettingKeys

fun GameSettings.copyWith(updates: Map<String, Any>): GameSettings {
    return this.copy(
        allowNegative = updates[GameSettingKeys.ALLOW_NEGATIVE] as? Boolean ?: this.allowNegative,
        endScore = updates[GameSettingKeys.END_SCORE] as? Int ?: this.endScore,
        roundLimit = updates[GameSettingKeys.ROUND_LIMIT] as? Int ?: this.roundLimit,

        okeyColorMultiplier = updates[GameSettingKeys.OKEY_COLOR_MULTIPLIER] as? Int ?: this.okeyColorMultiplier,
        okeyPenaltyOnPass = updates[GameSettingKeys.OKEY_PENALTY_ON_PASS] as? Int ?: this.okeyPenaltyOnPass,
        okeyPairBonus = updates[GameSettingKeys.OKEY_PAIR_BONUS] as? Int ?: this.okeyPairBonus,
        okeyDoubleOpeningScore = updates[GameSettingKeys.OKEY_DOUBLE_OPENING_SCORE] as? Int ?: this.okeyDoubleOpeningScore,
        okeyLuckyCardEnabled = updates[GameSettingKeys.OKEY_LUCKY_CARD_ENABLED] as? Boolean ?: this.okeyLuckyCardEnabled,
        okeyLuckyCardMultiplier = updates[GameSettingKeys.OKEY_LUCKY_CARD_MULTIPLIER] as? Int ?: this.okeyLuckyCardMultiplier,
        okeyLiveTimerSeconds = updates[GameSettingKeys.OKEY_LIVE_TIMER_SECONDS] as? Int ?: this.okeyLiveTimerSeconds,
        okeyAutoSort = updates[GameSettingKeys.OKEY_AUTO_SORT] as? Boolean ?: this.okeyAutoSort,
        //okeyColorMatchRequired = updates[GameSettingKeys.OKEY_COLOR_MATCH_REQUIRED] as? Boolean ?: this.okeyColorMatchRequired,

        unoCardLimit = updates[GameSettingKeys.UNO_CARD_LIMIT] as? Int ?: this.unoCardLimit,
        unoSpecialCardPenalty = updates[GameSettingKeys.UNO_SPECIAL_CARD_PENALTY] as? Boolean ?: this.unoSpecialCardPenalty,
        unoReversePenalty = updates[GameSettingKeys.UNO_REVERSE_PENALTY] as? Boolean ?: this.unoReversePenalty,

        kingForceDifferentRules = updates[GameSettingKeys.KING_FORCE_DIFFERENT_RULES] as? Boolean ?: this.kingForceDifferentRules,
        kingPointMode = updates[GameSettingKeys.KING_POINT_MODE] as? String ?: this.kingPointMode,
        kingFinishScore = updates[GameSettingKeys.KING_FINISH_SCORE] as? Int ?: this.kingFinishScore,

        batakAuctionRequired = updates[GameSettingKeys.BATAK_AUCTION_REQUIRED] as? Boolean ?: this.batakAuctionRequired,
        batakMinAuctionScore = updates[GameSettingKeys.BATAK_MIN_AUCTION_SCORE] as? Int ?: this.batakMinAuctionScore,
        batakTrumpSuit = updates[GameSettingKeys.BATAK_TRUMP_SUIT] as? String ?: this.batakTrumpSuit,
        batakOverbidPenalty = updates[GameSettingKeys.BATAK_OVERBID_PENALTY] as? Boolean ?: this.batakOverbidPenalty,
        //batakPassAllowed = updates[GameSettingKeys.BATAK_PASS_ALLOWED] as? Boolean ?: this.batakPassAllowed,

        pistiBaseScore = updates[GameSettingKeys.PISTI_BASE_SCORE] as? Int ?: this.pistiBaseScore,
        pistiAceBonus = updates[GameSettingKeys.PISTI_ACE_BONUS] as? Int ?: this.pistiAceBonus,
        pistiDoubleBonus = updates[GameSettingKeys.PISTI_DOUBLE_BONUS] as? Boolean ?: this.pistiDoubleBonus,

        pokerStartChips = updates[GameSettingKeys.POKER_START_CHIPS] as? Int ?: this.pokerStartChips,
        pokerMinBet = updates[GameSettingKeys.POKER_MIN_BET] as? Int ?: this.pokerMinBet,
        pokerAllowAllIn = updates[GameSettingKeys.POKER_ALLOW_ALL_IN] as? Boolean ?: this.pokerAllowAllIn,

        blackjackOver21Penalty = updates[GameSettingKeys.BLACKJACK_OVER_21_PENALTY] as? Int ?: this.blackjackOver21Penalty,
        blackjackAceAsEleven = updates[GameSettingKeys.BLACKJACK_ACE_AS_ELEVEN] as? Boolean ?: this.blackjackAceAsEleven,

        bridgeUseIMP = updates[GameSettingKeys.BRIDGE_USE_IMP] as? Boolean ?: this.bridgeUseIMP,
        bridgeTimedMatch = updates[GameSettingKeys.BRIDGE_TIMED_MATCH] as? Boolean ?: this.bridgeTimedMatch,
        bridgeRoundCount = updates[GameSettingKeys.BRIDGE_ROUND_COUNT] as? Int ?: this.bridgeRoundCount,

        piqueSpecialCards = updates[GameSettingKeys.PIQUE_SPECIAL_CARDS] as? Boolean ?: this.piqueSpecialCards,
        piqueDrawPenalty = updates[GameSettingKeys.PIQUE_DRAW_PENALTY] as? Boolean ?: this.piqueDrawPenalty,
        piqueInitialCardCount = updates[GameSettingKeys.PIQUE_INITIAL_CARD_COUNT] as? Int ?: this.piqueInitialCardCount
    )
}

fun GameSettings.toMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()

    map[GameSettingKeys.ALLOW_NEGATIVE] = this.allowNegative
    this.endScore?.let { map[GameSettingKeys.END_SCORE] = it }
    this.roundLimit?.let { map[GameSettingKeys.ROUND_LIMIT] = it }

    this.okeyColorMultiplier?.let { map[GameSettingKeys.OKEY_COLOR_MULTIPLIER] = it }
    this.okeyPenaltyOnPass?.let { map[GameSettingKeys.OKEY_PENALTY_ON_PASS] = it }
    this.okeyPairBonus?.let { map[GameSettingKeys.OKEY_PAIR_BONUS] = it }
    this.okeyDoubleOpeningScore?.let { map[GameSettingKeys.OKEY_DOUBLE_OPENING_SCORE] = it }
    map[GameSettingKeys.OKEY_LUCKY_CARD_ENABLED] = this.okeyLuckyCardEnabled
    this.okeyLuckyCardMultiplier?.let { map[GameSettingKeys.OKEY_LUCKY_CARD_MULTIPLIER] = it }
    this.okeyLiveTimerSeconds?.let { map[GameSettingKeys.OKEY_LIVE_TIMER_SECONDS] = it }
    map[GameSettingKeys.OKEY_AUTO_SORT] = this.okeyAutoSort
    //map[GameSettingKeys.OKEY_COLOR_MATCH_REQUIRED] = this.okeyColorMatchRequired

    this.unoCardLimit?.let { map[GameSettingKeys.UNO_CARD_LIMIT] = it }
    map[GameSettingKeys.UNO_SPECIAL_CARD_PENALTY] = this.unoSpecialCardPenalty
    map[GameSettingKeys.UNO_REVERSE_PENALTY] = this.unoReversePenalty

    map[GameSettingKeys.KING_FORCE_DIFFERENT_RULES] = this.kingForceDifferentRules
    this.kingPointMode?.let { map[GameSettingKeys.KING_POINT_MODE] = it }
    this.kingFinishScore?.let { map[GameSettingKeys.KING_FINISH_SCORE] = it }

    map[GameSettingKeys.BATAK_AUCTION_REQUIRED] = this.batakAuctionRequired
    this.batakMinAuctionScore?.let { map[GameSettingKeys.BATAK_MIN_AUCTION_SCORE] = it }
    this.batakTrumpSuit?.let { map[GameSettingKeys.BATAK_TRUMP_SUIT] = it }
    map[GameSettingKeys.BATAK_OVERBID_PENALTY] = this.batakOverbidPenalty
    //map[GameSettingKeys.BATAK_PASS_ALLOWED] = this.batakPassAllowed

    this.pistiBaseScore?.let { map[GameSettingKeys.PISTI_BASE_SCORE] = it }
    this.pistiAceBonus?.let { map[GameSettingKeys.PISTI_ACE_BONUS] = it }
    map[GameSettingKeys.PISTI_DOUBLE_BONUS] = this.pistiDoubleBonus

    this.pokerStartChips?.let { map[GameSettingKeys.POKER_START_CHIPS] = it }
    this.pokerMinBet?.let { map[GameSettingKeys.POKER_MIN_BET] = it }
    map[GameSettingKeys.POKER_ALLOW_ALL_IN] = this.pokerAllowAllIn

    this.blackjackOver21Penalty?.let { map[GameSettingKeys.BLACKJACK_OVER_21_PENALTY] = it }
    map[GameSettingKeys.BLACKJACK_ACE_AS_ELEVEN] = this.blackjackAceAsEleven

    map[GameSettingKeys.BRIDGE_USE_IMP] = this.bridgeUseIMP
    map[GameSettingKeys.BRIDGE_TIMED_MATCH] = this.bridgeTimedMatch
    this.bridgeRoundCount?.let { map[GameSettingKeys.BRIDGE_ROUND_COUNT] = it }

    map[GameSettingKeys.PIQUE_SPECIAL_CARDS] = this.piqueSpecialCards
    map[GameSettingKeys.PIQUE_DRAW_PENALTY] = this.piqueDrawPenalty
    this.piqueInitialCardCount?.let { map[GameSettingKeys.PIQUE_INITIAL_CARD_COUNT] = it }

    return map
}