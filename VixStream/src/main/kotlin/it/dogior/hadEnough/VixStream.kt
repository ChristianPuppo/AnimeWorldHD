package it.dogior.hadEnough

import com.lagradost.api.Log
import com.lagradost.cloudstream3.Episode
import com.lagradost.cloudstream3.HomePageList
import com.lagradost.cloudstream3.HomePageResponse
import com.lagradost.cloudstream3.LoadResponse
import com.lagradost.cloudstream3.Score
import com.lagradost.cloudstream3.LoadResponse.Companion.addActors
import com.lagradost.cloudstream3.LoadResponse.Companion.addImdbId
import com.lagradost.cloudstream3.LoadResponse.Companion.addScore
import com.lagradost.cloudstream3.LoadResponse.Companion.addTMDbId
import com.lagradost.cloudstream3.LoadResponse.Companion.addTrailer
import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.MainPageRequest
import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.mainPageOf
import com.lagradost.cloudstream3.newEpisode
import com.lagradost.cloudstream3.newHomePageResponse
import com.lagradost.cloudstream3.newMovieLoadResponse
import com.lagradost.cloudstream3.newMovieSearchResponse
import com.lagradost.cloudstream3.newTvSeriesLoadResponse
import com.lagradost.cloudstream3.newTvSeriesSearchResponse
import com.lagradost.cloudstream3.utils.AppUtils.parseJson
import com.lagradost.cloudstream3.utils.AppUtils.toJson
import com.lagradost.cloudstream3.utils.ExtractorLink

class VixStream : MainAPI() {
    companion object {
        const val TMDB_API_KEY = "e5c77d25389b58780229a24bff297109"
        const val TMDB_BASE = "https://api.themoviedb.org/3"
        const val TMDB_IMG = "https://image.tmdb.org/t/p/w500"
        const val VIXSRC_BASE = "https://vixsrc.to"
        const val TAG = "VixStream"

        private var availableMovies: Set<Int>? = null
        private var availableShows: Set<Int>? = null

        suspend fun loadCatalog() {
            if (availableMovies != null && availableShows != null) return
            try {
                val moviesJson = app.get("$VIXSRC_BASE/api/list/movie/?lang=it", timeout = 10).text
                val movies = parseJson<List<VixCatalogEntry>>(moviesJson)
                availableMovies = movies.map { it.tmdbId }.toHashSet()
                Log.d(TAG, "Loaded ${availableMovies!!.size} available movies")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load movie catalog: ${e.message}")
                availableMovies = emptySet()
            }
            try {
                val showsJson = app.get("$VIXSRC_BASE/api/list/tv/?lang=it", timeout = 10).text
                val shows = parseJson<List<VixCatalogEntry>>(showsJson)
                availableShows = shows.map { it.tmdbId }.toHashSet()
                Log.d(TAG, "Loaded ${availableShows!!.size} available shows")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load TV catalog: ${e.message}")
                availableShows = emptySet()
            }
        }

        fun isAvailable(tmdbId: Int, type: String): Boolean {
            return when (type) {
                "movie" -> availableMovies?.contains(tmdbId) ?: true
                "tv" -> availableShows?.contains(tmdbId) ?: true
                else -> (availableMovies?.contains(tmdbId) ?: false) || (availableShows?.contains(tmdbId) ?: false)
            }
        }
    }

    override var mainUrl = VIXSRC_BASE
    override var name = "VixStream"
    override var supportedTypes = setOf(TvType.Movie, TvType.TvSeries)
    override var lang = "it"
    override val hasMainPage = true

    override val mainPage = mainPageOf(
        "trending/movie/week" to "Film Trending",
        "trending/tv/week" to "Serie TV Trending",
        "movie/popular" to "Film Popolari",
        "tv/popular" to "Serie TV Popolari",
        "movie/top_rated" to "Film Più Votati",
        "tv/top_rated" to "Serie TV Più Votate",
        "movie/now_playing" to "Al Cinema",
        "tv/on_the_air" to "In Onda Ora",
    )

    private fun tmdbUrl(path: String, extraParams: String = ""): String {
        return "$TMDB_BASE/$path?api_key=$TMDB_API_KEY&language=it-IT$extraParams"
    }

    private fun tmdbItemToSearchResponse(item: TmdbItem): SearchResponse? {
        val type = item.getType()
        val tmdbId = item.id

        if (!isAvailable(tmdbId, type)) return null

        val title = item.getTitle()
        val posterUrl = item.getPosterUrl()
        // Store type + tmdbId in the URL for later retrieval
        val dataUrl = "$VIXSRC_BASE/$type/$tmdbId"

        return if (type == "tv") {
            newTvSeriesSearchResponse(title, dataUrl) {
                this.posterUrl = posterUrl
            }
        } else {
            newMovieSearchResponse(title, dataUrl) {
                this.posterUrl = posterUrl
            }
        }
    }

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        loadCatalog()

        val pageNum = if (page <= 0) 1 else page
        val url = tmdbUrl(request.data, "&page=$pageNum")
        val response = app.get(url).text
        val tmdbPage = parseJson<TmdbPageResponse>(response)

        val items = tmdbPage.results.mapNotNull { tmdbItemToSearchResponse(it) }

