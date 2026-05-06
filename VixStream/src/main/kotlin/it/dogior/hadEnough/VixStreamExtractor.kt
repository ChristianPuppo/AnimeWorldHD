package it.dogior.hadEnough

import android.util.Log
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.ExtractorLinkType
import com.lagradost.cloudstream3.utils.newExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import okhttp3.HttpUrl.Companion.toHttpUrl
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
            ExtractorLink(
                source = "VixSrc",
                name = "VixStream",
                url = playlistUrl,
                referer = referer ?: "https://vixsrc.to/",
                quality = Qualities.Unknown.value,
                headers = mapOf(
                    "Origin" to "https://vixsrc.to",
                    "User-Agent" to USER_AGENT,
                    "Accept" to "*/*"
                ),
                extractorData = null,
                isDash = false
            )
        )
    }

    private suspend fun getPlaylistLink(url: String): String {
        Log.d(TAG, "Item url: $url")
        val apiUrl = url.replace("/movie/", "/api/movie/").replace("/tv/", "/api/tv/")
        
        val headers = mapOf(
            "Accept" to "application/json",
            "Referer" to "https://vixsrc.to/",
            "Origin" to "https://vixsrc.to",
            "User-Agent" to USER_AGENT
        )

        val response = app.get(apiUrl, headers = headers).text
        Log.d(TAG, "API Response: $response")
        val json = JSONObject(response)
        
        val embedPath = json.getString("src")
        var playlistPath = embedPath.replace("/embed/", "/playlist/")
        
        // Ensure high quality is selected if missing
        if (!playlistPath.contains("h=")) {
            playlistPath += "&h=1"
        }
        
        val masterPlaylistUrl = "https://vixsrc.to$playlistPath"
        Log.d(TAG, "Master Playlist URL: $masterPlaylistUrl")
        
        // Fetch the master playlist manually to bypass Cloudflare
        val masterPlaylistContent = app.get(masterPlaylistUrl, headers = headers).text
        Log.d(TAG, "Master Playlist Content:\n$masterPlaylistContent")
        
        // TODO: parse the master playlist to find direct URLs if needed
        return masterPlaylistUrl
    }
}
