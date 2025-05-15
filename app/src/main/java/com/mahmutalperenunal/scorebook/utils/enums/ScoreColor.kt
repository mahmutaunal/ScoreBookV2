package com.mahmutalperenunal.scorebook.utils.enums

enum class ScoreColor(private val colorName: String) {
    NONE("Yok"),
    RED("Kırmızı"),
    BLUE("Mavi"),
    YELLOW("Sarı"),
    BLACK("Siyah");

    override fun toString(): String = colorName
}