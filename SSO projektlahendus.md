---
permalink: SSO projektlahendus
---

# Ühekordse sisselogimise (SSO) projektlahendus

<p style='text-align:left;'><img src='img/EL_logo.jpg' style='width:150px'></p>

- TOC
{:toc}

## Sõnastik

- OAuth – avatud standard kasutajate autoriseerimiseks. (Viited, [OAUTH])
- OIDC – OpenID Connect, standardne protokoll internetis kasutajate autentimiseks, baseerub OAuth 2.0 raamistikul.
- OIDF – OpenID Foundation, OpenID tehnoloogiate edendamist, arengut ja standardiseerimist juhtiv rahvusvaheline mittetulunduslik organisatsioon.
- SSO – _single-sign-on_ ehk ühekordne sisselogimine. Pääsu reguleerimise funktsioon, mis võimaldab kasutajal pöörduda üheainsa logimisega paljude eri ressursside poole. (Viited, [AKIT])
- TARA – Riigi autentimisteenus
- TARA SSO – ühekordse sisselogimise funktsionaalsus TARA teenuses (tehniliselt võib olla SSO funktsionaalsust pakkuv rakendus TARA teenuse ees)
- sirvik – lõppkasutaja masinasse paigaldatud tarkvara, mille kaudu inimene teenusepakkuja keskkonda (ja TARA SSO teenust) kasutab
- klientrakendus – TARA ja/või SSO teenust tarbiv asutuse infosüsteem. Võib olla nii asutusesisene e-teenus kui ka asutusesisene SSO lahendus
- OP – OpenID Connect Provider ehk OIDC protokolli järgi teenust pakkuv server
- eeskanal – OIDC kliendi ja serveri vaheline suhtlus, kus sõnumid liiguvad läbi sirviku ja on lõppkasutajale nähtavad (_front-channel_)
- taustakanal – OIDC kliendi ja serveri vaheline suhtlus, kus sõnumid liiguvad klientrakenduse ja TARA SSO serveri vahel sirviku vahenduseta (_back-channel_)

## Eesmärk

Analüüsida ja pakkuda välja tehnilise lahenduse kirjeldus TARAs ühekordse sisselogimise (SSO) teenuse implementeerimiseks. SSO teenuse äriline eesmärk on riigi autentimisteenuses tasuliste autentimispäringute arvu vähendamine ja seeläbi riigi autentimisteenuse kulude optimeerimine. Korduvate autentimiste vähendamisega paraneb ka Riigi autentimisteenuse kasutusmugavus.

## TARA SSO kirjeldus ja analüüsi skoop

Analüüsi käigus valitakse TARA tehnilise lahendusega ja Eesti kontekstiga sobivad OIDC protokollistiku kasutusvood ja –elemendid. TARA SSOd käsitletakse keskse autentimise lahendusena ega uurita keskse autoriseerimise lahenduse aspekte. See tähendab, et SSO analüüsis ei vaadelda isiku erinevate esindusõigustega seotud nüansse, vaid keskendutakse n-ö kodaniku vaatele.

TARA SSO teenuse ülesanne ei ole välja vahetada asutuses juba kasutusel olevat ühekordse sisselogimise komponenti. Kasutajate autoriseerimise loogika tuleb endiselt lahendada asutuse või valdkonna sees. Keskse ligipääsuhalduse teostus on ka OIDC protokollistikus teostatav, kuid nõuab mahukamat detailset analüüsi asutuste ja valdkondade spetsiifiliste nõuete osas.

