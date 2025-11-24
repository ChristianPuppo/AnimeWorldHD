# StreamingCommunity Fix v24

## 🐛 Problema

StreamingCommunity non funzionava:
- ❌ Homepage vuota (nessun contenuto)
- ❌ Ricerca non restituiva risultati

## 🔍 Causa

StreamingCommunity usa **Inertia.js** che richiede headers speciali:
- `Cookie` - Session cookies dal sito
- `X-Inertia-Version` - Versione Inertia estratta dalla pagina

Il problema era che `getMainPage()` **NON** chiamava `setupHeaders()` e **NON** passava gli `headers` alle richieste API.

## ✅ Soluzione

### Fix 1: Chiamare `setupHeaders()` prima di usare l'API

```kotlin
override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
    // ✅ NUOVO: Inizializza headers se vuoti
    if (headers["Cookie"].isNullOrEmpty()) {
        setupHeaders()
    }
    // ... resto del codice
}
```

### Fix 2: Passare `headers` alle richieste API

```kotlin
// ❌ PRIMA (senza headers)
val response = app.get(url, params = params)

// ✅ DOPO (con headers)
val response = app.get(url, params = params, headers = headers)
```

## 📊 Test

### Test 1: Homepage
```bash
curl -s "https://streamingunity.co/api/browse/trending?lang=it"
```
Risultato: ✅ JSON con contenuti

### Test 2: Ricerca
```bash
curl -s "https://streamingunity.co/api/search?q=avengers&lang=it"
```
Risultato: ✅ JSON con risultati di ricerca

## 🎯 Risultato

- ✅ Homepage ora mostra contenuti
- ✅ Ricerca funziona correttamente
- ✅ Tutte le sezioni (Trending, Latest, Top10, Generi) funzionano

## 📝 Note Tecniche

**Inertia.js** è un framework per creare SPA (Single Page Applications) che richiede:
1. Cookie di sessione valido
2. Header `X-Inertia: true`
3. Header `X-Inertia-Version` con la versione corrente

La funzione `setupHeaders()` estrae questi valori dalla pagina `/archive`:
```kotlin
val inertiaPageObject = page.select("#app").attr("data-page")
inertiaVersion = inertiaPageObject
    .substringAfter("\"version\":\"")
    .substringBefore("\"")
```

## 🚀 Come Testare

1. Ricompila il plugin con `./gradlew make`
2. Reinstalla StreamingCommunity in CloudStream
3. Apri l'app e vai alla sezione StreamingCommunity
4. Verifica che la homepage mostri contenuti
5. Prova a cercare un film/serie TV

---

**Fix by**: Chruis  
**Version**: 24  
**Date**: 2025-11-24  
**Commit**: `41e1025` 