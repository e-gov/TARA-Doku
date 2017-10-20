---
permalink: SSO
---

# SSO uuring. Lähteülesanne
{: .no_toc}

kavand 0.1, 20.10.2017

- TOC
{:toc}

E-teenuste ühtses inforuumis ei ole senine infosüsteemi- või asutusepõhine sisselogimine enam optimaalne lahendus. Kasutaja liigub erinevate e-teenuste vahel, näiteks alustab eesti.ee-s, leiab ja kasutab erinevaid teenuseid. Riigi teenusruumis sujuvaks liikumiseks tuleks kasutusele võtta ***ühekordse sisselogimise*** (ingl _single sign-on, SSO_) põhimõte.

Avaliku sektori keskse autentimisteenuse on loonud paljud riigid [1][2]. Välisriikidest on eeskujuna meile olulisim Soome. Suomi.fi autentimisteenus töötab SSO põhimõttel [2]. Samas ei saa me välisriikide kogemust lihtsalt kopeerida.

Eestis on keskne autentimisteenus seni puudunud. RIA on 2017. a sügisest arendanud keskset autentimistteenust TARA [3]. Esimeses järgus TARA pakub lihtsat autentimisteenust, ühtse sisselogimise lisamine nõuab kõigepealt küsimuse uurimist.

SSO autentimisteenust on RIA-s kavandatud alates 2015. a. Tulemusena on valminud kavand [6].

## Uurimisvajadus

Ühekordse sisselogimise rakendamine nõuab mitmete oluliste küsimuste selgitamist. Vaja on uurida kolme küsimuste rühma: 1) teenuse ärivajadus; 2) teenuse tehnilise kontseptsiooni headus; 3) turvalisus.

## SSO ärivajadus

Ärivajaduse lõikes on vaja saada vastused küsimustele:
- kas sellist teenust on üldse vaja?
- kas asutused on valmis teenust kasutama?
- kas asutused on huvitatud?
- kas teenus tasub end ära?

Küsimustele vastamiseks on otstarbekas koostada teenuse kasutuselevõtu (prognostiline) mudel. Mudel peab esitama argumenteeritud, kompaktse ja tervikliku käsitluse:
- asutuste motiividest SSO teenust kasutada
- asutuste motiividest SSO teenust mitte kasutada
- barjääridest teenuse kasutuselevõtmisel asutustes
- asutuste valmisolekust SSO teenust kasutusele võtta
- teenuse kasutuselevõtuga seotud kuludest
- teenusest saadavast kasust võrreldes alternatiividega.

Väga oluline on, kas teenuse kasutamine on kohustuslik, _de facto_ kohustuslik või vabatahtlik. Milline saab olema asutuste motiiv teenust kasutada? Kas kasutaja liikumine ilma täiendavate sisselogimisteta võib osutuda "mitte kellegi probleemiks"? Millised tehnilised või organisatsioonilised barjäärid tuleb asutusel ületada? _Näiteks iframe-e kasutav lahendus [7] võimaldab hea kasutajaliidest, kuid tõenäoliselt tähendab autentimis- ja sessioonihalduse loogika suurt muutmist asutuste infosüsteemides._

Praegune nägemus on arendada SSO võimekus juurde RIA pakutavale TARA teenusele (vt TARA teenuse teekaart [4]). Teenust pakub RIA. Need Küsimused ei vaja uurimist.

## SSO teenuse tehnilise kontseptsiooni headus

TARA suhtleb teenust kasutavate klientrakendustega OpenID Connect protokollile põhineva liidese abil [5]. Tarkvaralahendus on ehitatud CAS platvormile. SSO lisamine võiks toimuda vastavate funktsionaalsuste kasutuselevõtuga CAS-i platvormil töötavas OpenID Connect moodulis. Kuigi ühtse sisselogimise põhiidee on selge, on tehnilises teostuses nüansse ja otsustuskohti, mis vajavad tähelepanu ja kaalutletud otsuseid. Otstarbekas on tugineda standarditele. Seejuures peab arvestama, et OpenID Connect ja sellel alusstandard OAuth 2.0 on mitmeid valikuid pakkuvad raamistikud. Ei ole mõtet teostada kõiki standardi võimalusi. SSO teostamisel tuleks lähtuda OpenID fondi seansihalduse ja ühhekordse väljalogimise standarditest [7-9].

