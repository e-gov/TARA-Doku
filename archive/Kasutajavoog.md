---
permalink: Joudlus
---

# [ARHIVEERITUD]

&#9888; Dokumenti ei ajakohastata. Palume siinsele teabele mitte tugineda.

# Jõudlusanalüüs

## Jõudlusmudel

Jõudlusmudeli (_performance model_) all mõistame süsteemi ehituse läbilõiget, mis toob esile kasutaja liikumisteed süsteemis, olekud, milles kasutaja võib viibida ja üleminekud nende olekute vahel. Mudeli eesmärk on olla abiks jõudluseesmärkide püstitamisel ja tegeliku jõudluse mõõtmissüsteemi ülesehitamisel.

<img src='img/KasutajateVoog.PNG' style='width:600px'>

1. Kasutajad saabuvad TARA-sse klientrakendustest.
2. TARA esilehel valib kasutaja autentimismeetodi.
3. Järgneb autentimisdialoog, vastavalt valitud meetodile.
4. Autentimisdialoogi (5, kui pangalingid võtta eraldi, siis 10 erinevat dialoogi) jõudlus sõltub nii TARA kui ka välisteenuste (RIA eIDAS konnektorteenus ja selle taga olev välisriikide eIDAS-taristu) jõudlusest.
5. Autentimisdialoog võib päädida kolme olekusse:
  5.1 Kasutaja saadetakse (autenditult) klientrakendusse tagasi
  5.2 Autentimisel tekkis viga; Kasutajale esitatakse veateade
  5.3 Kasutaja katkestab autentimise (lahkub)
6. Veateate saamisel on võimalik tagasi pöörduda TARA esilehele
7. TARA esilehelt on võimalik tagasi pöörduda klientrakendusse ilma autentimata (kui klientrakendus on nii seadistatud).

Need on kasutaja liikumisteed TARAs.

## Jõudluseesmärgid

Jõudluseesmärgiks on püstitatud: TARA peab suutma teenindada 100 kasutajat/s.

See tähendab:
- kasutajate voogu, kus iga sekund saabub 100 uut kasutajat; üheaegselt võib süsteemis olla kuni 30 000 kasutajat (arvestusega, et kasutaja autentimiseks kulub kuni 5 minutit)
- adekvaatseid lehelaadimisaegu (praktiliselt silmapilkselt)
- praktiliselt silmapilkseid töötlusaegu - selles osas, mis ei sõltu välisteenuste jõudlusest
- adekvaatset kasutaja teavitamist välisteenuste ebaadekvaatse jõudluse korral.

## Koormustestimise strateegia

TARA koormustestimise strateegia piiritleb TARA teenuse koormustestimise eesmärgid ja põhimõtted lähtuvalt jõudlusmudelist ja eelpüstitatud jõudluseesmärgist.

TARA koormustestimise strateegia arvestab järgmiste jõudlusmudeli omadustega: 
- TARA töökoormus (_workload_) ja päringute töötlemise kiirus 
- Välisteenuste mõju TARA töökoormusele
- TARA mõju riistvara ressurssidele (CPU)
- Klasterdamise mõju TARA jõudlusele

Töökoormuse all mõistame TARA võimekust teenindada kasutajaid liikumisteede {1, 2, 3, 4, 5.1} lõikes. Liikumistee 4 puhul rakendatakse koormustestimisel autentimisdialooge m-ID, pangalink 1, pangalink 2, Smart-ID. 
Päringute töötlemise kiiruse all mõistame vastuspäringute saatmise kiirust (_request response time_).
TARA teenuse töökoormust hinnatakse välisteenuste suhtes isoleeritult, s.t välisteenused asendatakse imitatsioonidega (_mock_ teenused).
Klasterdamise mõju TARA jõudlusele hinnatakse skaleeruvustesti põhjal.





 