Rakendame TARA SSOs OIDC ja OAuth 2.0 tehnilisi protokolle. OIDC on avatud standard, mis on turul olnud pikka aega ning on toetatud laias hulgas standardteekides ja karbitoodetes. Käesoleva analüüsi eesmärk ei ole tõestada OIDC protokolli ja tema erinevate autentimisvoogude turvalisust. Seda teemat on varasemalt põhjalikult uuritud ning levinumad implementatsioonivead ja -ründed on hästi teada (Viited, [OAUTH THREAT], [SSO SECURITY]). Analüüsi eesmärk on rakendada OIDC protokolli tuumikspetsifikatsiooni ja selle seansihaldusega seotud laiendusi TARA SSO jaoks sobival moel. Tuleb leida keskse seansihalduse lahendus, mille puhu säiliks praeguse TARA teenuse kõrge turvatase, kuid mis oleks ka tehniliselt piisavalt paindlik.

Usaldusahela vaatest keskendume mudelile, kus TARA SSO on keskne autentimise seansi ja identiteedi hoidja ning TARA SSO klientideks asutused (kellel omakorda võib olla kasutusel oma sisemine SSO lahendus TARA SSO taga). Tehnilises analüüsis ega POC lahenduse skoobis ei keskenduta riigi autentimisteenuse födereeritud lahendusele, kus TARA SSO koos teiste alternatiivsete SSO lahendustega moodustaks OIDC hajutatud autentimisteenuse pakkujate võrgustiku (Viited, [OIDC Federated]).

Autentimisel luuakse kasutaja seadme (sirviku) ja TARA SSO komponendi vahel autentimise seanss, mille tunnus on salvestatud kasutaja seadmesse. Kogu SSO turvaahela seisukohast on kriitiline, et ligipääs sellele tunnusele on piiratud ja seansitõend hävitatakse, kui kasutaja on lõpetanud klientrakenduse ühenduse. Seansi tunnuse lekkimine on keskse seansihalduse vaatest kõige suurem risk, sest tunnuse lekkimine annab kuritahtlikule kasutajale loa siseneda kõikidesse SSOga liidestatud klientrakendustesse. Riski maandamiseks analüüsitakse ja koostatakse lisaks autentimisele seansi kehtivuse mudel, seansi kehtivuse kontrolli voog, seansi kehtivuse pikendamise voog ja seansi lõpetamise voog.

## TARA SSO nõuded

### TARA senise voo säilitamine

Kliendid, kellel pole keskse seansihalduse järele vajadust, peaksid saama jätkata TARA teenuse kasutamist senisel kujul. Igasugune lisaarendus olemasolevatele klientidele TARA SSO teenuse lisandumisega tuleks välistada. Seda kasutusjuhtu toetab valitud arhitektuuriline lahendus, kus TARA SSO komponent paigaldatakse TARA komponendist eraldi. TARA SSO toetamiseks vajalikud muudatused saab TARA protokollis teha ilma olemasolevate klientide liidest muutmata. SSO funktsionaalsuse toetamiseks on soovitatav TARA liideses sisendparameetrina implementeerida klientrakenduse identifikaatori vastuvõtmise võimalus. TARA SSO kasutab vastavat päringu välja, et edastada TARA SSO komponendist autentimispäringu saatnud klientrakenduse identifikaator TARA komponendile. Väli peab TARA protokollis olema mittekohustuslik ja lubatud kasutada ainult TARA SSO serveri edastatud päringute korral.

Kui tavapärases TARA autentimise voos tehakse eeldus, et klientrakendus on enne autentimise alustamist andnud kasutajale võimaluse tutvuda isikuandmete töötlemise reeglitega, siis SSO voos tekib oht, et kasutaja ei märka, kui tema andmed süsteemide vahel liiguvad. Tulenevalt asutuste ja infosüsteemide vaheliste isikuandmete töötlemise põhimõtete erisustest peab TARA SSO autentimise voos sisse tooma kasutaja volituse küsimise sammu. Volituse lehega säilib kasutajal võimalus sisselogimisest keelduda.

Uue SSO lahenduse välja töötamise üheks oluliseks nõudmiseks on võimalikult suur tagasiühilduvus olemasoleva TARA lahendusega. Keskse sisselogimisega TARA teenusest huvitatud asutused peaksid selle saama implementeerida võimalikult väikese vaevaga. Kuna olemas olev TARA teenus töötab OIDC protokollil ja on laialdaselt kasutusele võetud, on põhjendatud ka TARA SSO teenuse puhul järgida sama protokollistikku.

