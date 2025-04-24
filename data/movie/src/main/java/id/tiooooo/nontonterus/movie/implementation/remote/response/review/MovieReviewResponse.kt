package id.tiooooo.nontonterus.movie.implementation.remote.response.review

import com.google.gson.annotations.SerializedName
import id.tiooooo.nontonterus.movie.api.model.review.MovieReview

data class MovieReviewResponse(
    @SerializedName("author") val author: String?,
    @SerializedName("author_details") val authorDetails: AuthorDetailsResponse?,
    @SerializedName("content") val content: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("url") val url: String?,
)

data class AuthorDetailsResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: Int?,
)

fun MovieReviewResponse?.toMovieReview(): MovieReview =
    MovieReview(
        author = this?.author.orEmpty(),
        authorAva = this?.authorDetails?.avatarPath.toAvatarUrl().orEmpty(),
        content = this?.content.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        id = this?.id.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        url = this?.url.orEmpty(),
        rating = (this?.authorDetails?.rating ?: 0).toString()
    )

fun String?.toAvatarUrl(): String? {
    if (this.isNullOrBlank()) return null

    return when {
        this.startsWith("/https") -> this.removePrefix("/")
        this.startsWith("https") -> this
        else -> "https://image.tmdb.org/t/p/w185$this"
    }
}

