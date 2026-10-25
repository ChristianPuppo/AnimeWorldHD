# 🎬 AnimeWorldHD - CloudStream Repository

<p align="center">
  <img src="https://img.shields.io/badge/CloudStream-Extensions-blue" alt="CloudStream"/>
  <img src="https://img.shields.io/badge/Anime-HD_Metadata-red" alt="HD Metadata"/>
  <img src="https://img.shields.io/badge/Language-Italian-green" alt="Italian"/>
  <img src="https://img.shields.io/badge/Status-Active_%26_Maintained-brightgreen" alt="Status"/>
</p>

---

## 📖 Informazioni sul Repository

Questo repository contiene una selezione curata, ottimizzata e attivamente mantenuta di plugin per **[CloudStream](https://github.com/recloudstream/cloudstream)** focalizzati sullo streaming italiano di **Film**, **Serie TV** e **Anime**.

---

## 🎮 Plugin Disponibili

Tutti i plugin inclusi sono stati verificati e aggiornati per garantire la massima stabilità e compatibilità con i domini attuali:

### 🎬 Film & Serie TV

| Plugin | Descrizione | Qualità / Fonte | Status |
|--------|-------------|-----------------|:------:|
| **StreamingCommunity** | Il catalogo completo di StreamingCommunity con tracker automatico dei reindirizzamenti e fallback dinamico del dominio. | Fino a 1080p FHD | ✅ Attivo |
| **VixStream** | Streaming ultra-rapido via VixCloud e metadati completi da TMDB. Include selezione tracce audio (Italiano, Inglese) e sottotitoli. | Fino a 1080p FHD | ✅ Attivo |
| **CB01** | Film, nuove uscite al cinema e Serie TV storiche da CineBlog01 (aggiornato al mirror `cb01one.com`). | HD / MaxStream | ✅ Attivo |

### ⛩️ Anime (Italiano & SUB-ITA)

| Plugin | Descrizione | Feature Principali | Status |
|--------|-------------|-------------------|:------:|
| **AnimeWorldHD** ⭐ | Versione avanzata con metadati in alta risoluzione da AniList e Fanart HD (1920x1080) da ani.zip. | Banner HD & Smart Fallback | ✅ Attivo |
| **AnimeWorld** | Catalogo completo da AnimeWorld con episodi SUB-ITA e doppiati in Italiano. | Vasto catalogo anime | ✅ Attivo |
| **AnimeUnity** | Streaming di episodi e film d'animazione giapponese da AnimeUnity. | Aggiornamenti rapidi | ✅ Attivo |

---

## 📦 Installazione su CloudStream

### Metodo 1️⃣: Link Diretto (Consigliato)

1. Apri **CloudStream** sul tuo dispositivo Android / Fire TV / Google TV.
2. Vai in **Impostazioni** (icona ingranaggio) → **Estensioni**.
3. Clicca su **"+ Aggiungi repository"**.
4. Incolla l'indirizzo:
   ```
   https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/repo.json
   ```
5. Clicca su **OK** e scarica i plugin desiderati.

### Metodo 2️⃣: Link Breve

Se la tua versione di CloudStream lo supporta, inserisci semplicemente:
```
ChristianPuppo/AnimeWorldHD
```

---

## 🔧 Manutenzione & Build

Il repository compila automaticamente tutti i file `.cs3` e aggiorna `plugins.json` su ogni push tramite **GitHub Actions**:

- **Workflow automatico**: compila su Ubuntu con Android SDK e Java 17 Temurin.
- **Branch di distribuzione**: i binari compilati risiedono sul branch [`builds`](https://github.com/ChristianPuppo/AnimeWorldHD/tree/builds).

### Build manuale in locale:
```bash
./gradlew make makePluginsJson
```

---

## 🙏 Crediti & Ringraziamenti

- **[doGior](https://github.com/doGior)**: per il repository base originale [doGiorsHadEnough](https://github.com/doGior/doGiorsHadEnough).
- **[CloudStream](https://github.com/recloudstream/cloudstream)**: la fantastica applicazione media player open source per Android.
- **[AniList](https://anilist.co)** & **[ani.zip](https://ani.zip)**: per le API e le immagini Fanart HD adoperate in AnimeWorldHD.

---

<p align="center">
  <b>Mantenuto con ❤️ da ChristianPuppo</b><br>
  🇮🇹 🎬 🎌
</p>