### Asutusteülene ja asutusesisene SSO

Analüüsi käigus keskendume riigi autentimisteenusele keskse seansihalduse funktsionaalsuse lisamisele. Riigi autentimisteenus tegeleb kasutaja autentimisega ega tegele kasutaja autoriseerimisega (tema esindusõigustega). See tähendab, et autentimisteenus keskendub pigem kodaniku vaatele, mitte niivõrd ametniku vaatele. Riikliku autentimisteenuse kontekstis ei oma suurt tähtsust, kas klientrakendused asuvad ühe asutuse piires või mitme erineva asutuse haldusalas. Riigi autentimisteenus saab kõikidele SSOga liidestatud klientrakendustele pakkuda autentimise ja seansihalduse teenust samadel alustel.

Koostatud protokolli ja vaadeldud arhitektuuri puhul on arvestatud, et kui asutuse või haldusala piires on erisused autentimisel tagastatavate andmete osas, peavad need olema lahendatud asutusesisese SSO abil. Näiteks tagastab praegune TARA teenus e-posti aadressi ainult siis, kui kasutaja valib autentimisvahendiks ID-kaardi. Kuna iga klientrakendus saab TARA autentimise päringuga juhtida autentimisvahendite valikut sõltumata teistest klientrakendustest, saavad klientrakendused üksteisest sõltumatult juhtida ka seda, millised isikuandmed identsustõendi koosseisu jõuavad. TARA SSO puhul paraku pärast seansi alguses tehtud autentimist enam isikuandmete koosseis muutuda ei saa. Kui SSO seanssi ei alustatud ID-kaardiga, pole e-posti aadressi sinna hiljem võimalik lisada. Sellest tulenevalt on protokolli koostamisel arvestatud, et TARA SSO klientrakendus võib olla nii asutusesisene SSO lahendus kui mistahes asutuse poolt pakutav teenuseportaal. TARA SSO server saaks vajadusel isikuandmeid klientrakendustele väljastada OIDC protokolli kohase kasutajainfo (UserInfo) otspunkti kaudu. See aga teeks TARA SSO protokolli keerukamaks ja suurendaks liidestamiskulu.

Intervjuudest riigiasutuste IT-spetsialistidega sai ka kinnitust, et asutusesisese lahenduse erisused puudutavad pigem autoriseerimist kui autentimist. Praegu on suurematel asutustel juba endal kasutusel kesksed autoriseerimise süsteemid. Autoriseerimise reeglite ühtlustamine riiklikul tasandil ei mahu TARA SSO skoopi ning nõuab eraldi põhjalikumat analüüsi valdkondade ja asutuste nõuete osas.

Märkus: analüüsi käigus käsitlesime alternatiivset SSO andmemudelit, mille puhul klientrakendusi saaks TARA SSOs omavahel grupeerida. Klientrakenduste grupi sees toimuks autentimine ainult üks kord ning grupist välja logides tühistatakse kõik selle grupiga seotud seansid. Kui grupi sees on ühe rakenduse kohta SSO seansi jooksul kasutaja juba andnud volituse sisse logida, siis teiste puhul seda enam küsima ei peaks. Sellise mudeli rakendamisel aga oleksime kaotanud eelise, mida pakub kasutaja volituse küsimise ühtlustatud loogika, mida kirjeldame peatükis "Kasutaja teavitamine enne isikuandmete jagamist klientrakendusega". Lähtudes TARA SSO kõrgetest turvanõuetest ja kasutusmugavusest, otsustasime selle mudeliga mitte edasi liikuda.

### eesti.ee seansi üleandmine

