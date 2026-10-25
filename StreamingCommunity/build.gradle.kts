// use an integer for version numbers
version = 43


cloudstream {
    // All of these properties are optional, you can safely remove them

    description = "Film e Serie TV da StreamingCommunity in italiano con rilevamento automatico del dominio."
    authors = listOf("doGior")

    /**
    * Status int as the following:
    * 0: Down
    * 1: Ok
    * 2: Slow
    * 3: Beta only
    * */
    status = 1
    tvTypes = listOf(
        "TvSeries",
        "Movie",
        "Documentary",
        "Cartoon"
    )

    requiresResources = false
    language = "it"

    iconUrl = "https://streamingcommunityz.build/apple-touch-icon.png?v=2"
}

