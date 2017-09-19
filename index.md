---
permalink: /
---

![](img/LOVID.png)

TARA on turvaline autentimisteenus, millega asutuse e-teenus saab autentida mobiil-ID kasutaja.
TARA teenust pakub RIA.

E-teenus kasutab TARA OpenID Connect protokolli kohaselt. Edaspidi TARA võimalused laienevad: ühekordne sisselogimine, täiendavad autentimismeetodid, sh piiriülene eIDAS-autentimine jm. 

## Kood ja dokumentatsioon

- [TARA-Server](https://github.com/e-gov/TARA-Server) (teenuse server)
- [TARA-Client](https://github.com/e-gov/TARA-Client) (makettrakendus teenuse testimiseks)
- [TARA-Doku](https://github.com/e-gov/TARA-Doku) (teenuse dokumentatsioon)

| nimetus, asukoht             |  seis     |
|------------------------------|-----------|
| 1 [Teabeleht](Teabeleht)       | kavand    |
| 2 [Ärikirjeldus](Arikirjeldus) | kavand    |
| 3 [Tehnilised tingimused ja liidestamise juhend](Juhend) | alustatud |
| 4 [Tehniline kirjeldus](TehnilineKirjeldus) | alustatud   |
| 5 [Sõnastik](Sonastik) | töös |  |
| 6 [Viited](Viited)   | Töös |   |
| 7 [SLA]()                      |            |
| 8 [Paigaldusjuhend]()          |            |
| 9 [Haldusjuhend]()   |            |
| 10 [Testistrateegia](Testistrateegia)  | alustatud           |
| 11 [Testilood](Testilood)                  |  alustatud      |
| 12 [Testiraport]()                |            |

***Tehniline kirjeldus*** - tarkvara spetsifikatsioon, sisaldab: arh-ijoonist, paigaldusjoonist, kasutusloo või -lugude kirjeldust, tarkvara oluliste mittefunktsionaalsete omaduste kirjeldust - teave kasutatud teekide kohta, logimise lahenduse kirjeldus, hinnang tööjõudlusele

***Testistrateegia*** - kirjeldab, kuidas ja mida testitakse, joonis testimise setup-i kohta

***SLA*** - nii test-kui ka toodanguteenusele

## Teekaart

<img src='img/TEEKAART.PNG' style='width: 600px;'>

Töös, kaalumisel ja horisondil on tööpaketid:

|    tööpakett | selgitus  | maht (orientiir) |
|--------------|-----------|------------------|
| ***"m-ID"*** | Tarkvaraarendus: 1) OpenID Connect serveri kohandamine ja ülespanek; 2) testeesmärgilise makettrakenduse koostamine; 3) mobiil-ID-ga autentimise komponendi teostus. Arenduse osana: dokumentatsiooni koostamine ja tarkvara testimine. Teenuse käitamiseks vajaliku dokumentatsiooni koostamine (vt detailsemalt allpool). | 4-6 nädalat |
| ***"ID-kaart"*** | ID-kaardiga autentimise lisamine, koos dokumentatsiooni vastava täiendamisega ja testimisega. | 2 nädalat |
| ***"eIDAS"*** | eIDAS autentimise lisamine, sh liidestamine RIA eIDAS konnektorteenusega; koos dokumentatsiooni vastava täiendamisega ja testimisega. | 2 nädalat |
| ***"Smart-ID"*** | Smart-ID autentimise lisamine; koos dokumentatsiooni vastava täiendamisega ja testimisega. | 2 nädalat |
| ***"Pangalingid"*** | Pangalink-autentimise lisamine; koos dokumentatsiooni vastava täiendamisega ja testimisega. (3 panka). | 2 nädalat |
| ***"Uuring"*** | ühekordse sisselogimise (Single Sign-On, SSO) teenusele lisamise vajalikkuse ja teostatavuse uuring. Sisaldab _Proof of Concept" (PoC) lahenduse teostust ja selle hindamist. | 2 nädalat |
| ***"SSO"*** | ühekordse sisselogimise (SSO) teenusele lisamine. Sisaldab uuringus tehtud PoC lahenduse viimistlemist, dokumenteerimist ja testimist. | 2 nädalat |
| ***"Lisaatribuudid"*** | teenuse laiendamine täiendavate atribuutide pakkumisega. Nt päring äriregistrisse esindusõiguse kindlakstegemiseks, päring Rahvastikuregistrisse, võimalik, et päring Personali- ja palgaandmete andmekogusse (SAP-i). | 2 nädalat |

