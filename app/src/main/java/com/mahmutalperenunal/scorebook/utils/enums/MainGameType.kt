package com.mahmutalperenunal.scorebook.utils.enums

enum class MainGameType(val displayName: String) {
    OKEY("Okey Oyunları"),
    KAGIT("Kâğıt Oyunları"),
    OZEL("Özel Oyun");

    override fun toString(): String = displayName
}