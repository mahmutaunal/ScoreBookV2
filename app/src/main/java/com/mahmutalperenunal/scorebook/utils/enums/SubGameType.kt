package com.mahmutalperenunal.scorebook.utils.enums

enum class SubGameType(val displayName: String, val mainType: MainGameType) {
    // OKEY TÜRLERİ
    OKEY_101("101 Okey", MainGameType.OKEY),
    OKEY_RENKLI("Renkli 101", MainGameType.OKEY),
    OKEY_ELMALI("Elmalı", MainGameType.OKEY),
    OKEY_NORMAL("Düz Okey", MainGameType.OKEY),
    OKEY_CANLI("Canlı Okey", MainGameType.OKEY),
    OKEY_CIFT("Çift Okey", MainGameType.OKEY),
    OKEY_SANS("Şans Okeyi", MainGameType.OKEY),

    // KÂĞIT OYUNLARI
    UNO("UNO", MainGameType.KAGIT),
    KING("King", MainGameType.KAGIT),
    BATAK("Batak", MainGameType.KAGIT),
    BATAK_IHALELI("Batak (İhaleli)", MainGameType.KAGIT),
    BATAK_KOZLU("Batak (Kozlu)", MainGameType.KAGIT),
    PISTI("Pişti", MainGameType.KAGIT),
    PISTI_DOUBLE("Pişti (Çiftli)", MainGameType.KAGIT),
    BRIDGE("Briç", MainGameType.KAGIT),
    POKER("Poker", MainGameType.KAGIT),
    POKER_TEXAS("Texas Hold'em", MainGameType.KAGIT),
    POKER_OMAHA("Omaha Poker", MainGameType.KAGIT),
    PIQUE("Pique", MainGameType.KAGIT),
    BLACKJACK("Blackjack", MainGameType.KAGIT),

    // ÖZEL
    CUSTOM("Özel Oyun", MainGameType.OZEL);

    override fun toString(): String = displayName
}