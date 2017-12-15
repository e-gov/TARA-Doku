---
permalink: Muutmine
---

# Teenuse muudatused
{: .no_toc}

- TOC
{:toc}

___Stabiilsus___. Eesmärk on pakkuda stabiilset teenust &mdash; nii, et teenusega liidestatud klientrakenduses oleks kasutamise perioodil muudatusi vaja teha võimalikult vähe või üldse mitte.

___Teenuse muudatus___. Samas on kavas aeg-ajalt teenusesse lisada klientidele väärtust lisavaid uusi omadusi, nt täiendavaid autentimismeetodeid. Teenuse muutmine võib olla vajalik kasutatavuse, käideldavuse, turvalisuse jms omaduste parandamiseks. Samuti ei ole välistatud teenuse muutmine vigade parandamiseks. Teenuse muudatus võib hõlmata nii protokolli, tarkvara kui ka dokumentatsiooni muudatust.

___"Katkitegev muudatus"___ on selline, mis nõuab või võib nõuda muudatust klientrakenduses. Katkitegevaid muudatusi võimaluste piires minimeeritakse. Katkitegevast muudatusest antakse asutustele varakult teada ja lepitakse kokku üleminekukava.

___Reliis___ on teenuse uue versiooni avaldamine, sisaldades:
- muudatuste kokkuvõtte (_release notes_) avaldamist teenuse dokumentatsiooni veebilehel (käesolev leht)
- teenust kasutavate asutuste teavitamist; seejuures väikeste, teenuse siseehitust või kasutamise väheolulisi detaile puudutavatest muudatustest ei pea teavitama
- uue versiooni paigaldamist test- ja seejärel toodanguteenusesse. 

___Üleminekukavas___ detailiseeritakse uuele suurversioonile ümberlülitumise tehniline ja ajaline korraldus, sh testimine ja tagasilülitamine. 

Muudatuste markeerimiseks teenus versioneeritakse.

___Versiooninumbrite süsteem___. Järgime semantilise versioneerimise põhimõtet [Semver]:

| number | näide      | tähendus       |
|:------:|------------|----------------|
| suurversioon | 2.0, 3.0 jne | toob muudatusi, mis nõuavad muudatust klientrakenduses; teiste sõnadega, tagasiühilduvus ei ole tagatud |
| väikeversioon | 1.1, 1.2 jne | lisab uusi võimalusi &mdash; nt uue  autentimismeetodi, kuid klientrakendus ei ole kohustatud uut võimalust kasutama; klientrakendus, mis ei soovi uusi võimalusi kasutada, ei pea midagi muutma; teiste sõnadega &mdash; tagasiühilduvus on tagatud |
| paik e pisiparandus | 1.0.1, 1.0.2 jne | väike täiendus, mis ei lisa uusi võimalusi ega nõua klientrakenduse tarkvara muutmist |

Versiooninumber näidatakse teenuse kasutajaliideses.

