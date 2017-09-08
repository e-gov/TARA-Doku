---
permalink: /
---

# RIA autentimisteenus (TP-192)
{: .no_toc}

- TOC
{:toc}

## Ülevaade
RIA autentimisteenus on keskne autentimisteenus, millega asutuse e-teenus saab autentida mobiil-ID kasutaja.

RIA autentimisteenus avatakse `TBD`. Teenusega liidestumise tehnilised tingimused ja juhend avaldatakse `TBD`. Liidestuste testimise keskkond avatakse `TBD`.

Autentimismeetodina toetatakse esialgu mobiil-ID-d. Hiljem RIA autentimisteenuse võimalused laienevad: lisanduvad ühekordne sisselogimine (SSO) – asutustele, kes soovivad kasutada; lisatakse autentimismeetodeid (eIDAS, ID-kaart, SmartID, pangalingid).

E-teenus liidestatakse teenusega OpenID Connect protokolli kohaselt. 

RIA autentimisteenus on eriti suunatud asutustele, kes soovivad kiiresti pakkuda oma klientidele mobiil-ID-ga autentimist, kuid ei soovi ise DigiDoc teenusega liidestumist läbi teha.

## Teenuse arenduskava

| teetähis | tööd | tulemus | orienteeruv, minimaalne ajakava |
|----------|------|---------|--------------|
| MVP/PoC  | Arendaja: 1) paneb üles OpenID Connect serveri; 2) koostab testimiseks vajaliku makettrakenduse ja 3) mobiil-ID-ga autentimise komponendi. | Töötav tarkvara, mis realiseerib eesmärgiks seatud kasutusloo; käideldavus v-o madal; dokumentatsioon v.o mittetäielik. | 2 nädalat |
| Tarkvara toodanguvalmis | RIA ja CGI koostöös: 1) testivad põhjalikult; 2) koostavad SLA ja teevad vajalikud tööd selle saavutamiseks (klaster); 3) viimistlevad dokumentatsiooni | Tarkvara testitud, tootmisse paigaldamiseks valmis, korralik dokumentatsioon | 2 nädalat |
| Asutustele suunatud kommunikatsiooni-pakett valmis | RIA: 1) avaldab teenuse – teabematerjal + liidestumisjuhend; 2) edaspidi toetab liidestujaid tööde kavandamisel | Liitumiseks vajalik teave sihtrühmale edastatud | 1 nädal |
| Testteenus avatud | RIA: 1) paneb üles keskkonna, mille vastu liidestujad saavad oma arendusi testida; 2) edaspidi teenindab liidestujaid liideste testimisel (konf-ne jms) | Liitujad saavad oma liideseid testteenuse vastu testida | 1 nädal |
| Teenuse avamine toodangus | RIA: 1) avab teenuse toodangukeskkonna;
2) edaspidi laseb teenusekasutajad (pärast testide läbimist) toodangusse | | 1 nädal |

## Arhitektuur

![](img/ARH-01.PNG)
<img src=''>

Esimeses järgus teostatavad komponendid ja kasutusvoog on joonisel näidatud rõhutatult. Joonisel on kujutatud ka  teenuse EIDAS-autentimisega laiendamise võimalus.

## Esimesed arendustööd
1)	OpenID Connect serveri ülespanek (joonisel „OpenID Connect liidese haldur“)<br>
valida sobiv teek, võiks olla Java-põhine; otsus kooskõlastada Tellijaga. Seadistada ja täiendada teeki nii, et tarkvara teostab esmase autentimise kasutusloo [1].

2)	Makettrakenduse loomine<br>
koostada RIA rakendus, mis etendab autentimisteenust kasutavat Eesti e-teenust. Makettrakenduse eesmärk on testida loodavat RIA autentimisteenust. Selleks valida sobiv OpenID Connect kliendi teek. Ei pea olema Java.

3)	mobiil-ID autentija loomine<br>
teostada komponent, mis teostab Eesti mobiil-ID-ga autentimise. Siduda mobiil-ID autentija  ja OpenID Connect liidese haldur tervikuks. 

Arvestada edasise tööde järjekorraga, vt joonis ja [1]: 1) kasutusloo autentimise olemasolu kontroll teostamine; 2) kasutusloo väljalogimine teostamine; 3) komponentide Seansihaldur ja selle koosseisus Seansihoidla teostamine; 4) komponendi Isikutuvastusportaal teostamine (võimaldab kasutajal valida mitme autentimismeetodi vahel); 5) liidestamine eIDAS konnektorteenusega (vajalik välismaalaste autentimisel).

## Arenduskorraldus
- Skype (eraldi vestlused sisemiselt ja arendajaga)
- tööülesanded JIRA-s
- tulemkood - ja dokumentatsioon: GitHub (varundamisega)

## Kood ja dokumentatsioon

| nimetus | kirjeldus | koostaja  | seis |
|---------|-----------|-----------|------|
| Teenuse kirjeldus (ärivaates) |  |  |  |
| Tehniline kirjeldus | Tarkvara spetsifikatsioon, sisaldab: arh-ijoonis, kasutusloo või -lugude kirjeldust (lähtudes [1]), tarkvara oluliste mittefunktsionaalsete omaduste kirjeldust - teave kasutatud teekide kohta, logimise lahenduse kirjeldus, hinnang tööjõudlusele |
| Testistrateegia | kirjeldab, kuidas ja mida testitakse, joonis testimise setup-i kohta |  |  |
| Testilood   | | | |
| Testiraport | | | |
| Kood        | | | |
| Paigaldusjuhend | (kirjeldab ka makettrakenduse paigaldamist) | | |
| SLA         | nii test-kui ka toodanguteenusele | |
| Juhend liidestujale (ärivaates) | | |
| Tehniline juhend liidestajale | | |

## Viited

[1] Riigi Infosüsteemi Amet. RIA SSO autentimisteenuse kavand. 18 lk.
[2] [TP-93 RIA autentimisteenus](https://jira.ria.ee/browse/TP-93). RIA teenuseportfelli kirje. [mitteavalik]