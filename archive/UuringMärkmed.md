---
permalink: UuringMarkmed
layout: lihtne
---

# [ARHIVEERITUD]

&#9888; Dokumenti ei ajakohastata. Palume siinsele teabele mitte tugineda.

## Uurimisvajadus

E-teenuste ühtses inforuumis ei ole senine infosüsteemi- või asutusepõhine sisselogimine enam optimaalne lahendus. Kasutaja liigub erinevate e-teenuste vahel, näiteks alustab eesti.ee-s, avastab ja tarbib erinevaid teenuseid. Riigi teenusruumis sujuvaks liikumiseks tuleks kasutusele võtta ühekordne sisselogimine (_single sign-on, SSO_).

Ühekordse sisselogimisega, keskse, asutustele kohustusliku autentimisteenuse ehitamine mõjutab kogu avaliku sektori infosüsteemi. Mõjult on tegu peaaegu uue kindlustava süsteemi loomisega. Ühekordne sisselogimine pakub potentsiaalset atraktiivset kasutajakogemust, kuid on seotud ka oluliste ümberehitustega &mdash; ja sellega seotud kuludega teenust kasutavates e-teenustes. Väga tähtsad on ka infoturbe ja andmekaitse aspektid. Seetõttu ei saa enamate võimalustega autentimisteenuse väljatöötamisele asuda enne, kui rida olulisi küsimusi on põhjalikult läbi vaagitud.

## 2 Uurimisküsimused

Uurimist vajavad küsimused on otstarbekas rühmitada kolme rühma: 1) teenuse ärivajadus; 2) teenuse tehnilise kontseptsiooni headus; 3) turvalisus.

### 2.1 SSO ärivajadus

2.1.1 Ärivajaduse lõikes on vaja saada vastused küsimustele: 1) kas sellist teenust on üldse vaja? 2) kas asutused on valmis teenust kasutama? 3) kas asutused on huvitatud? 4) kas teenus tasub end ära?

2.1.2 Küsimustele vastamiseks on otstarbekas koostada teenuse kasutuselevõtu prognostiline mudel (võimalikud on ka teised metoodilised lähenemised). Mudel peab esitama argumenteeritud, kompaktse ja tervikliku käsitluse: 1) asutuste motiividest SSO teenust kasutada; 2) asutuste motiividest SSO teenust mitte kasutada; 3) barjääridest teenuse kasutuselevõtmisel asutustes; 4) asutuste valmisolekust SSO teenust kasutusele võtta; 5)  teenuse kasutuselevõtuga seotud kuludest; 6) teenusest saadavast kasust võrreldes alternatiividega.

2.1.3 Väga oluline on, kas teenuse kasutamine on kohustuslik, _de facto_ kohustuslik või vabatahtlik. Milline saab olema asutuste motiiv teenust kasutada? Kas kasutaja liikumine ilma täiendavate sisselogimisteta võib osutuda "mitte kellegi probleemiks" &mdash; ja järelikult asutuste motiiv teenust juurutada jääks kesiseks? Millised tehnilised või organisatsioonilised barjäärid tuleb asutusel ületada? _Näiteks iframe-e kasutav lahendus [7] võimaldab head kasutajaliidest, kuid tõenäoliselt nõuab autentimis- ja sessioonihalduse loogika suurt muutmist asutuste infosüsteemides._

2.1.4 Plaan on arendada SSO võimekus juurde RIA pakutavale TARA teenusele (vt TARA teenuse teekaart [4]). Teenust pakub RIA. Need küsimused ei vaja uurimist.

### 2.2 SSO teenuse tehnilise kontseptsiooni headus

2.2.1 TARA suhtleb teenust kasutavate klientrakendustega OpenID Connect protokollile põhineva liidese abil [5]. Tarkvaralahendus on ehitatud CAS platvormile. SSO lisamine on kavandatud vastavate funktsionaalsuste kasutuselevõtuga CAS-i platvormil töötavas OpenID Connect moodulis. Kuigi ühtse sisselogimise põhiidee on selge, on tehnilises teostuses nüansse ja otsustuskohti, mis vajavad tähelepanu ja kaalutletud otsuseid. Otstarbekas on tugineda standarditele. Seejuures peab arvestama, et OpenID Connect ja selle alusstandard OAuth 2.0 on mitmeid valikuid pakkuvad raamistikud. Ei ole mõtet teostada kõiki standardi võimalusi. SSO teostamisel tuleks lähtuda OpenID fondi seansihalduse ja ühekordse väljalogimise standarditest [7-9].

2.2.2 Uuring peaks kavandi [6] ja standardite [7-9] alusel andma soovituse sobivaima SSO protokollivoo valimiseks.

### 2.3 SSO turvalisus

2.3.1 SSO teenus peab olema vajalik, teostatav ja turvaline. Turvalisus vajab erilist tähelepanu, seda nii käideldavuse (teenusega tekiks ühtne nuripunkt), tervikluse (teenuse lahtimuukimine avaks ründajale ukse kõigisse liitunud e-teenustesse) kui ka andmekaitse seisukohalt.

## 4 Uuringu läbiviimise kord

4.1 Uuring on otstarbekas läbi viia hankena. Tellija sõnastab uurimisküsimused (käesolev dokument), annab metoodilised juhised, piiritleb uuringu mahuliselt (ajaraam, meeskonna rollid) ning esitab nõuded tulemitele (arutelud, raportid, esitlused). Pakkumuse osaks on nägemus uurimismetoodikast, tööde mahust ja ajakavast.
