---
permalink: Muutelugu
---

# Muutelugu

TARA autentimisteenuse arendamisel lähtume semantilise versioneerimise konventsioonist [Semver]:
- _suur versioon_ (2.0, 3.0 jne) võib sisaldada klientrakendustes ümbertegemist nõudvaid muudatusi s.t tagasiühilduvus ei ole tagatud
- _väike versioon_ (1.1, 1.2 jne) lisab uusi võimalusi - nt uusi autentimismeetodeid, kuid klientrakendustes, mis uusi võimalusi ei kasuta, ei ole vaja midagi muuta - tagasiühilduvus on tagatud
- _paik_ e pisiparandus (1.0.1, 1.0.2 jne) on teenuse dokumentatsiooni või tarkvara väike täiendus, mis ei lisa uusi võimalusi ega nõua klientrakenduse tarkvara muutmist.

Muudatustest antakse teenusekasutajatele aegsasti teada. Plaanis on teenust arendada tagasiühilduvalt ja järk-järgult, lähtudes kasutajate vajadustest.

| versioon, kuupäev | muudatus      |
|-------------------|---------------|
| v 0.7, 25.09.2017   | ettevalmistav tööversioon |