Protokollivoogude valimine ja tehtud valikute verifitseerimine.

## SSO turvalisus

SSO teenus peab olema vajalik, teostatav ja turvaline. Turvalisus vajab erilist tähelepanu, seda nii käideldavuse (teenusega tekiks ühtne nuripunkt), tervikluse (teenuse lahtimuukimine avaks ründajale ukse kõigisse liitunud e-teenustesse) kui ka andmekaitse seisukohalt.

## Ei ole uuringu skoobis

Uuring ei peaks süvitsi minema küsimustes, mis võivad küll olla olulised laiemas kontekstis, kuid ei ole ühtse sisselogimisteenuse teostamise seisukohalt kriitilised. Sinna kuuluvad:
- autentimise õiguslik regulatsioon Eesti Vabariigis. Vajadus asju täiendada võib olla. Kui, siis uuring peaks käsitlema küsimusi:
  - kas SSO teenuse osutamiseks on praeguses õigusruumis takistusi?
  - kui teenus teha kohustuslikuks, siis millise õigusaktiga?
- autentimismeetodite areng
- konkreetste autentimismeetodite tehnilised detailid
- OpenID Connect protokollist väljuv autentimise tehniliste, sotsiaalsete, filosoofiliste küsimuste ("mis on identiteet?") käsitlus.

## Uuringu läbiviimise kord

Uuring on otstarbekas läbi viia hankena. Tellija sõnastab uurimisküsimused (käesolev dokument), annab metoodilised juhised, piiritleb uuringu mahuliselt (ajaraam, meeskonna rollid) ning esitab nõuded tulemitele (arutelud, raportid, esitlused). Pakkumuse osaks on nägemus uurimismetoodikast, tööde mahust ja ajakavast. 

## Viited

[1] Lätis on keskne autentimisteenus tihedas kasutuses.
[2] Soomes on loodud keskne Suomi.fi autentimisteenus, mille võtavad kasutusele kõik keskvalitsuse asutused. Vt [https://tunnistaminen.suomi.fi/sivut/info/tietoapalvelusta/](https://tunnistaminen.suomi.fi/sivut/info/tietoapalvelusta/).
[3] Riigi Infosüsteemi Amet (2017) TARA autentimisteenus, [https://e-gov.github.io/TARA-Doku/](https://e-gov.github.io/TARA-Doku/).
[4] TARA autentimisteenus. Teekaart [https://e-gov.github.io/TARA-Doku/Teekaart](https://e-gov.github.io/TARA-Doku/Teekaart).
[5] TARA autentimisteenus. Tehniline kirjeldus [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus).
[6] Riigi Infosüsteemi Amet. RIA SSO autentimisteenuse kavand. 18 lk. [https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon](https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon).
[7] OpenID Foundation (2017) OpenID Connect Session Management 1.0. Draft 28. [http://openid.net/specs/openid-connect-session-1_0.html](http://openid.net/specs/openid-connect-session-1_0.html).
[8] OpenID Foundation (2017) OpenID Connect Front-Channel Logout 1.0. Draft 02. 
[http://openid.net/specs/openid-connect-frontchannel-1_0.html](http://openid.net/specs/openid-connect-frontchannel-1_0.html).
[9] OpenID Foundation (2017) OpenID Connect Back-Channel Logout 1.0. Draft 04. [http://openid.net/specs/openid-connect-backchannel-1_0.html](http://openid.net/specs/openid-connect-backchannel-1_0.html). 
