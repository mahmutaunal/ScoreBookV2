package com.mahmutalperenunal.scorebook.domain.calculator

import com.mahmutalperenunal.scorebook.utils.enums.SubGameType

object ScoreCalculatorFactory {

    fun getCalculator(gameType: SubGameType): ScoreCalculator {
        return when (gameType) {
            // OKEY TÜRLERİ
            SubGameType.OKEY_101,
            SubGameType.OKEY_RENKLI,
            SubGameType.OKEY_ELMALI,
            SubGameType.OKEY_NORMAL,
            SubGameType.OKEY_CIFT,
            SubGameType.OKEY_SANS,
            SubGameType.OKEY_CANLI -> OkeyScoreCalculator()

            // KÂĞIT OYUNLARI
            SubGameType.UNO -> UnoScoreCalculator()
            SubGameType.KING -> KingScoreCalculator()
            SubGameType.BATAK,
            SubGameType.BATAK_IHALELI,
            SubGameType.BATAK_KOZLU -> BatakScoreCalculator()
            SubGameType.PISTI,
            SubGameType.PISTI_DOUBLE -> PistiScoreCalculator()
            SubGameType.POKER,
            SubGameType.POKER_TEXAS,
            SubGameType.POKER_OMAHA -> PokerScoreCalculator()
            SubGameType.BLACKJACK -> BlackjackScoreCalculator()
            SubGameType.BRIDGE -> BridgeScoreCalculator()
            SubGameType.PIQUE -> PiqueScoreCalculator()

            SubGameType.CUSTOM -> throw UnsupportedOperationException("Custom game type does not support score calculation.")
        }
    }
}