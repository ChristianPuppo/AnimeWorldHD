import requests
s = requests.Session()
s.headers.update({"User-Agent": "Mozilla/5.0"})
r = s.get("https://streamingcommunityz.moe/archive")

version_str = r.text.split('"version":"')[1].split('"')[0]

s.headers.update({
    "X-Inertia": "true",
    "X-Inertia-Version": version_str,
    "X-Requested-With": "XMLHttpRequest"
})

r2 = s.get("https://streamingcommunityz.moe/it/browse/trending")
print(r2.text[:500])
