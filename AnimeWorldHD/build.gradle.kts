// use an integer for version numbers
version = 1


cloudstream {
    language = "it"
    // All of these properties are optional, you can safely remove them

    authors = listOf("Gian-Fr", "doGior", "Chruis")
    requiresResources = false
    description =
        "Anime in streaming da AnimeWorld con metadati e poster in alta risoluzione tramite AniList & ani.zip."

    /**
     * Status int as the following:
     * 0: Down
     * 1: Ok
     * 2: Slow
     * 3: Beta only
     * */
    status = 1 // will be 3 if unspecified
    tvTypes = listOf(
        "AnimeMovie",
        "Anime",
        "OVA",
    )

    iconUrl = "https://static.animeworld.ac/assets/images/favicon/android-icon-192x192.png?s"
}

android {
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation("com.google.android.material:material:1.12.0")
    implementation("org.mozilla:rhino:1.7.15")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
}