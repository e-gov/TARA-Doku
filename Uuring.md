---
permalink: Uuring
---

# SSO uuring
{: .no_toc}

lähteülesanne

kavand 0.1, 20.10.2017

- TOC
{:toc}

## Uurimisvajadus

E-teenuste ühtses inforuumis ei ole senine infosüsteemi- või asutusepõhine sisselogimine enam optimaalne lahendus. Kasutaja liigub erinevate e-teenuste vahel, näiteks alustab eesti.ee-s, avastab ja tarbib erinevaid teenuseid. Riigi teenusruumis sujuvaks liikumiseks tuleks kasutusele võtta ***ühekordse sisselogimise*** (ingl _single sign-on, SSO_) lahendus.

Keskse autentimisteenuse on loonud paljud riigid [1][2]. Välisriikidest on eeskujuna meile olulisim Soome. Suomi.fi autentimisteenus töötab SSO põhimõttel [2].

Eestis on keskne autentimisteenus seni puudunud. RIA on 2017. a sügisest saati aktiivselt arendanud keskset autentimistteenust TARA [3]. Esimeses, juba käivitunud järgus pakub TARA ainult lihtsat, SSO-ta autentimisteenust.

Ühtse sisselogimise lisamine nõuab kõigepealt küsimuse uurimist. SSO autentimisteenust on RIA-s kavandatud, vaheaegadega, alates 2015. a. Tulemusena on valminud kavand [6], mis käsitleb küll tehnilist külge, kuid sisalda ärivajaduse ega turvalisuse analüüsi.

Ühekordse sisselogimise ehitamine mõjutab praktiliselt kogu avaliku sektori infosüsteemi. Mõjult on tegu peaaegu uue kindlustava süsteemi loomisega. SSO pakub potentsiaalset atraktiivset kasutajakogemust, kuid on seotud ka oluliste ümberehitustega &mdash; ja sellega seotud kuludega teenust kasutavates e-teenustes. Riigi ulatusega SSO-teenuses on väga tähtsad ka infoturbe ja andmekaitse aspekt.

Seetõttu ei saa SSO lahenduse väljatöötamisele asuda enne, kui rida olulisi küsimusi on põhjalikult läbi vaagitud.

## Uurimisküsimused

Uurimist vajavad küsimused on otstarbekas rühmitada kolme rühma: 1) teenuse ärivajadus; 2) teenuse tehnilise kontseptsiooni headus; 3) turvalisus.

### SSO ärivajadus

Ärivajaduse lõikes on vaja saada vastused küsimustele:
- kas sellist teenust on üldse vaja?
- kas asutused on valmis teenust kasutama?
- kas asutused on huvitatud?
- kas teenus tasub end ära?

Küsimustele vastamiseks on otstarbekas koostada teenuse kasutuselevõtu prognostiline mudel (võimalikud on ka teised metoodilised lähenemised). Mudel peab esitama argumenteeritud, kompaktse ja tervikliku käsitluse:
- asutuste motiividest SSO teenust kasutada
- asutuste motiividest SSO teenust mitte kasutada
- barjääridest teenuse kasutuselevõtmisel asutustes
- asutuste valmisolekust SSO teenust kasutusele võtta
- teenuse kasutuselevõtuga seotud kuludest
- teenusest saadavast kasust võrreldes alternatiividega.

Väga oluline on, kas teenuse kasutamine on kohustuslik, _de facto_ kohustuslik või vabatahtlik. Milline saab olema asutuste motiiv teenust kasutada? Kas kasutaja liikumine ilma täiendavate sisselogimisteta võib osutuda "mitte kellegi probleemiks" &mdash; ja järelikult asutuste motiiv teenust juurutada jääks kesiseks? Millised tehnilised või organisatsioonilised barjäärid tuleb asutusel ületada? _Näiteks iframe-e kasutav lahendus [7] võimaldab head kasutajaliidest, kuid tõenäoliselt nõuab autentimis- ja sessioonihalduse loogika suurt muutmist asutuste infosüsteemides._

Plaan on arendada SSO võimekus juurde RIA pakutavale TARA teenusele (vt TARA teenuse teekaart [4]). Teenust pakub RIA. Need Küsimused ei vaja uurimist.

### SSO teenuse tehnilise kontseptsiooni headus

TARA suhtleb teenust kasutavate klientrakendustega OpenID Connect protokollile põhineva liidese abil [5]. Tarkvaralahendus on ehitatud CAS platvormile. SSO lisamine on kavandatud vastavate funktsionaalsuste kasutuselevõtuga CAS-i platvormil töötavas OpenID Connect moodulis. Kuigi ühtse sisselogimise põhiidee on selge, on tehnilises teostuses nüansse ja otsustuskohti, mis vajavad tähelepanu ja kaalutletud otsuseid. Otstarbekas on tugineda standarditele. Seejuures peab arvestama, et OpenID Connect ja sellel alusstandard OAuth 2.0 on mitmeid valikuid pakkuvad raamistikud. Ei ole mõtet teostada kõiki standardi võimalusi. SSO teostamisel tuleks lähtuda OpenID fondi seansihalduse ja ühekordse väljalogimise standarditest [7-9].

Uuring peaks kavandi [6] ja standardite [7-9] alusel andma soovituse sobivaima SSO protokollivoo valimiseks.

### SSO turvalisus

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
