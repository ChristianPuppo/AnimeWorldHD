package it.dogior.hadEnough

import android.content.Context
import android.util.Log
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class StreamingCommunityPlugin : Plugin() {
    override fun load(context: Context) {
        try {
            Log.d("SCommunityPlugin", "Loading StreamingCommunity plugin...")
            
            val scProvider = StreamingCommunity()
            Log.d("SCommunityPlugin", "StreamingCommunity instance created: ${scProvider.name}")
            registerMainAPI(scProvider)
            Log.d("SCommunityPlugin", "StreamingCommunity registered!")
            
            registerExtractorAPI(VixCloudExtractor())
            Log.d("SCommunityPlugin", "VixCloudExtractor registered!")
            
            registerExtractorAPI(VixSrcExtractor())
            Log.d("SCommunityPlugin", "VixSrcExtractor registered!")
            
            Log.d("SCommunityPlugin", "Plugin loaded successfully!")
        } catch (e: Exception) {
            Log.e("SCommunityPlugin", "FATAL ERROR loading plugin: ${e.message}", e)
            e.printStackTrace()
        }
    }
}
