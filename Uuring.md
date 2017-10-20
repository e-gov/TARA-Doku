---
permalink: Uuring
---

# SSO uuring
{: .no_toc}

lähteülesanne

kavand 0.1, 20.10.2017

- TOC
{:toc}

## 1 Uurimisvajadus

1.1 E-teenuste ühtses inforuumis ei ole senine infosüsteemi- või asutusepõhine sisselogimine enam optimaalne lahendus. Kasutaja liigub erinevate e-teenuste vahel, näiteks alustab eesti.ee-s, avastab ja tarbib erinevaid teenuseid. Riigi teenusruumis sujuvaks liikumiseks tuleks kasutusele võtta ***ühekordne sisselogimine*** (_single sign-on, SSO_).

1.2 Keskse autentimisteenuse on loonud paljud riigid [1][2]. Välisriikidest on eeskujuna meile olulisim Soome. Suomi.fi autentimisteenus töötab SSO põhimõttel [2].

1.3 Eestis on keskne autentimisteenus seni puudunud. RIA on 2017. a sügisest saati aktiivselt arendanud keskset autentimistteenust TARA [3]. Esimeses, juba käivitunud järgus pakub TARA ainult lihtsat, SSO-ta autentimisteenust.

1.4 Ühtse sisselogimise lisamine nõuab kõigepealt küsimuse uurimist. SSO autentimisteenust on RIA-s kavandatud, vaheaegadega, alates 2015. a. Tulemusena on valminud kavand [6], mis käsitleb küll tehnilist külge, kuid sisalda ärivajaduse ega turvalisuse analüüsi.

1.5 Ühekordse sisselogimise ehitamine mõjutab praktiliselt kogu avaliku sektori infosüsteemi. Mõjult on tegu peaaegu uue kindlustava süsteemi loomisega. SSO pakub potentsiaalset atraktiivset kasutajakogemust, kuid on seotud ka oluliste ümberehitustega &mdash; ja sellega seotud kuludega teenust kasutavates e-teenustes. Riigi ulatusega SSO-teenuses on väga tähtsad ka infoturbe ja andmekaitse aspekt.

1.6 Seetõttu ei saa SSO lahenduse väljatöötamisele asuda enne, kui rida olulisi küsimusi on põhjalikult läbi vaagitud.

## 2 Uurimisküsimused

Uurimist vajavad küsimused on otstarbekas rühmitada kolme rühma: 1) teenuse ärivajadus; 2) teenuse tehnilise kontseptsiooni headus; 3) turvalisus.

### 2.1 SSO ärivajadus

2.1.1 Ärivajaduse lõikes on vaja saada vastused küsimustele: 1) kas sellist teenust on üldse vaja? 2) kas asutused on valmis teenust kasutama? 3) kas asutused on huvitatud? 4) kas teenus tasub end ära?

2.1.2 Küsimustele vastamiseks on otstarbekas koostada teenuse kasutuselevõtu prognostiline mudel (võimalikud on ka teised metoodilised lähenemised). Mudel peab esitama argumenteeritud, kompaktse ja tervikliku käsitluse: 1) asutuste motiividest SSO teenust kasutada; 2) asutuste motiividest SSO teenust mitte kasutada; 3) barjääridest teenuse kasutuselevõtmisel asutustes; 4) asutuste valmisolekust SSO teenust kasutusele võtta; 5)  teenuse kasutuselevõtuga seotud kuludest; 6) teenusest saadavast kasust võrreldes alternatiividega.

2.1.3 Väga oluline on, kas teenuse kasutamine on kohustuslik, _de facto_ kohustuslik või vabatahtlik. Milline saab olema asutuste motiiv teenust kasutada? Kas kasutaja liikumine ilma täiendavate sisselogimisteta võib osutuda "mitte kellegi probleemiks" &mdash; ja järelikult asutuste motiiv teenust juurutada jääks kesiseks? Millised tehnilised või organisatsioonilised barjäärid tuleb asutusel ületada? _Näiteks iframe-e kasutav lahendus [7] võimaldab head kasutajaliidest, kuid tõenäoliselt nõuab autentimis- ja sessioonihalduse loogika suurt muutmist asutuste infosüsteemides._

2.1.4 Plaan on arendada SSO võimekus juurde RIA pakutavale TARA teenusele (vt TARA teenuse teekaart [4]). Teenust pakub RIA. Need Küsimused ei vaja uurimist.

### 2.2 SSO teenuse tehnilise kontseptsiooni headus

