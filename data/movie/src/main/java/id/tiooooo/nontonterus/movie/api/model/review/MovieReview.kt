package id.tiooooo.nontonterus.movie.api.model.review

import id.tiooooo.nontonterus.movie.api.model.detail.toDate
import id.tiooooo.nontonterus.movie.api.model.detail.toDateString

data class MovieReview(
    val author: String,
    val authorAva: String,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String,
    val rating: String,
){
    fun showCreatedAt(): String {
        return createdAt
            .toDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .toDateString("dd MMM yyyy")
    }
}
