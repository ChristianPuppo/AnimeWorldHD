package it.dogior.hadEnough

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.AppUtils.parseJson
import com.lagradost.cloudstream3.utils.AppUtils.toJson

/**
 * Data class for ani.zip image metadata
 * Follows the same structure as Ultima's implementation
 */
data class ImageData(
    @JsonProperty("coverType") val coverType: String?,
    @JsonProperty("url") val url: String?
)

data class AnimeMetadata(
    @JsonProperty("images") val images: List<ImageData>?
)

data class AniListImages(
    val fanartBanner: String?,
    val fanartPoster: String?,
    val anilistBanner: String?,
    val anilistCover: String?
) {
    fun getBestBackground(): String? {
        // Priority: Fanart > AniList banner
        return fanartBanner ?: anilistBanner
    }

    fun getBestPoster(): String? {
        // Priority: Fanart > AniList cover
        return fanartPoster ?: anilistCover
    }
}

object AniListMetadataFetcher {
    private val anilistApiUrl = "https://graphql.anilist.co"
    private val aniZipUrl = "https://api.ani.zip/mappings"
    private val headerJSON = mapOf(
        "Accept" to "application/json",
        "Content-Type" to "application/json"
    )

    suspend fun fetchImagesById(anilistId: Int): AniListImages? {
        return try {
            // Fetch from ani.zip for Fanart images (like Ultima does)
            val aniZipData = fetchAniZipMetadata(anilistId)
            val fanartImage = aniZipData?.images?.firstOrNull { it.coverType == "Fanart" }?.url

            // Fetch from AniList API as fallback
            val anilistData = fetchAniListMetadata(anilistId)

            AniListImages(
                fanartBanner = fanartImage,
                fanartPoster = fanartImage,
                anilistBanner = anilistData?.bannerImage,
                anilistCover = anilistData?.getCoverImage()
            )
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchAniZipMetadata(anilistId: Int): AnimeMetadata? {
        return try {
            val response = app.get("$aniZipUrl?anilist_id=$anilistId")
            val mapper = ObjectMapper()
            mapper.readValue(response.text, AnimeMetadata::class.java)
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchAniListMetadata(anilistId: Int): AniListMedia? {
        return try {
            val query = """
                query (${'$'}id: Int) {
                    Media(id: ${'$'}id, type: ANIME) {
                        bannerImage
                        coverImage {
                            extraLarge
                            large
                            medium
                        }
                    }
                }
            """.trimIndent()

            val body = mapOf(
                "query" to query,
                "variables" to """{"id":$anilistId}"""
            )

            val response = app.post(anilistApiUrl, headers = headerJSON, data = body)
            parseJson<AniListResponse>(response.text).data?.media
        } catch (e: Exception) {
            null
        }
    }

    // Data classes per il parsing JSON AniList
    private data class AniListResponse(
        @JsonProperty("data") val data: AniListData?
    )

    private data class AniListData(
        @JsonProperty("Media") val media: AniListMedia?
    )

    private data class AniListMedia(
        @JsonProperty("bannerImage") val bannerImage: String?,
        @JsonProperty("coverImage") val coverImage: AniListCoverImage?
    ) {
        fun getCoverImage(): String? {
            return coverImage?.extraLarge ?: coverImage?.large ?: coverImage?.medium
        }
    }

    private data class AniListCoverImage(
        @JsonProperty("extraLarge") val extraLarge: String?,
        @JsonProperty("large") val large: String?,
        @JsonProperty("medium") val medium: String?
    )
} 