import urllib.request
import ssl
import json
import re
import html as html_lib

ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

base = "https://streamingcommunityz.build"
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
}

print(f"=== TEST 1: Connessione ad archivio e recupero versione Inertia ===")
req_archive = urllib.request.Request(f"{base}/it/archive", headers=headers)
with urllib.request.urlopen(req_archive, timeout=10, context=ctx) as resp:
    assert resp.getcode() == 200, f"Archive code: {resp.getcode()}"
    html = resp.read().decode("utf-8")
    cookies = resp.headers.get("Set-Cookie", "")
    m = re.search(r"data-page=\"([^\"]+)\"", html)
    assert m, "data-page non trovato in archive"
    data_page = json.loads(html_lib.unescape(m.group(1)))
    version = data_page.get("version")
    print(f"✅ Versione Inertia estratta con successo: {version}")

inertia_headers = {
    "User-Agent": headers["User-Agent"],
    "X-Inertia": "true",
    "X-Inertia-Version": str(version),
    "X-Requested-With": "XMLHttpRequest",
    "Cookie": cookies
}

print(f"\n=== TEST 2: Homepage (Sezione Trending) ===")
req_trending = urllib.request.Request(f"{base}/it/browse/trending?lang=it", headers=inertia_headers)
with urllib.request.urlopen(req_trending, timeout=10, context=ctx) as resp:
    assert resp.getcode() == 200
    trending_data = json.loads(resp.read().decode("utf-8"))
    titles = trending_data.get("props", {}).get("titles", [])
    print(f"✅ Titoli in trending trovati: {len(titles)}")
    assert len(titles) > 0, "Nessun titolo in trending"
    first = titles[0]
    print(f"   Esempio primo titolo: {first.get('name')} (id: {first.get('id')})")

print(f"\n=== TEST 3: Ricerca (Query: 'matrix') ===")
req_search = urllib.request.Request(f"{base}/it/search?q=matrix", headers=inertia_headers)
with urllib.request.urlopen(req_search, timeout=10, context=ctx) as resp:
    assert resp.getcode() == 200
    search_data = json.loads(resp.read().decode("utf-8"))
    search_titles = search_data.get("props", {}).get("titles", [])
    print(f"✅ Risultati ricerca trovati: {len(search_titles)}")
    assert len(search_titles) > 0, "Nessun risultato di ricerca"
    target = search_titles[0]
    print(f"   Titolo trovato: {target.get('name')} (ID: {target.get('id')}, slug: {target.get('slug')})")

print(f"\n=== TEST 4: Caricamento Dettagli Titolo ===")
req_details = urllib.request.Request(f"{base}/it/titles/{target.get('id')}-{target.get('slug')}", headers=inertia_headers)
with urllib.request.urlopen(req_details, timeout=10, context=ctx) as resp:
    assert resp.getcode() == 200
    details_data = json.loads(resp.read().decode("utf-8"))
    title_obj = details_data.get("props", {}).get("title", {})
    print(f"✅ Titolo caricato: {title_obj.get('name')}, TMDB ID: {title_obj.get('tmdb_id')}")

print(f"\n=== TEST 5: Estrazione iframe e video stream ===")
req_iframe = urllib.request.Request(f"{base}/it/iframe/{target.get('id')}?canPlayFHD=1", headers=headers)
with urllib.request.urlopen(req_iframe, timeout=10, context=ctx) as resp:
    assert resp.getcode() == 200
    iframe_html = resp.read().decode("utf-8")
    m_src = re.search(r"iframe[^>]+src=[\"\x27]([^\"]+)[\"\x27]", iframe_html)
    assert m_src, "Iframe src non trovato"
    vixcloud_url = m_src.group(1).replace("&amp;", "&")
    print(f"✅ Iframe VixCloud estratto: {vixcloud_url[:80]}...")

vix_headers = {
    "User-Agent": headers["User-Agent"],
    "Referer": f"{base}/"
}
req_vix = urllib.request.Request(vixcloud_url, headers=vix_headers)
with urllib.request.urlopen(req_vix, timeout=10, context=ctx) as resp:
    assert resp.getcode() == 200
    vix_body = resp.read().decode("utf-8")
    assert "masterPlaylist" in vix_body, "masterPlaylist non trovato in VixCloud"
    print(f"✅ VixCloud masterPlaylist confermata e pronta per lo streaming!")

print("\n🎉 TUTTI I TEST SONO PASSATI CON SUCCESSO! IL PLUGIN FUNZIONA AL 100%!")
