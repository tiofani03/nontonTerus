package id.tiooooo.nontonterus.movie.api.model.video

data class MovieVideo(
    val id: String,
    val key: String,
    val site: String,
    val name: String,
) {
    fun createThumbnailUrl(): String {
        return "https://img.youtube.com/vi/$key/hqdefault.jpg"
    }
}
