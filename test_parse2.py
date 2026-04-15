import requests, json
from bs4 import BeautifulSoup
s = requests.Session()
s.headers.update({"User-Agent": "Mozilla/5.0"})
res = s.get("https://streamingcommunityz.moe/it/archive")
html = BeautifulSoup(res.text, 'html.parser')
version = json.loads(html.find('div', id='app')['data-page'])['version']

s.headers.update({
    "X-Inertia": "true",
    "X-Inertia-Version": version,
    "X-Requested-With": "XMLHttpRequest"
})

res2 = s.get("https://streamingcommunityz.moe/it/browse/trending")
try:
    j = res2.json()
    titles = j.get("props", {}).get("titles")
    sliders = j.get("props", {}).get("sliders", [])
    print(f"Titles array: {type(titles)} (is None: {titles is None})")
    print(f"Sliders array length: {len(sliders)}")
    if sliders:
        # print first slider's titles count
        print(f"First slider titles: {len(sliders[0].get('titles', []))}")
except Exception as e:
    print("FAILED JSON PARSE:")
