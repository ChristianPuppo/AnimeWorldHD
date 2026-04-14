// use an integer for version numbers
version = 40


cloudstream {
    // All of these properties are optional, you can safely remove them

    description = "StreamingCommunity. Include auto-redirect tracker. In caso estremo di blocco totale, cerca nell'app: !!sc-domain https://nuovo.sito e riavvia l'app."
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

    iconUrl = "https://streamingcommunityz.moe/apple-touch-icon.png?v=2"
}