## I arendusjärk (töös)

| teetähis | tööd | tulemus | orienteeruv, minimaalne ajakava |
|----------|------|---------|--------------|
| MVP/PoC  | Arendaja: 1) paneb üles OpenID Connect serveri; 2) koostab testimiseks vajaliku makettrakenduse ja 3) mobiil-ID-ga autentimise komponendi. | Töötav tarkvara, mis realiseerib eesmärgiks seatud kasutusloo; käideldavus v-o madal; dokumentatsioon v.o mittetäielik. | 2 nädalat |
| Tarkvara toodanguvalmis | RIA ja CGI koostöös: 1) testivad põhjalikult; 2) koostavad SLA ja teevad vajalikud tööd selle saavutamiseks (klaster); 3) viimistlevad dokumentatsiooni | Tarkvara testitud, tootmisse paigaldamiseks valmis, korralik dokumentatsioon | 2 nädalat |
| Asutustele suunatud kommunikatsiooni-pakett valmis | RIA: 1) avaldab teenuse – teabematerjal + liidestumisjuhend; 2) edaspidi toetab liidestujaid tööde kavandamisel | Liitumiseks vajalik teave sihtrühmale edastatud; kommunikatsioonipaketti on vähemalt ühe asutuse peal testitud | 1 nädal |
| Testteenus avatud | RIA: 1) paneb üles keskkonna, mille vastu liidestujad saavad oma arendusi testida; 2) edaspidi teenindab liidestujaid liideste testimisel (konf-ne jms) | Liitujad saavad oma liideseid testteenuse vastu testida | 1 nädal |
| Teenuse avamine toodangus | RIA: 1) avab teenuse toodangukeskkonna; 2) edaspidi laseb teenusekasutajad (pärast testide läbimist) toodangusse | | 1 nädal |

Arendustööd:
1)	OpenID Connect serveri ülespanek (joonisel „OpenID Connect liidese haldur“)<br>
valida sobiv teek, võiks olla Java-põhine; otsus kooskõlastada Tellijaga. Seadistada ja täiendada teeki nii, et tarkvara teostab esmase autentimise kasutusloo [kavand].

2)	Makettrakenduse loomine<br>
koostada RIA rakendus, mis etendab autentimisteenust kasutavat Eesti e-teenust. Makettrakenduse eesmärk on testida loodavat RIA autentimisteenust. Selleks valida sobiv OpenID Connect kliendi teek. Ei pea olema Java.

3)	mobiil-ID autentija loomine<br>
teostada komponent, mis teostab Eesti mobiil-ID-ga autentimise. Siduda mobiil-ID autentija  ja OpenID Connect liidese haldur tervikuks. 

Arvestada edasise tööde järjekorraga, vt joonis ja [kavand]: 1) kasutusloo autentimise olemasolu kontroll teostamine; 2) kasutusloo väljalogimine teostamine; 3) komponentide Seansihaldur ja selle koosseisus Seansihoidla teostamine; 4) komponendi Isikutuvastusportaal teostamine (võimaldab kasutajal valida mitme autentimismeetodi vahel); 5) liidestamine eIDAS konnektorteenusega (vajalik välismaalaste autentimisel).

## Arenduskorraldus

TARA Confluence-is:<br>
[https://confluence.ria.ee/display/TARA](https://confluence.ria.ee/display/TARA) 

TARA JIRA-s:<br>
[https://jira.ria.ee/browse/DD4J-88](https://jira.ria.ee/browse/DD4J-88) 

TARA Skype-is:<br>
Autentimisteenuse arendus

-----

[Teekaardi lähtejoonis](https://docs.google.com/drawings/d/1t-SfFV4VYMjPjjEav-ZL4TPNgYZE1Ko9XU6Y94Ypbw8/edit) (Google Docs)


