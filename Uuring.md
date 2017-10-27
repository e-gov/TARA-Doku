---
permalink: Uuring
layout: Lihtne
---

Autentimisteenuse turvauuring
{: .pais}

{: .no_toc}

# Lähteülesanne

kavand 0.2, 27.10.2017

- TOC
{:toc}

## Mõisted

_autentimine_, kasutaja isiku tuvastamise toiming (tehniliselt täpsemas tähenduses: isikusamasuse tuvastamine)
_autentimine kui teenus_, arhitektuurimuster, kus rakendus ei autendi kasutajat ise, vaid delegeerib autentimise eraldiseisvale e-teenusele.
_e-teenus_, asutuse poolt kasutajale, sh välismaalasele pakutav e-teenus
_kasutaja_, e-teenust kasutav füüsiline isik
_rakendus_, asutuse veebirakendus, mis pakub e-teenust; koosneb kahest osast: 1) kasutaja sirvijasse laetav osa; 2) serveripoolne osa
_sisselogimine_, (_sing-on_, _sign-in_), toiming, millega kasutaja isik tuvastatakse ja luuakse seanss kasutaja ja rakenduse vahel
_ühekordne sisselogimine_, (single sign-on, SSO), arhitektuurimuster, kus kasutaja saab ühe sisselogimisega kasutada mitut e-teenust.
_ühekordne väljalogimine_ (single sign-off), arhitektuurimuster, kus kasutaja logitakse ühe väljalogimisega välja mitmest e-teenusest.
_seanss_, ka _sessioon_, ajas piiratud suhe kasutaja ja rakenduse vahel. Võib alata sisselogimisega aga ka lihtsalt sirvijas esimese pöördumisega rakenduse poole; võib lõppeda väljalogimisega, aga ka sirvija sulgemisega, seansi ühepoolse lõpetamisega rakenduse poolt vms.
_seansihaldus_, ka _seansi pidamine_, seansi loomise, hoidmise, turvamise ja lõpetamise toimingud.

