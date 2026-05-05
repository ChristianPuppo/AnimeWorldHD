package it.dogior.hadEnough

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

// ===== VixSrc Catalog =====
data class VixCatalogEntry(
    @JsonProperty("tmdb_id") val tmdbId: Int,
    @JsonProperty("imdb_id") val imdbId: String?
)

// ===== TMDB Models =====
@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbPageResponse(
    @JsonProperty("page") val page: Int,
    @JsonProperty("results") val results: List<TmdbItem>,
    @JsonProperty("total_pages") val totalPages: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbItem(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("overview") val overview: String?,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("backdrop_path") val backdropPath: String?,
    @JsonProperty("media_type") val mediaType: String?,
    @JsonProperty("release_date") val releaseDate: String?,
    @JsonProperty("first_air_date") val firstAirDate: String?,
    @JsonProperty("vote_average") val voteAverage: Double?,
    @JsonProperty("genre_ids") val genreIds: List<Int>?
) {
    fun getTitle() = title ?: name ?: "Unknown"
    fun getDate() = releaseDate ?: firstAirDate
    fun getType() = mediaType ?: "movie"
    fun getPosterUrl() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
    fun getBackdropUrl() = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbMovieDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String?,
    @JsonProperty("overview") val overview: String?,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("backdrop_path") val backdropPath: String?,
    @JsonProperty("release_date") val releaseDate: String?,
    @JsonProperty("vote_average") val voteAverage: Double?,
    @JsonProperty("runtime") val runtime: Int?,
    @JsonProperty("imdb_id") val imdbId: String?,
    @JsonProperty("genres") val genres: List<TmdbGenre>?,
    @JsonProperty("credits") val credits: TmdbCredits?,
    @JsonProperty("videos") val videos: TmdbVideos?
) {
    fun getPosterUrl() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
    fun getBackdropUrl() = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbTvDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String?,
    @JsonProperty("overview") val overview: String?,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("backdrop_path") val backdropPath: String?,
    @JsonProperty("first_air_date") val firstAirDate: String?,
    @JsonProperty("vote_average") val voteAverage: Double?,
    @JsonProperty("number_of_seasons") val numberOfSeasons: Int?,
    @JsonProperty("episode_run_time") val episodeRunTime: List<Int>?,
    @JsonProperty("genres") val genres: List<TmdbGenre>?,
    @JsonProperty("credits") val credits: TmdbCredits?,
    @JsonProperty("videos") val videos: TmdbVideos?,
    @JsonProperty("seasons") val seasons: List<TmdbSeason>?
) {
    fun getPosterUrl() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
    fun getBackdropUrl() = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbGenre(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCredits(
    @JsonProperty("cast") val cast: List<TmdbCast>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbCast(
    @JsonProperty("name") val name: String,
    @JsonProperty("character") val character: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbVideos(
    @JsonProperty("results") val results: List<TmdbVideo>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbVideo(
    @JsonProperty("key") val key: String,
    @JsonProperty("site") val site: String,
    @JsonProperty("type") val type: String
) {
    fun getYoutubeUrl() = if (site == "YouTube") "https://www.youtube.com/watch?v=$key" else null
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbSeason(
    @JsonProperty("season_number") val seasonNumber: Int,
    @JsonProperty("episode_count") val episodeCount: Int?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("poster_path") val posterPath: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbSeasonDetail(
    @JsonProperty("season_number") val seasonNumber: Int,
    @JsonProperty("episodes") val episodes: List<TmdbEpisode>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TmdbEpisode(
    @JsonProperty("episode_number") val episodeNumber: Int,
    @JsonProperty("name") val name: String?,
    @JsonProperty("overview") val overview: String?,
    @JsonProperty("still_path") val stillPath: String?,
    @JsonProperty("runtime") val runtime: Int?
) {
    fun getStillUrl() = stillPath?.let { "https://image.tmdb.org/t/p/w300$it" }
}

// ===== Load Data =====
data class VixLoadData(
    val tmdbId: Int,
    val type: String,
    val seasonNumber: Int? = null,
    val episodeNumber: Int? = null
)
