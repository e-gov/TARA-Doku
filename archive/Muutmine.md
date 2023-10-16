---
permalink: Muutmine
---

# Teenuse muudatused
{: .no_toc}

- TOC
{:toc}

## 1 Eesmärgid

___Stabiilsus___. Eesmärk on pakkuda stabiilset teenust &mdash; nii, et teenusega liidestatud klientrakenduses oleks kasutamise perioodil muudatusi vaja teha võimalikult vähe või üldse mitte.

## 2 Teenusemuudatused

___Teenusemuudatus___. Samas on kavas aeg-ajalt teenusesse lisada klientidele väärtust lisavaid uusi omadusi, nt täiendavaid autentimismeetodeid. Teenuse muutmine võib olla vajalik kasutatavuse, käideldavuse, turvalisuse jm omaduste parandamiseks. Samuti ei ole välistatud teenuse muutmine vigade parandamiseks. Teenuse muudatus võib hõlmata nii liides spetsifikatsiooni (protokolli), tarkvara kui ka dokumentatsiooni muudatust.

___"Katkitegev muudatus"___ on selline, mis nõuab või võib nõuda muudatust klientrakenduses. Katkitegevaid muudatusi võimaluste piires minimeeritakse.

__Teenusemuudatustest teavitamine__. Muudatusest teavitatakse teenust kasutavaid asutusi. Katkitegeva muudatuse korral antakse asutustele varakult teada ja lepitakse kokku üleminekukava. Muudatuse kokkuvõte avaldatakse teenuse dokumentatsioonis (käesoleval veebilehel).

___Üleminekukavas___ detailiseeritakse teenuse tarkvara uuele suurversioonile (vt allpool) ümberlülitumise tehniline ja ajaline korraldus, sh testimine ja varuplaanina &mdash; tagasilülitamine. 

## 3 Tarkvaramuudatused

___Tarkvaramuudatus___ on muudatus TARA teenust teostavas, RIA taristusse paigaldatud tarkvaras. Iga tarkvaramuudatus, nt teenuse siseehitust või kasutamise väheolulisi detaile puudutav, ei too kaasa teenusemuudatust.

___Reliis___ on teenuse tarkvara uue versiooni avaldamine ja paigaldamine RIA taristus, test- ja seejärel toodanguteenuse teostamiseks.

___Tarkvaramuudatuste kokkuvõte___, (_release notes_), avaldatakse TARA tarkvararepodes. Tarkvaramuudatuste kokkuvõtted on mõeldud eelkõige TARA teenusepakkujale ja arendajatele endile.

Muudatuste markeerimiseks tarkvara versioneeritakse.

___Versiooninumbrite süsteem___. Järgime semantilise versioneerimise põhimõtet [Semver]:

| number | näide      | tähendus       |
|:------:|------------|----------------|
| suurversioon | 2.0, 3.0 jne | toob muudatusi, mis nõuavad muudatust klientrakenduses; teiste sõnadega, tagasiühilduvus ei ole tagatud |
| väikeversioon | 1.1, 1.2 jne | lisab uusi võimalusi &mdash; nt uue  autentimismeetodi, kuid klientrakendus ei ole kohustatud uut võimalust kasutama; klientrakendus, mis ei soovi uusi võimalusi kasutada, ei pea midagi muutma; teiste sõnadega &mdash; tagasiühilduvus on tagatud |
| paik e pisiparandus | 1.0.1, 1.0.2 jne | väike täiendus, mis ei lisa uusi võimalusi ega nõua klientrakenduse tarkvara muutmist |

Tarkvara versiooninumber näidatakse teenuse kasutajaliideses.
