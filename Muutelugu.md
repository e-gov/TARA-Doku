---
permalink: Muutmine
---

# Teenuse muutmine
{: .no_toc}

- TOC
{:toc}

Eesmärk on pakkuda asutustele stabiilset teenust, nii, et  klientrakendustes muudatusi oleks võimalikult vähe või üldse mitte. Samas on kavas aeg-ajalt teenusesse lisada klientidele väärtust lisavaid uusi omadusi, nt täiendavaid autentimismeetodeid. Teenuse muutmine võib olla vajalik kasutatavuse, käideldavuse, turvalisuse jms omaduste parandamiseks. Samuti ei ole välistatud teenuse muutmine vigade parandamiseks. 

Teenuse muudatus võib tähendada nii protokolli, tarkvara kui ka dokumentatsiooni muudatust.

___"Katkitegev muudatus"___ on selline, mis nõuab või võib nõuda muudatust klientrakenduses. Katkitegevaid muudatusi minimeeritakse. Katkitegevast muudatusest antakse asutustele varakult teada ja lepitakse kokku üleminekukava.

___Reliis___ on teenuse uue versiooni avaldamine, sisaldades:
- muudatuste kokkuvõtte (_release notes_) avaldamist teenuse dokumentatsiooni veebilehel (käesolev leht)
- teenust kasutavate asutuste teavitamist; seejuures väikeste, teenuse siseehitust või kasutamise väheolulisi detaile puudutavatest muudatustest ei pea teavitama
- uue versiooni paigaldamist test- ja seejärel toodanguteenusesse. 

___Üleminekukavas___ detailiseeritakse uuele suurversioonile ümberlülitumise tehniline ja ajalike korraldus, sh testimine ja tagasilülitamine. 

Muudatuste markeerimiseks teenus versioneeritakse.

___Versiooninumbrite süsteem___. Järgime semantilise versioneerimise põhimõtet [Semver]:

|        | näide      | tähendus       |
|--------|------------|----------------|
| suurversioon | 2.0, 3.0 jne | toob muudatusi, mis nõuavad klientrakenduse  ümbertegemist; teiste sõnadega, tagasiühilduvus ei ole tagatud |
| väikeversioon | 1.1, 1.2 jne | lisab uusi võimalusi - nt uue  autentimismeetodi, kuid klientrakendus ei ole kohustatud uut võimalust kasutama; klientrakendus, mis ei soovi uusi võimalusi kasutada, ei pea midagi muutma; teiste sõnadega - tagasiühilduvus on tagatud |
| paik e pisiparandus | 1.0.1, 1.0.2 jne | väike täiendus, mis ei lisa uusi võimalusi ega nõua klientrakenduse tarkvara muutmist |

Versiooninumber näidatakse teenuse kasutajaliideses.

