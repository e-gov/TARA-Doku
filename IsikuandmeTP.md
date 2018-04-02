---
permalink: Isikuandmed
---

# Isikuandmete töötluse poliitika

Käesolevas dokumendis kehtestatakse RIA autentimisteenustes [1], edaspidi "teenused", rakendatavad isikuandmete töötluse konkreetsed meetmed. 

## 1 Mõisted

_kasutaja_, füüsiline isik, kes suunatakse Eesti või välismaa e-teenusest RIA autentimisteenusesse isikusamasuse tuvastamisele.

## 2 Ulatus

/2.1 Poliitika rakendub:

1. RIA eIDAS konnektorteenusele
2. RIA autentimisteenusele
3. TARA autentimisteenuses TARA
4. EL piiriülese autentimistaristu Eesti sõlmele ("eIDAS Node")

## 3 Töödeldavad isikuandmed

/3.1 Teenustes töödeldakse järgmisi andmeid autenditavate isikute kohta:

1. isikut identifitseerivad andmed
  - kasutaja isikukood vm isiku identifikaator
  - isiku ees- ja perekonnanimi
  - isiku riik
  - muud isikuandmed, eIDASE terminoloogias "atribuudid", vastavalt eIDAS määruse alusel väljatöötatud eIDAS tehnilise spetsifikatsiooniga [2]. Eestikeelne nimekiri eIDAS atribuutidest on kättesaadav TARA tehnilises kirjelduses [3].

2. autentimistoimingu andmed
  - kuupäev ja kellaaeg
  - klientrakendus, kust kasutaja

## 4 Andmete väljastamine

/4.1 Ülalnimetatud andmeid väljastatakse teenustega liidestatud e-teenustele. Siseriiklikult on nendeks asutuste e-teenuseid pakkuvad infosüsteemid. Piiriüleselt - EL teiste liikmesriikide eIDAS sõlmed.

/4.2 Kasutajale on autentimise tulemus (sisse logitud või mitte) nähtav sirvikus. Kasutajale ei ole praegu loodud elektroonilist võimalust tutvuda (iseteeninduse põhimõttel) oma autentimisajalooga. Vajadusel saab vastavatele kasutaja päringutele vastata teenusehaldur käsitsi.

/4.3 Eesti eID kasutaja andmete saatmiseks välisriiki küsitakse kasutaja nõusolekut (Eesti autentimisteenus). 

## 5 Turvalogi

/5.1 Turvalogi eesmärk on:

- rakenduste väärkasutamise ärahoidmine ja turvaintsidentide uurimise jaoks andmete kogumine
- teenustega liidestatud e-teenuste omanike s.t asutuste poolt raporteeritud tehniliste probleemide põhjuste väljaselgitamine
- kasutajate võimalike pöördumiste (teated võimalike turvaprobleemide või tehniliste rikete) menetlemine 

/5.2 Turvalogis logitakse autentimistoimingute andmed, sh autenditu isikute andmed.

/5.3 Turvalogile on juurdepääs vajaduspõhine, ainult teenuse käitamisega otseselt seotud süsteemi- ja teenusehalduritele.

/5.4 Turvalogide andmeid ei edastata teistesse süsteemidesse ega kasutata muudel eesmärkidel, kui kasutaja isikusamasuse tuvastamine konkreetse e-teenuse pöördumise kontekstis (välja arvatud kuriteo tõkestamise või uurimise vajaduse korral, vastavalt õigusele).

/5.5 Turvalogisid säilitatakse üks aasta.

## 6 Statistikalogi

/6.1 vStatistikalogi eesmärk on teenuste kasutamise kohta statistika tootmine, teenuse juhtimise ja edasiarendamise eesmärgil.

/6.2 Statistikalogisse kogutakse andmed autentimistoimingute kohta, kuid ei koguta isikut identifitseerivate andmeid. S.t kogutakse autentimistoimingu kuupäev ja kellaaeg, autentimismeetod, autentimise tulemus (autenditud või mitte), e-teenus, kus kasutaja autentimisele suunati; kasutaja enda kohta aga ainult kasutaja riik; kasutaja nime ega muid atribuute ei koguta.

/6.3 Statistikalogi põhjal koostatakse ja avalikustatakse statistilisi aruandeid, milles isikuandmed on agregeeritud ja konkreetseid isikuid ei ole võimalik tuvastada.  

## Viited

[1] RIA autentimisteenused, [https://www.ria.ee/ee/autentimisteenused.html](https://www.ria.ee/ee/autentimisteenused.html).

[2] eIDAS Technical specification, [https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS+Profile](https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS+Profile)

[3] Autentimisteenus TARA. Tehniline kirjeldus [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#3-autentimisp%C3%A4ring](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#3-autentimisp%C3%A4ring).
