package it.dogior.hadEnough

import com.lagradost.cloudstream3.*

class AnimeWorldHDSub(isSplit: Boolean) : AnimeWorldCore(isSplit) {
    override var name = "AnimeWorldHD Sub"
    override var lang = "jp"
    override val currentExtension = CurrentExtension.SUB

    override val mainPage = super.mainPage + mainPageOf(
        "$mainUrl/filter?status=0&language=jp&sort=1" to "In Corso",
        "$mainUrl/filter?language=jp&sort=1" to "Ultimi aggiunti",
        "$mainUrl/filter?language=jp&sort=6" to "Più Visti",
        "$mainUrl/tops/all?sort=1" to "Top Anime",
    )
}
