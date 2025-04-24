package id.tiooooo.nontonterus.movie.api.model.detail

import id.tiooooo.nontonterus.core.constant.Constant.BASE_IMAGE_500
import id.tiooooo.nontonterus.core.constant.Constant.BASE_IMAGE_ORIGINAL
import id.tiooooo.nontonterus.core.constant.Constant.DATE_FORMAT
import id.tiooooo.nontonterus.core.constant.Constant.LONG_DATE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MovieDetail(
    val backdropPath: String,
    val genres: List<String>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
) {
    fun createTitleWithYear(): String {
        if (releaseDate.isEmpty()) {
            return title
        } else {
            releaseDate.split("-").let {
                return "$title (${it.first()})"
            }
        }
    }

    fun createBackdropPath(): String =
        if (backdropPath.isEmpty()) {
            posterPath.createImageUrl()
        } else {
            backdropPath.createImageUrl()
        }
}

fun String.toDate(format: String): Date {
    return try {
        val formatter = SimpleDateFormat(format, getIndonesianLocale())
        formatter.parse(this) ?: Date()
    } catch (e: Exception) {
        Date()
    }
}

fun getIndonesianLocale(): Locale {
    return Locale("en", "EN")
}

fun Date.toDateString(format: String): String {
    val formatter = SimpleDateFormat(format, getIndonesianLocale())
    return formatter.format(this) ?: ""
}

fun String.createImageUrl(isOriginal: Boolean? = true): String =
    if (isOriginal == true) BASE_IMAGE_ORIGINAL + this
    else BASE_IMAGE_500 + this
