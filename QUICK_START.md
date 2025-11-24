# 🚀 Quick Start - AnimeWorldHD Repository

## ✅ STATO: PRONTA PER IL BUILD!

Tutto è configurato correttamente:
- ✅ Repository GitHub creata
- ✅ Branch `main` e `builds` pronti
- ✅ Workflow GitHub Actions corretto (v4)
- ✅ `build.gradle.kts` configurato

---

## 🎯 3 PASSI PER PARTIRE

### 1️⃣ Configura Secret TMDB_API (2 minuti)

**Vai qui**: https://github.com/ChristianPuppo/AnimeWorldHD/settings/secrets/actions

**Opzione Veloce (test)**:
- Clicca "New repository secret"
- Nome: `TMDB_API`
- Valore: `dummy_key_12345`
- Salva

### 2️⃣ Avvia Build (1 click)

**Vai qui**: https://github.com/ChristianPuppo/AnimeWorldHD/actions

- Clicca "Build" nella sidebar
- Clicca "Run workflow" (dropdown)
- Seleziona branch: `main`
- Clicca "Run workflow" verde
- ⏱️ Attendi 5-10 minuti

### 3️⃣ Usa in CloudStream (30 secondi)

**Nell'app CloudStream**:
1. Impostazioni → Estensioni
2. "+ Aggiungi repository"
3. Scrivi: `ChristianPuppo`
4. OK

**Done!** 🎉

---

## 📱 LINK CLOUDSTREAM

```
https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/repo.json
```

Oppure il link corto (CloudStream espande automaticamente):
```
ChristianPuppo/AnimeWorldHD
```

---

## 🔧 PROBLEMI RISOLTI

### Fix 1: Checkout Builds Branch
**Errore**:
```
Error: The process '/usr/bin/git' failed with exit code 1
```

**Fix**: Aggiornato `actions/checkout@master` → `actions/checkout@v4`  
**Commit**: `d17450d`

### Fix 2: Clean Missing .cs3 Files
**Errore**:
```
rm: cannot remove '*.cs3': No such file or directory
```

**Fix**: Aggiunto `rm -f ... || true` per non fallire se file non esistono  
**Commit**: `1d67555`

### Fix 3: Missing gradle-wrapper.properties
**Errore**:
```
Wrapper properties file does not exist
```

**Fix**: Aggiunta eccezione a `.gitignore` per `gradle-wrapper.properties`  
**Commit**: `72ea95d`

### Fix 4: AndroidX Configuration Missing
**Errore**:
```
Configuration contains AndroidX dependencies, but the `android.useAndroidX` property is not enabled
```

**Fix**: Creato `gradle.properties` con `android.useAndroidX=true` e `android.enableJetifier=true`  
**Commit**: `1e7a4b2`

### Fix 5: AniList GraphQL Variables Format
**Errore**:
```
Argument type mismatch: actual type is 'Map<String, Any>', but 'Map<String, String>?' was expected
```

**Fix**: Le `variables` GraphQL devono essere stringa JSON `"""{"id":$id}"""`, non `Map`  
**Commit**: `4b5e402`

### Fix 6: GitHub Actions Write Permissions
**Errore**:
```
remote: Permission to ChristianPuppo/AnimeWorldHD.git denied to github-actions[bot]
```

**Fix**: Aggiunto `permissions: contents: write` al workflow  
**Commit**: `1be9858`

✅ **Build completato! 18 plugin creati incluso AnimeWorldHD.cs3!**

---

## 🎨 COSA OTTERRAI

### AnimeWorldHD - Features

| Feature | Descrizione |
|---------|-------------|
| 🖼️ **Fanart HD** | Banner 1920x1080 da ani.zip |
| 🎯 **AniList API** | Metadata premium come Ultima |
| 🇮🇹 **Contenuto ITA** | Tutti gli anime da AnimeWorld |
| 🔄 **Triple Fallback** | ani.zip → AniList → AnimeWorld |
| ⚡ **Zero Text Search** | Usa ID AniList diretti |

**Hero Banner**: Da immagine SD 225x338 → HD 1920x1080! 🎨

---

## 🧪 TEST RAPIDO

Dopo il build, verifica che funzioni:

### Test 1: plugins.json esiste?
```bash
curl https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/plugins.json
```

Dovresti vedere JSON con array di plugin.

### Test 2: AnimeWorldHD.cs3 esiste?
```bash
curl -I https://raw.githubusercontent.com/ChristianPuppo/AnimeWorldHD/builds/AnimeWorldHD.cs3
```

Dovresti vedere `HTTP/2 200`.

### Test 3: In CloudStream
1. Aggiungi repository
2. Scarica "AnimeWorldHD"
3. Cerca "Attack on Titan"
4. Guarda il banner hero → dovrebbe essere HD! ✨

---

## 📚 DOCUMENTAZIONE COMPLETA

Per dettagli completi, vedi:
- **Setup Guide**: `CLOUDSTREAM_SETUP.md`
- **AnimeWorldHD README**: `AnimeWorldHD/README.md`
- **Changelog**: `AnimeWorldHD/CHANGELOG.md`
- **API Tests**: `AnimeWorldHD/API_TEST_RESULTS.txt`

---

## 🆘 HELP

### Build fallisce?
→ Controlla che il secret `TMDB_API` sia configurato

### Repository non appare in CloudStream?
→ Aspetta che il workflow finisca (verifica su Actions)

### Immagini HD non appaiono?
→ Alcuni anime potrebbero non avere ID AniList su AnimeWorld

### Altri problemi?
→ Apri issue su: https://github.com/ChristianPuppo/AnimeWorldHD/issues

---

**Ready to go! 🚀**

Segui i 3 passi sopra e in 10 minuti avrai la tua repository funzionante! 