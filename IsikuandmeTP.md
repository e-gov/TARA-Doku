---
permalink: Isikuandmed
---

# Isikuandmete töötluse poliitika

Käesolevas dokumendis kehtestatakse RIA autentimisteenustes [1], edaspidi "teenused", rakendatavad isikuandmete töötluse konkreetsed meetmed. 

### 1 Rakendusala

2\.1 Poliitika rakendub:

1. RIA eIDAS konnektorteenusele
2. RIA autentimisteenusele
3. TARA autentimisteenusele TARA
4. EL piiriülese autentimistaristu Eesti sõlmele ("eIDAS Node").

### 2 Mõisted

_kasutaja_, füüsiline isik, kes suunatakse Eesti või välismaa e-teenusest RIA autentimisteenusesse isikusamasuse tuvastamisele.

### 3 Töödeldavad isikuandmed

3\.1 Teenustes töödeldakse kasutajate (autenditavate isikute) kohta järgmisi andmeid:

1. kasutajat identifitseerivad andmed
  - kasutaja isikukood vm isiku identifikaator
  - kasutaja ees- ja perekonnanimi
  - kasutaja riik
  - muud kasutaja isikuandmed, eIDASE terminoloogias "atribuudid", vastavalt eIDAS määruse alusel väljatöötatud eIDAS tehnilisele spetsifikatsioonile [2]. _Eestikeelne nimekiri eIDAS atribuutidest on kättesaadav TARA tehnilises kirjelduses [3]._

2. autentimistoimingu andmed
  - kuupäev ja kellaaeg
  - klientrakendus, kust kasutaja autentimisele suunati
  - autentimismeetod
  - autentimise tulemus (autenditud või mitte).

3\.2 Teenuste haldamise eesmärgil kogutakse ka liidestatud asutuste kontaktsisikute andmeid.

### 4 Andmete väljastamine

4\.1 Ülalnimetatud isikuandmeid väljastatakse teenustega liidestatud e-teenustele. Siseriiklikult on nendeks asutuste e-teenuseid pakkuvad infosüsteemid. Piiriüleselt - EL teiste liikmesriikide eIDAS sõlmed.

4\.2 Kasutajale on autentimise tulemus (sisse logitud või mitte) nähtav sirvikus.

4\.3 Kasutajale ei ole praegu loodud iseteeninduse põhimõttel toimivat elektroonilist võimalust tutvuda oma autentimisajalooga. Vajadusel saab vastavatele kasutaja päringutele vastata teenusehaldur käsitsi.

4\.4 Eesti eID kasutaja andmete saatmisel EL teise riiki küsitakse kasutaja nõusolekut (Eesti autentimisteenuses). 

### 5 Turvalogi

5\.1 Turvalogi eesmärk on:

1. rakenduste väärkasutamise ärahoidmine ja turvaintsidentide uurimise jaoks andmete kogumine
2. teenustega liidestatud e-teenuste omanike s.t asutuste poolt raporteeritud tehniliste probleemide põhjuste väljaselgitamine
3. kasutajate võimalike pöördumiste (teated võimalike turvaprobleemide või tehniliste rikete) menetlemine.

5\.2 Turvalogis logitakse autentimistoimingute andmed, sh autenditud kasutajate andmed.

5\.3 Turvalogile juurdepääs on rangelt vajaduspõhine. Ligi pääsevad ainult teenuse käitamisega otseselt seotud süsteemi- ja teenusehaldurid.

5\.4 Turvalogide andmeid ei edastata teistesse süsteemidesse ega kasutata muudel eesmärkidel, kui kasutaja isikusamasuse tuvastamine konkreetse e-teenuse pöördumise kontekstis (välja arvatud kuriteo tõkestamise või uurimise vajaduse korral, vastavalt õigusele).

5\.5 Turvalogisid säilitatakse üks aasta.

### 6 Statistikalogi

6\.1 Statistikalogi eesmärk on teenuste kasutamise kohta statistika tootmine, teenuse juhtimise ja edasiarendamise eesmärgil.

6\.2 Statistikalogisse kogutakse andmed autentimistoimingute kohta, kuid ei koguta isikut identifitseerivate andmeid. S.t kogutakse autentimistoimingu kuupäev ja kellaaeg, autentimismeetod, autentimise tulemus (autenditud või mitte), e-teenus, kus kasutaja autentimisele suunati; kasutaja enda kohta aga ainult kasutaja riik; kasutaja nime ega muid atribuute ei koguta.

6\.3 Statistikalogi põhjal koostatakse ja avalikustatakse statistilisi aruandeid, milles isikuandmed on agregeeritud ja konkreetseid isikuid ei ole võimalik tuvastada.  

### Viited

[1] RIA autentimisteenused, [https:\\www.ria.ee\ee\autentimisteenused.html](https:\\www.ria.ee\ee\autentimisteenused.html).

[2] eIDAS Technical specification, [https:\\ec.europa.eu\cefdigital\wiki\display\CEFDIGITAL\eIDAS+Profile](https:\\ec.europa.eu\cefdigital\wiki\display\CEFDIGITAL\eIDAS+Profile).

[3] Autentimisteenus TARA. Tehniline kirjeldus,  [https:\\e-gov.github.io\TARA-Doku\TehnilineKirjeldus#3-autentimisp%C3%A4ring](https:\\e-gov.github.io\TARA-Doku\TehnilineKirjeldus#3-autentimisp%C3%A4ring).
