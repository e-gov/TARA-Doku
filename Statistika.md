---
permalink: Statistika
---

# TARA statistika

## Vajadus

Statistikavajadus tuleneb vajadusest:
- 1) tagada deklareeritud teenustase (SLA)
- 2) pidada väliste, tasuliste teenuste (DigiDocService) tarbimise arvestust
- 3) pidada arvestust teenuse kasutamise kohta klientide lõikes.

Statistika võib olla ka kasulik ka teenuse turvamisel.

Vajame statistilisi näitajaid (periood - kuu):
- autentimiste arv
  - kokku
  - klientide lõikes (klient TARA mõistes on Eesti e-teenuse omanik-asutus)
    - klientrakenduste lõikes (kliendil võib olla mitu klientrakendust)
  - piiriülene/siseriiklik lõikes
    - välisriikide lõikes
  - autentimismeetodite lõikes
- edukate autentimiste osakaal.

Huvi pakuks ka palju siis erinevaid välismaalasi teenuses autenditakse? See nõuaks eIDAS-identifikaatorite andmebaasi pidamist. Keerukas ja oht, et problemaatiline andmekaitse seisukohalt. Kui, siis teises järjekorras.

## Lahendus

Statistika tootmiseks tuleks võimalusel kasutada oma lahendust. Google Analyticsile tuginemine, eriti kui teenust hakatakse laialdaselt kasutama, sh ühekordse sisselogimise režiimis, tekitab riski, et autentimisandmestik, kuigi sealt on isikukoodid ja nimed eemaldatud, võimaldab muude andmetega kombineerides teha järeldusi, mille võimalikku kasu või kahju me ei oska hinnata.

----

_Viimati muudetud: 15.11.2017_