        return newHomePageResponse(
            HomePageList(
                name = request.name,
                list = items,
                isHorizontalImages = false
            ), hasNext = pageNum < tmdbPage.totalPages
        )
    }

    override suspend fun search(query: String): List<SearchResponse> {
        loadCatalog()

        val url = tmdbUrl("search/multi", "&query=${query}&include_adult=false")
        val response = app.get(url).text
        val tmdbPage = parseJson<TmdbPageResponse>(response)

        return tmdbPage.results
            .filter { it.getType() == "movie" || it.getType() == "tv" }
            .mapNotNull { tmdbItemToSearchResponse(it) }
    }

    override suspend fun load(url: String): LoadResponse {
        loadCatalog()

        // Parse type and tmdbId from URL: https://vixsrc.to/movie/12345 or https://vixsrc.to/tv/12345
        val parts = url.removePrefix("$VIXSRC_BASE/").split("/")
        val type = parts.getOrNull(0) ?: "movie"
        val tmdbId = parts.getOrNull(1)?.toIntOrNull() ?: throw Exception("Invalid TMDB ID")

        if (type == "tv") {
            return loadTvShow(tmdbId, url)
        } else {
            return loadMovie(tmdbId, url)
        }
    }

    private suspend fun loadMovie(tmdbId: Int, url: String): LoadResponse {
        val detailUrl = tmdbUrl("movie/$tmdbId", "&append_to_response=credits,videos")
        val detail = parseJson<TmdbMovieDetail>(app.get(detailUrl).text)

        val trailers = detail.videos?.results
            ?.filter { it.type == "Trailer" }
            ?.mapNotNull { it.getYoutubeUrl() }

        val loadData = VixLoadData(tmdbId, "movie")

        return newMovieLoadResponse(
            detail.title ?: "Unknown",
            url,
            TvType.Movie,
            dataUrl = loadData.toJson()
        ) {
            this.posterUrl = detail.getPosterUrl()
            this.backgroundPosterUrl = detail.getBackdropUrl()
            this.plot = detail.overview
            this.tags = detail.genres?.map { it.name }
            this.year = detail.releaseDate?.substringBefore('-')?.toIntOrNull()
            this.duration = detail.runtime
            detail.imdbId?.let { this.addImdbId(it) }
            this.addTMDbId(tmdbId.toString())
            detail.credits?.cast?.take(10)?.let { cast ->
                this.addActors(cast.map { it.name })
            }
            detail.voteAverage?.let {
                this.addScore(Score((it * 10).toInt()))
            }
            if (!trailers.isNullOrEmpty()) {
                addTrailer(trailers)
            }
        }
    }

    private suspend fun loadTvShow(tmdbId: Int, url: String): LoadResponse {
        val detailUrl = tmdbUrl("tv/$tmdbId", "&append_to_response=credits,videos")
        val detail = parseJson<TmdbTvDetail>(app.get(detailUrl).text)

        val trailers = detail.videos?.results
            ?.filter { it.type == "Trailer" }
            ?.mapNotNull { it.getYoutubeUrl() }

        val episodes = mutableListOf<Episode>()

        detail.seasons?.filter { it.seasonNumber > 0 }?.forEach { season ->
            try {
                val seasonUrl = tmdbUrl("tv/$tmdbId/season/${season.seasonNumber}")
                val seasonDetail = parseJson<TmdbSeasonDetail>(app.get(seasonUrl).text)

                seasonDetail.episodes?.forEach { ep ->
                    val loadData = VixLoadData(
                        tmdbId = tmdbId,
                        type = "tv",
                        seasonNumber = season.seasonNumber,
                        episodeNumber = ep.episodeNumber
                    )
                    episodes.add(
                        newEpisode(loadData.toJson()) {
                            this.name = ep.name ?: "Episodio ${ep.episodeNumber}"
                            this.posterUrl = ep.getStillUrl()
                            this.description = ep.overview
                            this.episode = ep.episodeNumber
                            this.season = season.seasonNumber
                            this.runTime = ep.runtime
                        }
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load season ${season.seasonNumber}: ${e.message}")
            }
        }

        return newTvSeriesLoadResponse(
            detail.name ?: "Unknown",
            url,
            TvType.TvSeries,
            episodes
        ) {
            this.posterUrl = detail.getPosterUrl()
            this.backgroundPosterUrl = detail.getBackdropUrl()
            this.plot = detail.overview
            this.tags = detail.genres?.map { it.name }
            this.year = detail.firstAirDate?.substringBefore('-')?.toIntOrNull()
            this.addTMDbId(tmdbId.toString())
            detail.credits?.cast?.take(10)?.let { cast ->
                this.addActors(cast.map { it.name })
            }
            detail.voteAverage?.let {
                this.addScore(Score((it * 10).toInt()))
            }
            if (!trailers.isNullOrEmpty()) {
                addTrailer(trailers)
            }
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        Log.d(TAG, "Load Data: $data")
        if (data.isEmpty()) return false
        val loadData = parseJson<VixLoadData>(data)

        val vixsrcUrl = if (loadData.type == "movie") {
            "$VIXSRC_BASE/movie/${loadData.tmdbId}"
        } else {
            "$VIXSRC_BASE/tv/${loadData.tmdbId}/${loadData.seasonNumber}/${loadData.episodeNumber}"
        }

        Log.d(TAG, "VixSrc URL: $vixsrcUrl")

        VixStreamExtractor().getUrl(
            url = vixsrcUrl,
            referer = "$VIXSRC_BASE/",
            subtitleCallback = subtitleCallback,
            callback = callback
        )

        return true
    }
}
