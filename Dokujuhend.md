---
permalink: Dokujuhend
---

# Dokumentatsiooni täiendamise juhend

- Kasuta Markdown-i; dokumentatsioon publitseeritakse `github.io` mehhanismiga.
- Dokumendi lisamiseks lisa uus `.md`-fail repo peakausta.
- Markdown-faili algusesse pane päis:

```
---
permalink: <URL-inimi>
---
```

- `URL-inimi` vali arvestusega, et faili poole pöördumine saab olema URL-ga: 
`https://e-gov.github.io/TARA-Doku/<URL-inimi>`.
- Joonise- ja pildifailid pane need kausta `img`.
- Muud failid (`.pdf` jms) pane kausta `files`.
- Avalehele `index.md` lisa sobivasse jaotisse link dokumendile, kujul: `[Dokumendi nimetus](URL-inimi)`.
- Konfidentsiaalset taristuteavet ära pane.
- Asjakohastes kohtades pane lingid JIRA ja Confluence-i suletud ruumidele. Hea on markeerida need võtmesümboliga `&#128273;`.
- Arvesta, et repo on avalik. Korrektne keel ja vormistus.
