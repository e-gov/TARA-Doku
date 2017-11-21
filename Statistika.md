---
permalink: Statistika
---

# TARA statistika
{: .no_toc}

- TOC
{:toc}

## Vajadus

Statistikavajadus tuleneb vajadusest:
- 1) tagada deklareeritud teenustase (SLA)
- 2) pidada väliste, tasuliste teenuste (DigiDocService) tarbimise arvestust
- 3) pidada arvestust teenuse kasutamise kohta klientide lõikes.

Statistika võib olla ka kasulik ka teenuse turvamisel.

Vajame statistilisi näitajaid (periood - kuu):
- kasutusstatistika
  - autentimiste arv
    - kokku
    - klientide lõikes (klient TARA mõistes on Eesti e-teenuse omanik-asutus)
      - klientrakenduste lõikes (kliendil võib olla mitu klientrakendust)
    - piiriülene/siseriiklik lõikes
      - välisriikide lõikes
    - autentimismeetodite lõikes
  - edukate autentimiste osakaal
- teenustaseme statistika
  - välisriigi teenuse maasoleku tõttu ebaõnnestunud autentimiste arv
  - välisriigi teenuste ülevaloleku protsent
  - Eesti TARA-välise teenuse maasoleku tõttu ebaõnnestunud autentimiste arv

Kõike võib-olla kohe teostada ei jõua, kuid järk-järgult. Piiriülene statistika muutub aktuaalseks siis, kui lisame eIDAS-autentimise.

Huvi pakuks ka palju siis erinevaid välismaalasi teenuses autenditakse? See nõuaks eIDAS-identifikaatorite andmebaasi pidamist. Keerukas ja oht, et problemaatiline andmekaitse seisukohalt. Kui, siis teises järjekorras.

Ülalesitatu ei sisalda SSO statistikat. SSO toob kaasa täiesti uued küsimused: Kui palju SSO-d kasutatakse? Mitmesse teenusesse SSO-ga sisse logitakse? Kes SSO-d kasutab?

## Tehniline lahendus

Alternatiivid:
- 1) majasisese ELK-stacki baasil
- 2) programmeerida eraldi statistikaotstarbeline logi + statistika arvutamise skript (majasisene)
- 3) väline teenus, _in-premise_ paigaldus - Pivic
- <strike>4) Google Analytics.</strike>

Statistika tootmiseks tuleks võimalusel kasutada oma lahendust. Google Analyticsile tuginemine, eriti kui teenust hakatakse laialdaselt kasutama, sh ühekordse sisselogimise režiimis, tekitab riski, et autentimisandmestik, kuigi sealt on isikukoodid ja nimed eemaldatud, võimaldab muude andmetega kombineerides teha järeldusi, mille võimalikku kasu või kahju me ei oska hinnata.

## TARA statistikalahendus. Spetsifikatsioon

20\.11.2017, Priit Parmakson

1\.	Statistikalogi
1\.1\.	Statistikalogi eesmärk on koguda andmeid RIHA kasutusstatistika koostamiseks.
1\.2\.	Statistikalogi salvestatakse eraldi logifailis.
1\.3\.	Statistikalogi hõlmab nii siseriiklikku kui ka piiriülest autentimist.
1\.4\.	Logisse ei salvestata isikuandmeid.
1\.5\.	Logi ei toimetata RIA taristust välja.
1\.6\.	Statistika tootmise kõrval võib logi kasutada ka teenuse tõrgete põhjuste otsimisel ja turvajuhtumite uurimisel.
1\.7\.	Logi säilitatakse 1 aasta.
1\.8\.	Logikirje formaat:

| nr	| väli |	selgitus	| formaat	| näide |
|-----|------|------------|---------|-------|
| 1.	| toimingu aeg	| autentimistoimingu alustamise aeg.	| ISO 8601, YYYY-MM-DDThh:mm:ssTZD	| 2017-11-20T16:49:42+02:00 |
| 2.	| klientrakendus	| klientrakenduse nimi, client id OpenID Connecti mõistes.	| sõne	| KalanduseIS |
| 3.	| asutus	| klientrakenduse omaniku - asutuse registrikood.	| registrikood	| 70001231 |
| 4.	| autentimismeetod	| eID, mID jne. Piiriülese autentimise koodid lisame siis, kui eIDASe osa hakkame tegema.	| sõne	| eID |
| 5.	| toimingu tulemus	| sõne OK (autentimine edukas) või NOK (autentimine ebaedukas)	| OK |
| 6.	| ebaedu põhjus	| autentimistoimingu ebaõnnestumise põhjus, kui on teada; õnnestumise korral jäetakse väli tühjaks.	| põhjuse kood (sõnena). Koodid vt [1]. | mID.15 | 

2\.	Autentimistoiming
2\.1\.	Autentimistoimingu alguseks loeme hetke, kus TARA teenusesse saabunud kasutaja valib autentimismeetodi.
2\.2\.	Autentimistoiming lõpeb kas edukalt või ebaedukalt.
2\.3\.	Autentimistoimingu loeme lõppenuks edukalt siis, kui klientrakendusele väljastatakse identsustõend.
2\.4\.	Kõigil muudel juhtudel loetakse autentimistoiming lõppenuks ebaedukalt.
3\.	TARA teenuse statistiline aruanne
3\.1\.	TARA teenuse statistiline aruanne esitab teenuse kaudu teostatud autentimistoimingute arvu ajaperioodil:
3\.1\.1\.	klientrakenduste lõikes
3\.1\.2\.	piiriülene/siseriiklik lõikes
3\.1\.3\.	välisriikide lõikes
3\.1\.4\.	autentimismeetodite lõikes
3\.1\.5\.	autentimitoimingu õnnestumise/ebaõnnestumise lõikes
3\.1\.6\.	ajalises lõikes.
3\.2\.	Statistikaperioodiks on 1 kuu.
3\.3\.	Statistiline aruanne koostatakse kuu statistikalogi faili põhjal, spetsiaalse skriptiga.
3\.4\.	Statistiline aruanne vormistatakse inimloetava, UTF-8 vormingus tekstifailina. Kujundus ei ole vajalik.

[1] https://confluence.ria.ee/display/TARA/TARA+veateated+ja+kasutajale+antavad+juhised. 

----

_Viimati muudetud: 21.11.2017_

