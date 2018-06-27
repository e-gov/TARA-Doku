---
permalink: Joudlus
---

# Jõudlusanalüüs

## Jõudlusmudel

Jõudlusmudeli (_performance model_) all mõistame süsteemi ehituse läbilõiget, mis toob esile kasutaja liikumisteed süsteemis, olekud, milles kasutaja võib viibida ja üleminekud nende olekute vahel. Mudeli eesmärk on olla abiks jõudluseesmärkide püstitamisel ja tegeliku jõudluse mõõtmissüsteemi ülesehitamisel.

<img src='img/KasutajateVoog.PNG' style='width:600px'>

- Kasutajad saabuvad TARA-sse klientrakendustest.
- TARA esilehel valib kasutaja autentimismeetodi.
- Järgneb autentimisdialoog, vastavalt valitud meetodile.
- Autentimisdialoogi (5, kui pangalingid võtta eraldi, siis 10 erinevat dialoogi) jõudlus sõltub nii TARA kui ka välisteenuste (RIA eIDAS konnektorteenus ja selle taga olev välisriikide eIDAS-taristu) jõudlusest.
- Autentimisdialoog võib päädida kolme olekusse:
  - Kasutaja saadetakse (autenditult) klientrakendusse tagasi
  - Autentimisel tekkis viga; Kasutajale esitatakse veateade
  - Kasutaja katkestab autentimise (lahkub)
- Veateate saamisel on võimalik tagasi pöörduda TARA esilehele
- TARA esilehelt on võimalik tagasi pöörduda klientrakendusse ilma autentimata (kui klientrakendus on nii seadistatud).

Need on kasutaja liikumisteed TARAs.

## Jõudluseesmärgid

Jõudluseesmärgiks on püstitatud: TARA peab suutma teenindada 100 kasutajat/s.

See tähendab:
- kasutajate voogu, kus iga sekund saabub 100 uut kasutajat; üheaegselt võib süsteemis olla kuni 30 000 kasutajat (arvestusega, et kasutaja autentimiseks kulub kuni 5 minutit)
- adekvaatseid lehelaadimisaegu (praktiliselt silmapilkselt)
- praktiliselt silmapilkseid töötlusaegu - selles osas, mis ei sõltu välisteenuste jõudlusest
- adekvaatset kasutaja teavitamist välisteenuste ebaadekvaatse jõudluse korral.


 


