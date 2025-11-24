# Changelog - AnimeWorldHD

## Version 1.0 - Initial Release

### 🎨 Image Fetching System (Same as Ultima)

**Priority Chain:**
1. **ani.zip Fanart** (Highest Priority)
   - Community-curated professional banners
   - High quality, perfectly sized for mobile/TV
   - Best for hero banners and backgrounds

2. **AniList Banner/Cover** (Fallback)
   - Official AniList bannerImage for backgrounds
   - Official coverImage (extraLarge > large > medium) for posters
   - Good quality, official source

3. **AnimeWorld Scraping** (Final Fallback)
   - Original scraped images from AnimeWorld.ac
   - Lower quality but always available
   - Guarantees content is always displayed

### 📊 Why ani.zip Fanart?

**Problem with AniList bannerImage:**
- Too wide (1920x1080+) for hero display
- Gets cropped/resized losing quality
- Not optimized for app display

**ani.zip Fanart Advantages:**
- Curated by community for optimal display
- Perfect aspect ratio for hero/background
- High quality without being oversized
- Same system used by Ultima (proven quality)

### 🔧 Technical Implementation

```kotlin
// 1. Fetch from ani.zip
val aniZipData = fetchAniZipMetadata(anilistId)
val fanartImage = aniZipData?.images?.firstOrNull { 
    it.coverType == "Fanart" 
}?.url

// 2. Fetch from AniList as fallback
val anilistData = fetchAniListMetadata(anilistId)

// 3. Combine with priority
backgroundPosterUrl = fanartImage ?: anilistBanner
posterUrl = fanartImage ?: anilistCover ?: animeWorldPoster
```

### ✨ Features

- ✅ Hero banner HD quality
- ✅ Poster extra large
- ✅ Background banner in detail page
- ✅ Triple fallback system
- ✅ 100% compatible with AnimeWorld
- ✅ All video links from AnimeWorld.ac

### 🔗 API Sources

- **ani.zip**: `https://api.ani.zip/mappings?anilist_id={id}`
- **AniList**: `https://graphql.anilist.co` (GraphQL)
- **AnimeWorld**: HTML scraping from animeworld.ac

### 📝 Notes

This implementation follows the exact same pattern as Ultima's AniList provider, ensuring consistency and proven quality across CloudStream ecosystem. 