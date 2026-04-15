import requests, json
from bs4 import BeautifulSoup

s = requests.Session()
s.headers.update({"User-Agent": "Mozilla/5.0"})
res = s.get("https://streamingcommunityz.moe/it/archive")

# Get version
html = BeautifulSoup(res.text, 'html.parser')
data_page = html.find('div', id='app')['data-page']
version = json.loads(data_page)['version']

print("Version:", version)

s.headers.update({
    "X-Inertia": "true",
    "X-Inertia-Version": version,
    "X-Requested-With": "XMLHttpRequest"
})

res2 = s.get("https://streamingcommunityz.moe/it/search", params={"q": "batman"})
try:
    j = res2.json()
    titles = j.get("props", {}).get("titles", [])
    print(f"Success! Found {len(titles)} titles")
    if titles:
        print("First title:", titles[0].get("name"))
except Exception as e:
    print("FAILED JSON PARSE:", res2.text[:500])