Käesolevas lähteülesandes kasutatakse ka veebilehel [https://e-gov.github.io/TARA-Doku/Sonastik](https://e-gov.github.io/TARA-Doku/Sonastik) määratletud mõisteid.

## Autentimisteenuse ulatus 

Autentimistoiming on protseduur, mis lõpeb _autentimissündmusega_. See on hetk, kus jõutakse 0/1 tulemuseni - kas isik loetakse tuvastatuks või mitte. 

Kuid autentimisteenus võib olla &mdash; ja sageli ongi &mdash; seotud täiendavate teenustega:
- ***autentimisteenuste vahendamise teenus***. Teenus koondab ja teeb rakendusele kättesaadavaks erinevate teenuseosutajate pakutavaid autentimisteenuseid.  
- ***kasutaja rollide väljaselgitamise teenus***. Näiteks, autentimisteenusega võib olla ühitatud päring äriregistrisse. Autentimise tulemus sisaldab kasutaja esindusõiguste nimekirja.
- ***rolli valimise teenus***. Näiteks autentimisdialoogis valib kasutaja ühtlasi organisatsiooni, kelle nimel ta tegutseb. 
- ***kasutajat kirjeldavate tunnuste (atribuutide) teenus***. Minimaalsel juhul tekib autentimissündmuses väga väike teabekogum: tuvastatud isiku identifikaator, nimi, autentimisteenuse nimi, autentimise aeg, mõned konteksti kirjeldavad andmed. Tihti soovitakse, et autentimisega selgitatakse välja täiendavaid kasutajaga seotud andmeid. Rakendus võib kasutajaandmeid käia autentimisteenusest isegi hiljem pärimas (nt OpenID Connect protokollis UserInfo otspunkti kaudu).
- ***kasutaja nõusoleku võtmise teenus***. Kasutaja nõusoleku andmine on tihedalt seotud isiku tuvastamisega. Seetõttu teostatakse need funktsionaalsused tihti koos. Kasutaja nõusolek (_user consent_) võib olla andmete töötlemiseks või ka muuks. OAuth 2.0 ongi eelkõige kasutaja nõusoleku võtmise protokoll, kus autentimine on vaid nõusoleku võtmise kaasnähtus.
- ***sessioonihalduse teenus***. _Lihtne autentimisteenus_ piirdub ühekordse autentimistoimingu läbiviimisega ja sessioonihaldusega ei tegele. Autentimistoimingu tulemuseks on rakendusele väljastatav teave autentimissündmuse kohta (OpenID Connect protokollis _identsustõendi_ vormis). Seansi loomisega ega hoidmisega lihtne autentimisteenus ei tegele. Sessioonihaldust sisaldav autentimisteenus võtab enda kanda ka teisi sessiooni haldamise tegevusi, nt kasutaja perioodiline uuesti autentimine, seansi aegumise jälgimine jms.  
- ***ühekordse sisselogimise teenus***. 
- ***ühekordse väljalogimise teenus***.

Laiemas tähenduses võib kõiki neid teenuseid nimetada autentimisteenusteks.

Oluline küsimus on kas ja millist osateenuste buketti pakkuda.

## 1 Uurimisvajadus

1.1 E-teenuste ühtses inforuumis ei ole senine infosüsteemi- või asutusepõhine sisselogimine enam optimaalne lahendus. Kasutaja liigub erinevate e-teenuste vahel, näiteks alustab eesti.ee-s, avastab ja tarbib erinevaid teenuseid. Riigi teenusruumis sujuvaks liikumiseks tuleks kasutusele võtta ühekordne sisselogimine (_single sign-on, SSO_).

1.2 Keskse autentimisteenuse on loonud paljud riigid [1][2]. Välisriikidest on eeskujuna meile olulisim Soome. Suomi.fi autentimisteenus töötab SSO põhimõttel [2].

1.3 Eestis on keskne autentimisteenus seni puudunud. RIA on 2017. a sügisest saati aktiivselt arendanud keskset autentimisteenust TARA [3]. Esimeses, juba käivitunud järgus pakub TARA ainult lihtsat, SSO-ta autentimisteenust.

1.4 Ühtse sisselogimise lisamine nõuab kõigepealt küsimuse uurimist. SSO autentimisteenust on RIA-s kavandatud, vaheaegadega, alates 2015. a. Tulemusena on valminud kavand [6], mis käsitleb küll tehnilist külge, kuid ei sisalda ärivajaduse ega turvalisuse analüüsi.

1.5 Ühekordse sisselogimise ehitamine mõjutab praktiliselt kogu avaliku sektori infosüsteemi. Mõjult on tegu peaaegu uue kindlustava süsteemi loomisega. SSO pakub potentsiaalset atraktiivset kasutajakogemust, kuid on seotud ka oluliste ümberehitustega &mdash; ja sellega seotud kuludega teenust kasutavates e-teenustes. Riigi ulatusega SSO-teenuses on väga tähtsad ka infoturbe ja andmekaitse aspektid.

1.6 Seetõttu ei saa SSO lahenduse väljatöötamisele asuda enne, kui rida olulisi küsimusi on põhjalikult läbi vaagitud.

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

## 3 Ei ole uuringu skoobis

3.1 Uuring ei peaks süvitsi minema küsimustes, mis võivad küll olla olulised laiemas kontekstis, kuid ei ole ühtse sisselogimisteenuse teostamise seisukohalt kriitilised. Sinna kuuluvad: 1) autentimise õiguslik regulatsioon Eesti Vabariigis. Allkirjastamisega võrreldes on autentimine vähem reguleeritud. Võib olla vajadus autentimist õiguslikult rohkem reguleerida, kuid uuring peaks piirduma küsimustega: kas SSO teenuse osutamiseks on praeguses õigusruumis olulisi takistusi? kui teenus teha kohustuslikuks, siis millise õigusaktiga? 2) autentimismeetodite areng; 3)  konkreetsete autentimismeetodite tehnilised detailid; 4) OpenID Connect protokollist väljuv autentimise tehniliste, sotsiaalsete, filosoofiliste küsimuste ("mis on identiteet?") käsitlus.

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
