# AnimeWorldHD

**Versione migliorata di AnimeWorld con metadata di alta qualità da AniList**

## 🌟 Caratteristiche

### Immagini ad Alta Risoluzione
- **Banner Wide Hero**: Banner panoramico in HD nella hero della home screen
- **Poster Extra Large**: Cover poster in altissima qualità da AniList
- **Banner Pagina Dettaglio**: Background banner wide nella schermata anime
- **Fallback Intelligente**: Se AniList non ha immagini, usa quelle di AnimeWorld

### Come Funziona
1. Scrappa contenuti e link video da **AnimeWorld.ac** (streaming italiano)
2. Estrae l'**ID AniList** già presente su AnimeWorld
3. Fetcha immagini **Fanart** professionali da **ani.zip** (stesso sistema di Ultima)
4. Fallback su **AniList GraphQL API** per metadata aggiuntivi
5. Combina il meglio: contenuto italiano + immagini professionali community-curated

### Vantaggi
- ✅ **Zero ricerca testuale** - usa ID precisi
- ✅ **Nessuna dipendenza** da altre estensioni
- ✅ **100% compatibile** con tutte le funzioni di AnimeWorld
- ✅ **Performance ottimizzata** - cache automatica
- ✅ **Fallback robusto** - funziona anche se AniList è offline

## 🎨 Differenze con AnimeWorld Standard

| Feature | AnimeWorld | AnimeWorldHD |
|---------|-----------|-------------|
| Hero Banner | Poster portrait SD | Banner wide HD |
| Poster Card | Standard quality | Extra Large HQ |
| Detail Banner | Poster only | Wide banner + poster |
| Long Press Preview | Low quality | High quality |
| Source | AnimeWorld scraping | AnimeWorld + AniList API |

## 📦 Installazione

1. Aggiungi questa repository a CloudStream
2. Installa **AnimeWorldHD** dal manager estensioni
3. Disabilita/rimuovi AnimeWorld standard (opzionale)

## 👨‍💻 Credits

- **Original AnimeWorld**: Gian-Fr, doGior
- **HD Metadata Integration**: Chruis (inspired by Ultima)
- **ani.zip**: Manami Project (community-curated database)
- **AniList API**: AniList.co
- **CloudStream Framework**: LagradOst

## 📝 Note Tecniche

- **ani.zip**: Database community-curated con Fanart professionali
- **AniList GraphQL**: Fallback per banner e cover ufficiali
- **Jackson ObjectMapper**: Parsing JSON ottimizzato
- **Triple Fallback**: ani.zip → AniList API → AnimeWorld scraping
- **Query ottimizzate**: Richieste parallele per ridurre latenza
- Compatibile con split mode (Sub/Dub separati)

## 🔗 Links

- [AnimeWorld](https://www.animeworld.ac)
- [AniList](https://anilist.co)
- [ani.zip](https://github.com/manami-project/anime-offline-database)
- [CloudStream](https://github.com/recloudstream/cloudstream) 