Riigiportaalis eesti.ee on varasemalt kasutatud nii-öelda seansi üleandmise funktsionaalsust (Viited, [TARA Legacy]). Vana seansi üleandmise protokoll ei ole standardiseeritud ja nõuab teenusepakkujalt märkimisväärset tarkvaraarendust. Seansi üleandmiseks vahetatakse taustakanalis (üle X-tee) autoriseerimise infot teenuste serverkomponentide vahel ning eduka autoriseerimise korral suunatakse kasutaja sirvik koos unikaalse autoriseerimiskoodiga teise teenusepakkuja portaali. Suunamise järel tekitatakse automaatselt kasutajat vastuvõtvas teenuses seanss. Loodud seanss ei ole tehniliselt enam eesti.ee seansiga ühenduses.

TARA SSOd implementeerides saab seansi üleandmise kasutusjuht täidetud. OIDC protokollil põhinev keskne seansihaldusteenus pakub sama funktsionaalsust juba standardiseeritud kujul. Seansi üleandmise eelduseks on, et mõlemad osapooled on liidestatud TARA SSO teenusega. Pakutud OpenID Connect profiili järgi pole eesti.ee seansi üleandmist tarvis eraldi kasutusjuhuna käsitleda.

### Piiriülese autentimise (eIDAS) ja TARA SSO kooskasutamine

TARA SSO teenuse näol on tegemist riigi autentimisteenuse vahendusteenusega. Kõik autentimise meetodid, mis on võimalikud TARA puhul, on võimalikud ka TARA SSO puhul. Kuna TARA SSO ei paku nii täpset autentimisvahendite valiku juhtimist kui praegune TARA protokoll, ei ole TARA SSO puhul ainult piiriülese autentimise lubamine enam võimalik (Viited: [TARA] "4.1.3 Autentimismeetodite valikuline kasutus" 'eidasonly' skoobi parameeter). See tähendab, et autentimisvahendite valikus on eIDAS autentimine alati lubatud.

## Arhitektuuri kirjeldus

Plaanitav TARA SSO arhitektuur mõjutab olulisel määral analüüsitava protokolli disaini. Analüüsi käigus uurisime TARA SSO implementeerimiseks kahte alternatiivset arhitektuuri mudelit.

Esimeses variandis vaatlesime kõrgvaatelist arhitektuuri, milles seansihaldus on lahendatud olemasoleva TARA teenuse ja protokolli koosseisus. Lahenduse eeliseks on TARA ja TARA SSO arenduste ja halduse ühendamine. Liidestaja vaatest eksisteeriks üks riiklik OIDC teenusepakkuja ja liitumisel valitakse ära, millises mahus protokolli kasutatakse. Kõige kriitilisem on sellise lahenduse juures muudatuste tagasiühilduvus. OIDFi pakutavad seansihalduse spetsifikatsioonide kavandid on tagasiühilduvad OIDC põhiprotokolliga (Viited [OIDC Core]). Eelanalüüs tõi TARA kontekstis välja kaks olulist kitsaskohta:

1. TARA praeguses protokollis võimaldatakse autentimisvahendi valikut juhtida klientrakenduse poolelt. Juhtimine käib skoobi parameetri abil ja määratud skoobid on üksteist välistavad. Skoobi kaudu autentimisvahendi juhtimise muutmine tuleb TARA SSOs meie hinnangul eemaldada ja seeläbi poleks TARA autentimise voog enam tagasiühilduv. Seega kujutab niisugune muudatus otsest vastuolu analüüsi nõudega TARA olemasoleva voo säilitamise kohta. Täpsem probleemi kirjeldus on toodud peatükis "Autentimise tasemete valik TARA SSO autentimisel (LoA)".

2. Eelanalüüsi käigus selgus, et seansihalduse äriloogika lisamine TARA olemas olevasse protokolli seaks piiranguid TARA tulevikuarenduste osas. Mida keerulisemaks muutub autentimise loogika TARAs, seda keerulisem on leida karbitoodet või standardteeki, mis antud hübriidtöövoogu sobivalt ka implementeeriks. Seetõttu võib kogu TARA koos SSOga lahenduse arenduskulu hoopis tõusta nii TARA SSO implementeerija kui ka liidestaja jaoks.

