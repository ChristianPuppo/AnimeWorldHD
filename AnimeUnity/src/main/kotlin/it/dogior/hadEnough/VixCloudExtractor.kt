package it.dogior.hadEnough

import android.util.Log
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.ExtractorLinkType
import com.lagradost.cloudstream3.utils.newExtractorLink

class VixCloudExtractor : ExtractorApi() {
    override val mainUrl = "vixcloud.co"
    override val name = "VixCloud"
    override val requiresReferer = false
    private val TAG = "VixCloudExtractor"
    private val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36"

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        Log.d(TAG, "REFERER: $referer  URL: $url")
        val playlistUrl = getPlaylistLink(url)
        Log.d(TAG, "FINAL URL: $playlistUrl")

        callback.invoke(
            newExtractorLink(
                source = "VixCloud",
                name = "AnimeUnity",
                url = playlistUrl,
                type = ExtractorLinkType.M3U8
            ) {
                this.referer = "https://vixcloud.cc/"
                this.headers = mapOf(
                    "Referer" to "https://vixcloud.cc/",
                    "Origin" to "https://vixcloud.cc",
                    "User-Agent" to USER_AGENT,
                    "Accept" to "*/*"
                )
            }
        )
    }

    private suspend fun getPlaylistLink(url: String): String {
        Log.d(TAG, "Item url: $url")
        val embedUrl = if (url.startsWith("http")) url else "https://vixcloud.cc$url"
        val embedHeaders = mapOf(
            "User-Agent" to USER_AGENT,
            "Referer" to "https://www.animeunity.co/"
        )

        val embedHtml = app.get(embedUrl, headers = embedHeaders).text

        val token = Regex("""['"]token['"]\s*:\s*['"]([^'"]+)['"]""").find(embedHtml)?.groupValues?.get(1)
            ?: throw RuntimeException("VixCloud: token not found in embed HTML")
        val expires = Regex("""['"]expires['"]\s*:\s*['"]([^'"]+)['"]""").find(embedHtml)?.groupValues?.get(1)
            ?: throw RuntimeException("VixCloud: expires not found in embed HTML")
        val basePlaylistUrl = Regex("""url\s*:\s*['"](https?://[^'"]+)['"]""").find(embedHtml)?.groupValues?.get(1)
            ?: throw RuntimeException("VixCloud: base playlist URL not found in embed HTML")

        val canPlayFHD = embedHtml.contains("canPlayFHD = true") || embedUrl.contains("canPlayFHD=1")

        val separator = if (basePlaylistUrl.contains("?")) "&" else "?"
        var playlistUrl = "$basePlaylistUrl${separator}token=$token&expires=$expires"
        if (canPlayFHD && !playlistUrl.contains("h=")) {
            playlistUrl += "&h=1"
        }

        Log.d(TAG, "Master Playlist URL: $playlistUrl")
        return playlistUrl
    }
}
