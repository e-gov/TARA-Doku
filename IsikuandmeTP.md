---
permalink: Isikuandmed
---

# Andmekaitsetingimused

Käesolevas dokumendis kirjeldame milliseid isikuandmeid ja mis eesmärgil RIA autentimisteenustes ([1], edaspidi "teenused") töötlema. Samuti anname teavet mis meetmeid kasutaja eraelu privaatsuse kaitseks rakendame. 

### 1 Rakendusala

Tingimused rakenduvad:

1. TARA autentimisteenusele TARA
2. RIA autentimisteenusele
3. RIA eIDAS konnektorteenusele
4. EL piiriülese autentimistaristu Eesti sõlmele ("eIDAS Node").

### 2 Mõisted

_kasutaja_, füüsiline isik, kes suunatakse Eesti või välismaa e-teenusest RIA autentimisteenusesse isikusamasuse tuvastamisele.

Teisi mõisteid vt [sõnastikust](https://e-gov.github.io/TARA-Doku/Sonastik).

### 3 Autentimisandmed

3\.1 Teenustes töödeldakse kasutajate (autenditavate isikute) kohta järgmisi andmeid ("autentimisandmed"):

1. kasutajat identifitseerivad andmed
  - kasutaja isikukood vm isiku identifikaator
  - kasutaja ees- ja perekonnanimi
  - kasutaja riik
  - muud kasutaja isikuandmed, eIDAS-e terminoloogias "atribuudid", vastavalt eIDAS määruse alusel väljatöötatud eIDAS tehnilisele spetsifikatsioonile [2]. _Eestikeelne nimekiri eIDAS atribuutidest on kättesaadav TARA tehnilises kirjelduses [3]._

2. autentimistoimingu andmed
  - kuupäev ja kellaaeg
  - klientrakendus, kust kasutaja autentimisele suunati
  - autentimismeetod, sh pangalinki puhul ka pank; mobiil-ID puhul mobiilinumber
  - autentimise tulemus (autenditud või mitte).

3\.2 Teenuste haldamise eesmärgil kogutakse sisemiseks tarbeks ka liidestatud asutuste kontaktsisikute andmeid.

### 4 Andmete väljastamine

4\.1 Autentimisandmeid väljastatakse teenustega liidestatud e-teenusele. Siseriiklikult on nendeks asutuste e-teenuseid pakkuvad infosüsteemid; piiriüleselt - EL teiste liikmesriikide eIDAS sõlmed.

4\.2 Andmete väljastamisel lähtutakse isikuandmete töötlemise minimaalsuse põhimõttest. Väljastatakse minimaalsed autentimise fakti ja tuvastatud isikut identifitseerivad andmed. Näiteks, mobiil-ID-ga autentimisel ei väljastata e-teenusele kasutaja mobiilinumbrit, sest e-teenuse osutamiseks on otseselt vajalik ainult autentimise fakt, mitte millist mobiilinumbrit isik kasutab.

4\.3 Kasutajale on autentimise tulemus (sisse logitud või mitte) nähtav sirvikus.

4\.4 Kasutajale ei ole praegu loodud iseteeninduse põhimõttel toimivat elektroonilist võimalust tutvuda oma autentimisajalooga. Vajadusel saab vastavatele kasutaja päringutele vastata teenusehaldur käsitsi.

4\.5 Eesti eID kasutaja autentimisandmete saatmisel piiriülese eIDAS autentimistaristuga liitunud teise riiki küsitakse kasutaja nõusolekut (Eesti autentimisteenuses). 

4\.6 Andmeid võidakse väljastada õigustatud asutustele turvaintsidentide uurimiseks või kohtuliku uurimise tarbeks, seaduses ettenähtud korras.  

### 5 Turvalogi

5\.1 Teenuses logitakse autentimistoimingute andmed, sh autenditud kasutajate andmed.

5\.2 Logimise eesmärk on:

1. teenuse väärkasutamise, sh identiteedivarguste ja nende katsete, samuti küberrünnakute avastamise ja uurimise jaoks andmete säilitamine. _Identiteedivargus on näiteks see, kui keegi saab oma valdusse kodaniku ID-kaardi ja PIN-koodid ja kasutab e-teenuseid, esinedes kodanikuna. Identiteedivargus on kuritegu; selle avastamiseks ja lahendamiseks võib teenustesse sisselogimiste logist olla suur kasu._ 
2. tehniliste tõrgete avastamiseks ja kõrvaldamiseks andmete kogumine ja säilitamine. _Tehniline tõrge võib olla nii riist- kui ka tarkvara viga, võrguühenduse viga jms._
3. teenustega liidestatud e-teenuste omanike s.t asutuste poolt raporteeritud tehniliste probleemide põhjuste väljaselgitamine.
4. kasutajate võimalike pöördumiste (teated võimalike turvaprobleemide või tehniliste rikete kohta) menetlemine.

5\.3 Logile juurdepääs on rangelt vajaduspõhine. Ligi pääsevad ainult teenuse käitamisega otseselt seotud süsteemi- ja teenusehaldurid, vajadusel ka turvaintsidentide ja/või kohtuliku uurimisega tegelevad ametiisikud. Ka kodanikul on õigus pöörduda tema autentimiste logiga tutvumiseks, kuid palume arvestada, et praegu ei ole veel võimalust väljavõtte automaatseks väljastamiseks.

5\.4 Logi andmeid ei edastata teistesse süsteemidesse ega kasutata muudel eesmärkidel, kui kasutaja isikusamasuse tuvastamine konkreetse e-teenuse pöördumise kontekstis (välja arvatud kuriteo tõkestamise või uurimise vajaduse korral, vastavalt õigusele).

5\.5 Logisid säilitatakse üks aasta.

5\.6 Autentimisi soovitame logida ka klientrakenduse poolel. See on vajalik nii tehniliste tõrgete kui ka teenuse väärkasutuse tuvastamisel ja uurimisel.

### 6 Statistikalogi

6\.1 Statistikalogi eesmärk on teenuste kasutamise kohta statistika tootmine, teenuse juhtimise ja edasiarendamise eesmärgil.

6\.2 Statistikalogisse kogutakse andmed autentimistoimingute kohta, kuid ei koguta isikut identifitseerivate andmeid. S.t kogutakse autentimistoimingu kuupäev ja kellaaeg, autentimismeetod, autentimise tulemus (autenditud või mitte), e-teenus, kus kasutaja autentimisele suunati; kasutaja enda kohta aga ainult kasutaja riik; kasutaja nime ega muid atribuute ei koguta.

6\.3 Statistikalogi põhjal koostatakse ja avalikustatakse statistilisi aruandeid, milles isikuandmed on agregeeritud ja konkreetseid isikuid ei ole võimalik tuvastada.  

### Viited

[1] RIA autentimisteenused, [https://www.ria.ee/et/riigi-infosusteem/eid/partnerile.html#tara](https://www.ria.ee/et/riigi-infosusteem/eid/partnerile.html#tara).

[2] eIDAS Technical specification, [https:\\ec.europa.eu\cefdigital\wiki\display\CEFDIGITAL\eIDAS+Profile](https:\\ec.europa.eu\cefdigital\wiki\display\CEFDIGITAL\eIDAS+Profile).

[3] Autentimisteenus TARA. Tehniline kirjeldus,  [https:\\e-gov.github.io\TARA-Doku\TehnilineKirjeldus#3-autentimisp%C3%A4ring](https:\\e-gov.github.io\TARA-Doku\TehnilineKirjeldus#3-autentimisp%C3%A4ring).
