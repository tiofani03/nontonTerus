package id.tiooooo.nontonterus.core.utils

enum class AppLanguage(val code: String, val label: String) {
    ENGLISH("en", "English"),
    INDONESIA("id", "Bahasa");

    companion object {
        fun fromValue(value: String): AppLanguage {
            return entries.find { it.code == value } ?: ENGLISH
        }
    }
}