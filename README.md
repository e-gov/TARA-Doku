# TARA autentimisteenus

RIA autentimisteenuse "TARA" dokumentatsioon

----

Dokumentatsiooni täiendamisel palun arvesta ja järgi:

- kasutusel on Markdown ja Jekyll; dokumentatsioon publitseeritakse `github.io` mehhanismi abil.

- Uue dokumendi moodustamisel moodusta uus `.md`-fail repo peakausta.
- Markdown-faili algusesse pane päis:

```
---
permalink: <URL-inimi>
---
```

`URL-inimi` vali arvestusega, et faili poole pöördumine saab olema URL-ga:

`https://e-gov.github.io/TARA-Doku/<URL-inimi>` 

- kui kasutada dokumendis joonise- või pildifaile, siis pane need kausta `img`.
- muud failid (`.pdf` jms) pane kausta `files`.

- avalehele `index.md` lisa sobivasse jaotisse link dokumendile, kujul:

`[Dokumendi nimetus](URL-inimi)`

- arvesta, et repo on avalik. Konfidentsiaalset taristuteavet ära pane.
- asjakohastes kohtades pane lingid JIRA ja Confluence-i suletud ruumidele. Hea on markeerida need võtmesümboliga `&#128273;`.

- arvesta, et repo sisaldab nii TARA kasutajatele kui ka arendajatele suunatud dokumentatsiooni. Korrektne keel ja vormistus.






