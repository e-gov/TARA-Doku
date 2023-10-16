---
permalink: Dokuabi
---

# Abiks dokumenteerijale

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
- Joonise- ja pildifailid pane kausta `img`.
- Muud failid (`.pdf` jms) pane kausta `files`.
- Avalehele `index.md` lisa sobivasse jaotisse link dokumendile, kujul: `[Dokumendi nimetus](URL-inimi)`.
- Avalehel on selgelt eristatud:
    - teenuse ärikasutajale suunatud dok-n
    - liidese arendajale suunatud, tehniline dok-n
    - teenuse enda arendamise, sisemine dok-n
    - järgi seda eristust. 
- Konfidentsiaalset taristuteavet ära pane.
- Arvesta, et repo on avalik. Korrektne keel ja vormistus.

- Esiletõsteid saab lisada nii:

```
NB! See on rahulik esiletõst.
{:.note}
```

NB! See on rahulik esiletõst.
{:.note}

```
NB! See on silmatorkav esiletõst.
{:.adv}
```

NB! See on silmatorkav esiletõst.
{:.adv}

