package it.dogior.hadEnough

import android.util.Log
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.network.CloudflareKiller
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.ExtractorLinkType
import com.lagradost.cloudstream3.utils.newExtractorLink
import org.json.JSONObject

class VixStreamExtractor : ExtractorApi() {
    override val mainUrl = "vixsrc.to"
    override val name = "VixStream"
    override val requiresReferer = false
    val TAG = "VixStreamExtractor"
    private var referer: String? = null

    private val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36"

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        this.referer = referer
        Log.d(TAG, "REFERER: $referer  URL: $url")
        val playlistUrl = getPlaylistLink(url)
        Log.w(TAG, "FINAL URL: $playlistUrl")

        callback.invoke(
            newExtractorLink(
                source = "VixSrc",
                name = "VixStream",
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
        val apiUrl = url.replace("/movie/", "/api/movie/").replace("/tv/", "/api/tv/")

        val apiHeaders = mapOf(
            "Accept" to "application/json",
            "Referer" to "https://vixsrc.to/",
            "Origin" to "https://vixsrc.to",
            "User-Agent" to USER_AGENT
        )

        val response = app.get(apiUrl, headers = apiHeaders, interceptor = CloudflareKiller()).text
        Log.d(TAG, "API Response: $response")
        val json = JSONObject(response)

        val embedPath = json.getString("src")
        val embedUrl = if (embedPath.startsWith("http")) embedPath else "https://vixcloud.cc$embedPath"
        Log.d(TAG, "Embed URL: $embedUrl")

        val embedHeaders = mapOf(
            "User-Agent" to USER_AGENT,
            "Referer" to "https://vixsrc.to/"
        )

        val embedHtml = app.get(embedUrl, headers = embedHeaders, interceptor = CloudflareKiller()).text

        // Extract masterPlaylist parameters: token, expires, base playlist url
        val token = Regex("""['"]token['"]\s*:\s*['"]([^'"]+)['"]""").find(embedHtml)?.groupValues?.get(1)
            ?: throw RuntimeException("VixStream: token not found in embed HTML")
        val expires = Regex("""['"]expires['"]\s*:\s*['"]([^'"]+)['"]""").find(embedHtml)?.groupValues?.get(1)
            ?: throw RuntimeException("VixStream: expires not found in embed HTML")
        val basePlaylistUrl = Regex("""url\s*:\s*['"](https?://[^'"]+)['"]""").find(embedHtml)?.groupValues?.get(1)
            ?: throw RuntimeException("VixStream: base playlist URL not found in embed HTML")

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