2.2.1 TARA suhtleb teenust kasutavate klientrakendustega OpenID Connect protokollile põhineva liidese abil [5]. Tarkvaralahendus on ehitatud CAS platvormile. SSO lisamine on kavandatud vastavate funktsionaalsuste kasutuselevõtuga CAS-i platvormil töötavas OpenID Connect moodulis. Kuigi ühtse sisselogimise põhiidee on selge, on tehnilises teostuses nüansse ja otsustuskohti, mis vajavad tähelepanu ja kaalutletud otsuseid. Otstarbekas on tugineda standarditele. Seejuures peab arvestama, et OpenID Connect ja sellel alusstandard OAuth 2.0 on mitmeid valikuid pakkuvad raamistikud. Ei ole mõtet teostada kõiki standardi võimalusi. SSO teostamisel tuleks lähtuda OpenID fondi seansihalduse ja ühekordse väljalogimise standarditest [7-9].

2.2.2 Uuring peaks kavandi [6] ja standardite [7-9] alusel andma soovituse sobivaima SSO protokollivoo valimiseks.

### 2.3 SSO turvalisus

2.3.1 SSO teenus peab olema vajalik, teostatav ja turvaline. Turvalisus vajab erilist tähelepanu, seda nii käideldavuse (teenusega tekiks ühtne nuripunkt), tervikluse (teenuse lahtimuukimine avaks ründajale ukse kõigisse liitunud e-teenustesse) kui ka andmekaitse seisukohalt.

## 3 Ei ole uuringu skoobis

3.1 Uuring ei peaks süvitsi minema küsimustes, mis võivad küll olla olulised laiemas kontekstis, kuid ei ole ühtse sisselogimisteenuse teostamise seisukohalt kriitilised. Sinna kuuluvad: 1) autentimise õiguslik regulatsioon Eesti Vabariigis. Vajadus asju täiendada võib olla. Kui, siis uuring peaks käsitlema küsimusi: kas SSO teenuse osutamiseks on praeguses õigusruumis takistusi? kui teenus teha kohustuslikuks, siis millise õigusaktiga? 2) autentimismeetodite areng; 3)  konkreetste autentimismeetodite tehnilised detailid; 4) OpenID Connect protokollist väljuv autentimise tehniliste, sotsiaalsete,filosoofiliste küsimuste ("mis on identiteet?") käsitlus.

## 4 Uuringu läbiviimise kord

4.1 Uuring on otstarbekas läbi viia hankena. Tellija sõnastab uurimisküsimused (käesolev dokument), annab metoodilised juhised, piiritleb uuringu mahuliselt (ajaraam, meeskonna rollid) ning esitab nõuded tulemitele (arutelud, raportid, esitlused). Pakkumuse osaks on nägemus uurimismetoodikast, tööde mahust ja ajakavast. 

## Viited

[1] Lätis on keskne autentimisteenus tihedas kasutuses.<br>
[2] Soomes on loodud keskne Suomi.fi autentimisteenus, mille võtavad kasutusele kõik keskvalitsuse asutused. Vt [https://tunnistaminen.suomi.fi/sivut/info/tietoapalvelusta/](https://tunnistaminen.suomi.fi/sivut/info/tietoapalvelusta/).<br>
[3] Riigi Infosüsteemi Amet (2017) TARA autentimisteenus, [https://e-gov.github.io/TARA-Doku/](https://e-gov.github.io/TARA-Doku/).<br>
[4] TARA autentimisteenus. Teekaart [https://e-gov.github.io/TARA-Doku/Teekaart](https://e-gov.github.io/TARA-Doku/Teekaart).<br>
[5] TARA autentimisteenus. Tehniline kirjeldus [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus).<br>
[6] Riigi Infosüsteemi Amet. RIA SSO autentimisteenuse kavand. 18 lk. [https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon](https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon).<br>
[7] OpenID Foundation (2017) OpenID Connect Session Management 1.0. Draft 28. [http://openid.net/specs/openid-connect-session-1_0.html](http://openid.net/specs/openid-connect-session-1_0.html).<br>
[8] OpenID Foundation (2017) OpenID Connect Front-Channel Logout 1.0. Draft 02. 
[http://openid.net/specs/openid-connect-frontchannel-1_0.html](http://openid.net/specs/openid-connect-frontchannel-1_0.html).<br>
[9] OpenID Foundation (2017) OpenID Connect Back-Channel Logout 1.0. Draft 04. [http://openid.net/specs/openid-connect-backchannel-1_0.html](http://openid.net/specs/openid-connect-backchannel-1_0.html).<br>