Teise variandina vaatasime arhitektuuri mudelit, milles käsitletakse TARA SSO teenust eraldiseisva komponendina olemasoleva TARA teenuse ees.

<p style='text-align:left;'><img src='img/SSO_joonis1.png' style='width:800px'></p>

Joonis 1: Riigi autentimisteenuse kõrgvaateline arhitektuur

Nende komponentide jaotus aitab kõige paremini tagada TARA SSO teenuse esimest kasutusjuhtu - TARA senise voo säilitamist. Kogu SSO funktsionaalsuse eraldiseisvasse rakendusse viimine tagab olemasoleva TARA voo ja protokolli maksimaalse sõltumatuse SSO teenuse arendustest. TARA teenuse vaatest on TARA SSO näol tegemist tavalise OIDC klientrakendusega. Suhtlus TARA ja TARA SSO vahel toimub üle OIDC protokolli ja on dikteeritud TARA arendustest. Arvestades, et OIDF pole kinnitanud ühtegi standardit seansihalduse kohta OIDC protokollis, on põhjendatud mitte rutata TARA protokollis mitte-standardsete muudatustesisseviimisega.

Asutuse vaatest on TARA SSO teenuse näol tegemist TARAst eraldi seisva OIDC teenusepakkujaga. TARA SSO teenus pakub eranditult ainult koos keskse seansihalduse toega OIDC teenust. See tähendab, et tavapärast TARA autentimise voogu ei ole võimalik selle teenuse kaudu kasutada. Kuna TARA ja TARA SSO vood esitavad klientrakenduste sisemisele seansihalduse keerukusele väga erinevaid nõudeid, näeme, et ühe klientrakenduse piires TARA ja TARA SSO ristkasutus ei ole praegu arvestatava tähtsusega kasutusjuhtude hulgas. Kui tulevikus tekib vajadus kasutada ühes klientrakenduses nii TARA kui ka TARA SSOd, tuleks kaaluda alternatiivseid arhitektuurilisi lahendusi, kus otsustuskoht ei ole lõppkasutaja käes. TARA SSO kasutatavuse testide tulemused tõid välja, et lõppkasutajad ei oska TARA ja TARA SSO vahel valikut teha.

Ei ole välistatud, et klientrakenduses tehakse TARA SSO tõrke korral automaatne siire TARA teenusele. Kuna TARA SSO teenus sõltub suuresti TARA teenuse käideldavusest ja TARA tõrke korral on suure tõenäosusega häiritud ka TARA SSO teenus, ei ole sellise lahenduse kasutamine ärikriitiliste teenuste puhul soovitatav. Ärikriitilistes klientrakendustes on sisselogimise tõrkekindluse suurendamiseks eelistatav lisaks TARA SSO-le teha liidestamine otse alternatiivsete autentimisteenuste pakkujatega (mobiil-ID, Smart-ID või muud teenused). Niisuguse soovituseni jõuti RIA tellitud eID infrastruktuuri tõrkekindluse analüüsis (Viited [EID FAILURE], 4. Prioritiseeritud ettepanekud).

Pakutud arhitektuuri nõrgaks küljeks võib lugeda TARA ja TARA SSO halduse keerukuse tõusu. Paratamatult hakkavad kummagi teenuse protokolli arendused omaette kulgema ja OIDC turvaparanduste korral tuleb teha muudatused mõlemas protokollis paralleelselt. Kahe kriitilise komponendi ülalpidamine suurendab haldusosakonna koormust.

Võrreldud mudelitest lugesime sobivamaks teise variandi. Eraldades TARA ja TARA SSO teenused, saame koostada protokolli, mis katab paremini TARA SSO kasutusjuhte. TARA liides ei pea muutuma ja täiendavat arendust TARA klientrakendustelt ei ole tarvis.

## TARA SSO OIDC protokoll

### Protokolli ülevaade
