package id.tiooooo.nontonterus.movie.implementation.remote.response.genre

import com.google.gson.annotations.SerializedName
import id.tiooooo.nontonterus.movie.api.model.list.Genre
import id.tiooooo.nontonterus.movie.api.model.list.GenreList

data class GenreResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
)

data class GenreListResponse(
    @SerializedName("genres") val genres: List<GenreResponse>?
)

fun GenreListResponse.toGenreList(): GenreList {
    val list = this.genres?.map { it.toGenre() } ?: listOf()
    return GenreList(
        genreList = list
    )
}


fun GenreResponse.toGenre() = Genre(
    id = this.id ?: 0,
    name = this.name.orEmpty()
)

