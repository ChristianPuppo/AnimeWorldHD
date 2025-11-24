# 📱 CloudStream Repository Setup Guide

## ✅ STATO ATTUALE

- ✅ Repository creata su GitHub: `ChristianPuppo/AnimeWorldHD`
- ✅ Branch `main` con codice sorgente
- ✅ Branch `builds` creato per i file compilati
- ✅ `build.gradle.kts` configurato con il repository corretto
- ⏳ **Manca**: Configurare GitHub Actions e compilare

---

## 🔧 SETUP GITHUB ACTIONS

### 1️⃣ Aggiungi il Secret TMDB_API

**⚠️ IMPORTANTE**: Il workflow di build richiede un API key di TMDB.

**Opzione A - Usa una chiave TMDB (consigliato):**

1. Vai su https://www.themoviedb.org/settings/api
2. Crea un account e richiedi una API key (gratis)
3. Vai su GitHub: https://github.com/ChristianPuppo/AnimeWorldHD/settings/secrets/actions
4. Clicca "New repository secret"
5. Nome: `TMDB_API`
6. Valore: la tua API key di TMDB
7. Clicca "Add secret"

**Opzione B - Usa una chiave dummy (temporaneo):**

Se non hai bisogno delle funzioni TMDB:
1. Vai su https://github.com/ChristianPuppo/AnimeWorldHD/settings/secrets/actions
2. Nome: `TMDB_API`
3. Valore: `dummy_key_12345`
4. Clicca "Add secret"

---

### 2️⃣ Triggera il Workflow Manualmente

1. Vai su: https://github.com/ChristianPuppo/AnimeWorldHD/actions
2. Clicca su "Build" nella sidebar sinistra
3. Clicca su "Run workflow" (bottone dropdown a destra)
4. Seleziona branch: `main`
5. Clicca "Run workflow" (bottone verde)

Il workflow:
- ✅ Compilerà tutti i plugin (.cs3)
- ✅ Creerà `plugins.json`
- ✅ Pusherà tutto nel branch `builds`
- ⏱️ Durata: ~5-10 minuti

---

### 3️⃣ Verifica il Build

Dopo il completamento, verifica che il file esista:

```
https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/plugins.json
```

Dovresti vedere un JSON con la lista di tutti i plugin disponibili.

---

## 📱 AGGIUNGI A CLOUDSTREAM

Ora puoi aggiungere la repository a CloudStream in **2 modi**:

### Metodo 1️⃣: Link Corto (consigliato)

Nell'app CloudStream:
1. Vai in **Impostazioni** → **Estensioni**
2. Clicca **"+ Aggiungi repository"**
3. Incolla: `ChristianPuppo`
4. Clicca **OK**

CloudStream risolverà automaticamente a:
```
https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/plugins.json
```

### Metodo 2️⃣: Link Completo

Nell'app CloudStream:
1. Vai in **Impostazioni** → **Estensioni**
2. Clicca **"+ Aggiungi repository"**
3. Incolla:
```
https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/plugins.json
```
4. Clicca **OK**

---

## 🎉 PLUGIN DISPONIBILI

Dopo aver aggiunto la repository, vedrai questi plugin:

| Plugin | Descrizione | Lingua |
|--------|-------------|--------|
| **AnimeWorldHD** ⭐ | Anime con HD metadata da ani.zip + AniList | 🇮🇹 |
| AnimeWorld | Anime standard (versione originale) | 🇮🇹 |
| AnimeUnity | Anime da AnimeUnity | 🇮🇹 |
| AltaDefinizione | Film e Serie TV | 🇮🇹 |
| StreamingCommunity | Film, Serie TV, Anime | 🇮🇹 |
| CB01 | Film e Serie TV | 🇮🇹 |
| DaddyLive | TV e Sport Live | 🌍 |
| IPTV | Canali TV live | 🌍 |
| Torrentio | Torrenti via Stremio | 🌍 |
| ...e molti altri! | | |

---

## 🔄 AUTO-BUILD

Il workflow si attiverà automaticamente quando:
- ✅ Fai push sul branch `main`
- ✅ Modifichi file Kotlin (`.kt`)
- ✅ Modifichi file Gradle (`.kts`)
- ❌ **NON** si attiva per modifiche a:
  - File Markdown (`.md`)
  - Immagini (`.png`, `.jpg`)
  - File YAML (`.yml`)

---

## 📊 VERIFICA CHE FUNZIONI

### Test 1: Controlla plugins.json

```bash
curl https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/plugins.json | jq
```

Dovresti vedere JSON con array di plugin.

### Test 2: Controlla i file .cs3

```bash
curl -I https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/AnimeWorldHD.cs3
```

Dovresti vedere `200 OK`.

### Test 3: CloudStream App

1. Aggiungi repository come sopra
2. Vai in **Estensioni** → **Tutte**
3. Cerca "AnimeWorldHD"
4. Clicca **Scarica**
5. Testa con un anime (es: Attack on Titan)
6. Verifica che il banner hero sia in HD! 🎨

---

## 🎯 LINK UTILI

- **Repository GitHub**: https://github.com/ChristianPuppo/AnimeWorldHD
- **Branch Main**: https://github.com/ChristianPuppo/AnimeWorldHD/tree/main
- **Branch Builds**: https://github.com/ChristianPuppo/AnimeWorldHD/tree/builds
- **GitHub Actions**: https://github.com/ChristianPuppo/AnimeWorldHD/actions
- **Secrets Settings**: https://github.com/ChristianPuppo/AnimeWorldHD/settings/secrets/actions

---

## ❓ TROUBLESHOOTING

### Workflow fallisce con "SDK not found"
→ È normale, il workflow usa GitHub Actions runners con SDK preinstallato.

### Workflow fallisce con "TMDB_API not found"
→ Aggiungi il secret `TMDB_API` come spiegato sopra.

### plugins.json non si aggiorna
→ Controlla lo stato del workflow su Actions. Potrebbe essere ancora in esecuzione.

### CloudStream non trova la repository
→ Assicurati che il branch `builds` esista e contenga `plugins.json`.

### Le immagini HD non appaiono
→ Verifica che l'anime abbia un ID AniList. Non tutti gli anime su AnimeWorld ce l'hanno.

---

## 🚀 PASSI SUCCESSIVI

1. ✅ Configura TMDB_API secret
2. ✅ Trigga il workflow manualmente
3. ✅ Attendi il completamento (5-10 min)
4. ✅ Verifica che plugins.json esista
5. ✅ Aggiungi `ChristianPuppo` a CloudStream
6. ✅ Scarica e testa AnimeWorldHD
7. 🎉 Goditi gli anime in HD!

---

**Made with ❤️ by Chruis**  
**Powered by ani.zip + AniList + AnimeWorld** 