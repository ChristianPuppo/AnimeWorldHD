# 🎬 AnimeWorldHD - CloudStream Repository

<p align="center">
  <img src="https://img.shields.io/badge/CloudStream-Extensions-blue" alt="CloudStream"/>
  <img src="https://img.shields.io/badge/Anime-HD_Metadata-red" alt="HD Metadata"/>
  <img src="https://img.shields.io/badge/Language-Italian-green" alt="Italian"/>
  <img src="https://img.shields.io/badge/Fork-doGior-orange" alt="Fork"/>
</p>

---

## 📖 About This Repository

Questo repository è un **fork** del progetto originale [**doGiorsHadEnough**](https://github.com/doGior/doGiorsHadEnough) di [doGior](https://github.com/doGior), con modifiche e migliorie sviluppate da **Chruis**.

### 🎯 Differenze Principali

| Caratteristica | Originale (doGior) | Questo Fork (Chruis) |
|---------------|-------------------|---------------------|
| **AnimeWorld** | Versione standard | ➕ **AnimeWorldHD** con metadata AniList |
| **Qualità Immagini** | Standard (SD) | ⭐ **HD 1920x1080** (Fanart da ani.zip) |
| **Banner Hero** | Poster 225x338px | 🖼️ **Wide Banner 1920x1080px** |
| **Metadata** | Solo AnimeWorld | 🎨 **ani.zip + AniList GraphQL API** |
| **Fallback Images** | Nessuno | 🔄 **Triple Fallback System** |

---

## ⭐ Novità: AnimeWorldHD

**AnimeWorldHD** è una versione potenziata dell'estensione **AnimeWorld** che integra metadata di alta qualità da:

- 🎨 **[ani.zip](https://ani.zip)** - Community-curated Fanart (1920x1080)
- 📊 **[AniList](https://anilist.co)** - Banner e Cover ufficiali
- 🇮🇹 **[AnimeWorld](https://animeworld.tv)** - Contenuti ITA + AniList IDs

### 🚀 Vantaggi di AnimeWorldHD

| Feature | Descrizione |
|---------|-------------|
| 🖼️ **Hero Banner HD** | Banner 1920x1080 invece di 225x338 (8.5x più pixel!) |
| 🎯 **ID-Based Fetching** | Usa AniList IDs (no ricerca per titolo) |
| 🔄 **Smart Fallback** | ani.zip → AniList → AnimeWorld (sempre un'immagine!) |
| ⚡ **Zero Text Search** | Niente ricerche ambigue, solo match precisi |
| 🇮🇹 **100% Compatibile** | Stesso catalogo AnimeWorld + visual migliorati |

### 📸 Qualità Visiva

```
┌─────────────────────────────────────────────────────────────┐
│  HERO BANNER                                                │
├─────────────────────────────────────────────────────────────┤
│  AnimeWorld Standard:   225 x 338px  =   76,050 pixel     │
│  AnimeWorldHD Fanart:  1920 x 1080px = 2,073,600 pixel    │
│                                                             │
│  🎨 27x PIÙ PIXEL = QUALITÀ CINEMATOGRAFICA!               │
└─────────────────────────────────────────────────────────────┘
```

---

## 📦 Installazione

### Metodo 1️⃣: Link Completo (Consigliato)

1. Apri **CloudStream**
2. Vai in **Impostazioni** → **Estensioni**
3. Clicca **"+ Aggiungi repository"**
4. Incolla:
   ```
   https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/repo.json
   ```
5. Clicca **OK**

### Metodo 2️⃣: Link Corto

```
ChristianPuppo/AnimeWorldHD
```

CloudStream espanderà automaticamente il link completo.

---

## 🎮 Plugin Disponibili

### ⭐ Nuovi / Modificati

| Plugin | Descrizione | Status | Note |
|--------|-------------|:------:|------|
| **AnimeWorldHD** 🆕 | Anime con HD metadata (ani.zip + AniList) | ✅ | **NOVITÀ DI QUESTO FORK!** |
| **StreamingCommunity** 🔧 | Fix Inertia headers + Homepage + Search | ✅ | **FIXATO IN QUESTO FORK!** |

### 🇮🇹 Plugin Italiani (da doGior)

| Plugin | Sito | Categorie | Status | Note |
|--------|------|-----------|:------:|------|
| AnimeWorld | [animeworld.tv](https://animeworld.tv) | Anime | ✅ | Versione standard |
| AnimeUnity | [animeunity.so](https://www.animeunity.so) | Anime | ✅ | |
| AltaDefinizione | [altadefinizionegratis.store](https://altadefinizionegratis.store) | Film, TV, Cartoni | ✅ | |
| StreamingCommunity | [streamingunity.co](https://streamingunity.co) | Film, TV, Cartoni | ✅ | Fix v25 Inertia headers |
| CB01 | [cb01.uno](https://cb01.uno) | Film, TV | ✅ | Richiede prerelease CS |
| CalcioStreaming | [guardacalcio.icu](https://guardacalcio.icu) | Sport Live | ✅ | |
| CorsaroNero | [ilcorsaronero.link](https://ilcorsaronero.link) | Film | ✅ | |
| Arte | [arte.tv](https://www.arte.tv) | Documentari | ✅ | Multi-lingua in futuro |
| Torrentio | [torrentio.strem.fun](https://torrentio.strem.fun) | Film, TV, Anime | ✅ | |

### 🌍 Plugin Internazionali (da doGior)

| Plugin | Descrizione | Status | Note |
|--------|-------------|:------:|------|
| DaddyLive | TV e Sport Live | ✅ | Potrebbe richiedere VPN |
| Huhu | TV Live | ✅ | |
| IPTV | Canali TV (Free-TV/IPTV) | ✅ | |
| TV | Canali TV Live | ✅ | |

---

## 🔧 Sviluppo

### Requisiti

- Java 17+
- Android SDK
- Gradle 8.12+

### Build Locale

```bash
cd doGiorsHadEnough-master
./gradlew make makePluginsJson
```

### GitHub Actions

Il repository usa GitHub Actions per build automatici:
- ✅ Trigger su push a `main`
- ✅ Compila tutti i `.cs3`
- ✅ Genera `plugins.json` e `repo.json`
- ✅ Pubblica su branch `builds`

---

## 📚 Documentazione

- **[Quick Start Guide](QUICK_START.md)** - Setup rapido in 3 passi
- **[CloudStream Setup](CLOUDSTREAM_SETUP.md)** - Guida completa setup
- **[AnimeWorldHD README](AnimeWorldHD/README.md)** - Dettagli tecnici AnimeWorldHD
- **[AnimeWorldHD CHANGELOG](AnimeWorldHD/CHANGELOG.md)** - Spiegazione sistema di fetching
- **[API Test Results](AnimeWorldHD/API_TEST_RESULTS.txt)** - Risultati test ani.zip + AniList

---

## 🙏 Crediti

### Progetto Originale
- **[doGior](https://github.com/doGior)** - Autore del repository originale [doGiorsHadEnough](https://github.com/doGior/doGiorsHadEnough)
- Tutti i plugin italiani eccetto AnimeWorldHD sono opera di doGior e dei contributori originali

### Modifiche e Migliorie di Questo Fork
- **Chruis** ([ChristianPuppo](https://github.com/ChristianPuppo)) - AnimeWorldHD + Integrazione ani.zip/AniList

### Tecnologie
- **[CloudStream](https://github.com/recloudstream/cloudstream)** - App di streaming
- **[ani.zip](https://ani.zip)** - Community-curated anime metadata
- **[AniList](https://anilist.co)** - Anime database e API GraphQL
- **[AnimeWorld](https://animeworld.tv)** - Fonte contenuti italiani

---

## 💡 Perché Un Fork?

Ho deciso di creare questo fork per:

1. ✅ **Mantenere separati i progetti** - Le modifiche HD sono sperimentali
2. ✅ **Testare nuove feature** - Integrazione ani.zip è una proof-of-concept
3. ✅ **Libertà di sviluppo** - Posso iterare velocemente senza impattare l'originale
4. ✅ **Crediti chiari** - Il lavoro di doGior rimane riconosciuto

**Rispetto totale per il progetto originale di doGior!** ❤️

Se le modifiche HD dovessero interessare, sono disponibile a contribuire upstream tramite PR.

---

## 🔗 Link Utili

- **Repository Originale**: https://github.com/doGior/doGiorsHadEnough
- **Questo Fork**: https://github.com/ChristianPuppo/AnimeWorldHD
- **Branch Builds**: https://github.com/ChristianPuppo/AnimeWorldHD/tree/builds
- **GitHub Actions**: https://github.com/ChristianPuppo/AnimeWorldHD/actions

### Altre Repository CloudStream

- [Lista Completa Repository CS3](https://rentry.org/cs3-repos)
- [Documentazione CloudStream](https://recloudstream.github.io/csdocs/)

---

## 💝 Supporto

### Supporta doGior (Autore Originale)
- [Buy Me a Coffee](https://buymeacoffee.com/dogior) ☕

### Supporta Questo Fork
Se apprezzi le migliorie HD e vuoi supportare lo sviluppo di questo fork, considera:
- ⭐ Dai una stella a questo repository
- 🐛 Segnala bug o suggerimenti via Issues
- 🔀 Contribuisci con Pull Requests

---

## 📜 Licenza

Questo fork mantiene la stessa licenza del progetto originale.

---

## 🚀 Status Repository

| Componente | Status | Note |
|------------|:------:|------|
| GitHub Actions | ✅ | Auto-build attivo |
| Branch `builds` | ✅ | Aggiornato automaticamente |
| `repo.json` | ✅ | Configurato correttamente |
| `plugins.json` | ✅ | Generato automaticamente |
| AnimeWorldHD | ✅ | Funzionante e testato |
| Altri Plugin | ✅ | Ereditati da doGior |

---

<p align="center">
  <b>Made with ❤️ by Chruis</b><br>
  <i>Powered by doGior's original work + ani.zip + AniList</i><br>
  🇮🇹 🎌 🎬
</p>
