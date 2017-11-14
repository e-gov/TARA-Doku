---
permalink: Jatkusuutlikkus
---

# TARA tarkvara jätkusuutlikkus
{: .no_toc}

- TOC
{:toc}

## Mõisted

- _eIDAS konnektorteenus_, _eIDAS Connector Service_, piiriülese eIDAS piiriülese autentimistaristu element, üks kahest eIDAS-sõlme (_eIDAS-Node_) osateenusest, edastab välismaalase autentimiseks tema identiteedisüsteemi koduriiki, võtab vastu autentimise tulemuse ja edastab (TARA-teenuse kaudu või otse) Eesti e-teenusesse.
- _eIDAS vahendusteenus_, _eIDAS Proxy Service_, piiriülese eIDAS piiriülese autentimistaristu element, üks kahest eIDAS-sõlme (_eIDAS-Node_) osateenusest, võtab vastu välisriigist saabunud autentimispäringu, edastab selle siseriiklikule autentimisteenusele ja saadab välisriiki autentimistoimingu tulemuse.
- _siseriiklik autentimisteenus_, eIDAS piiriülese autentimistaristu element, autendib välisriigist autentimisele saadetud kasutaja. Suhtleb eIDAS vahendusteenusega.

Lisaks kasutame [TARA sõnastiku](https://e-gov.github.io/TARA-Doku/Sonastik) mõisteid.

## Praegune tarkvara-stack

TARA arendus algas septembris 2017, ID-kaardi turvanõrkusest tingitud kriisitööde raames. Alustasime OpenID Connect teegi valikust. Kaalumisel oli kaks alternatiivi:
  - Apereo CAS [4]
  - MITRE Corporation ja MIT Internet Trust Consortiumi MITREid Connect [5].

Nimetatud tarkvarade põhjalikum iseloomustus ei mahu käesoleva memo raamidesse. Lühidalt: mõlemad tarkvarad on kirjutatud Javas. MITRE on OpenID Connect sertifitseeritud, CAS ei ole. CAS on laiaulatuslik tarkvara, OpenID Connect on üks mitmetest CAS-i teostatud protokollidest. CAS-i koosseisus olev OpenID Connect teostus põhineb MITRE lahendusel. Kokkuvõttes, nii CAS kui ka MITRE pakuvad OpenID Connect-i, kuid CAS pakub veel palju muud.

Arendaja soovitusel sai valitud CAS, kuna sellega oli arendaja kogemus ja suuremad väljavaated teenuse nõutav miinimumkomplekt pingeliseks tähtajaks valmis seada.

Tagantjärele vaadates oli see õige otsus. Mobiil-ID-ga ja seejärel ka ID-kaardiga autentimine saadi 3-4 nädalaga tööle.

## Jätkusuutlikkuse küsimus

Siiski on TARA tarkvara jätkusuutlikkuse tagamiseks vaja uurida ja hinnata, kas valitud tarkvara-stack on jätkusuutlik.

Kavas on TARA-teenust laiendada. Esimese tööna lisame eIDAS piiriülese autentimise, seejärel, vastavalt enne läbiviidava uuringu tulemustele ühekordse sisselogimise, võimalik, et ka täiendavaid autentimismeetodeid.

Laiendamise kavast annab ülevaate ettekanne ["Piiriülene autentimine"](https://e-gov.github.io/TARA-Doku/PiiriyleneAutentimine.pdf), kuva 20.

Jätkusuutlikkus tähendab, et:
  - tarkvara täiendamisel ei kasva haldus- ja arenduskulud eksponentsiaalselt
    - seejuures oleme kaitstud arendaja või arendaja võtmeisiku vahetumise vastu
  - tarkvara on kaitstud alustarkvara elutsüklimuutuste vastu
    - See tähendab, et CAS-i uue versiooni ilmumisel me ei pea kandma suurt uuendamiskulu.

Jätkusuutlikkus on seotud ka sellega, et turvaprobleemide ilmnemisel oleme suutelised neid uurima ja tarkvaras kiiresti vajalikke muudatusi tegema.

Senine töö on ilmsiks toonud mitmed murettekitavad nähtused:
  - esimene programmeerija tundis CAS-i hästi; hiljem esimese välja vahetanud teine programmeerija, kes on küll teinud autentimislahendusi, tunneb hästi Java Spring ja Spring Boot raamistikke, ütleb, et CAS on keeruline, selles orienteerumine nõuab palju aega. (Kuid CAS olevat korralikult kirjutatud).
  - CAS-i kohandamine meie vajadusteks on osutunud keerukaks, mitmetes kohtades, nt otspunktide (URL-ide) ja logimise juures ei saa teha muudatusi, kuna CAS-i sisemuses - mida me ei tunne - läheb siis midagi katki.
  - Kui muudatused ka oleks teostatavad, tekib küsimus, kas seda on mõtet teha, sest nii läheksime "tootest" kaugele. Ei ole selge, kuidas ja millise arenduskuluga CAS-i versiooniuuenduse ilmumisel muudatused uude versiooni kanda.
  - OpenID Connect on CAS-is üks mitmetest protokollidest. CAS-i alusprotokoll on CAS-i enda protokoll (mis veel 5-6 aastat tagasi oli kehvalt dokumenteeritud. Nüüd on seis parem). Seetõttu on CAS-i logiteated CAS-i protokolli terminites. Semantiline lisakiht ei hõlbusta mõistmist.
  - CAS-i laiendused ei tule lihtsasti mõistetavad ega ilusad. Nt kasutajaliidese olekumasin (Spring Webflow).
  - meile mittevajalikke CAS-i osi ja käitumisi on olnud komplitseeritud välja lülitada.

Kokkuvõttes on reaalne oht, et "toode" hakkab draivima meie vajadusi.

Märkus. Siinkohal on mõtet nimetada Tomas Petriceki head kirjutist raamistiku ja teegi erinevusest [6]. Raamistik tähendab, et meie, kasutades raamistiku pakutavaid laienduspunkte (_extension points_), asetame oma koodi raamistiku sisse. Teegi kasutamisel, vastupidi, asetame teegi pakutavaid komponente oma rakenduse sisse.

Peame mõtlema, mida vajame ja kuidas seda meile jõukohaste arendus- ja halduskuludega teostada. 

Ühelt poolt:
  - me ei vaja CAS-i.
    - See, et CAS-i on riigis kasutatud, teadaolevalt on RMIT-is neid püsti pandud isegi mitu. Teateid on CAS-i kasutamisest RIK-is. SMIT-is on CAS kasutusel (kuid SSO ei ole juurdunud). KEMIT on CAS-le lisanud ID-kaardiga ja mobiil-ID-ga autentimise. KEMIT-i CAS lahenduse kasutamist kaaluvat ka Maanteeamet.
    - oleme protokolliks valinud OpenID Connect-i. Tuleks minna otseteed.
    - on suur, risk, kui me ei saa tööriistast täielikult aru.
      - Sellisel pinnal ehitatud lahendused jäävad paratamatult kipakaks, _engineering_-lahenduse asemel MacGyver lahendus.

Teiselt poolt:
  - kui CAS võimaldab meie vajadused täita alternatiividega võrreldes odavamalt &mdash; teenuse elutsükli kogukulu (__Total Cost of Ownership_) mõistes, siis pole probleemi.  

## Mida vajame?

TARA teenus peab:
1. pakkuma TARA teenuse klientrakendustele OpenID Connect protokolli kohast liidest, toetatud voo (_flow_) ja omaduste ulatuses.
    Praegu on toetatud volituskoodi voog, sh identsustõendi väljastamine. Tõenäoliselt lisame hiljem ühekordse sisselogimise (SSO) toe.
2. teostama toetatud autentimismeetodid, kas ise või kasutades autentimisteenuse pakkujat.
    Praegu on teostatud ID-kaardiga autentimine, sh kehtivuskinnituspäring SK OCSP teenusesse ja mobiil-ID-ga autentimine (SK DigiDocService teenuse abil).
3. Eesti e-teenust kasutava välismaalase autentimine, eIDAS taristu abil.
    See tähendab SAML autentimispäringu koostamist ja saatmist (veebisirvija ümbersuunamiskorraldusega) RIA konnektorteenusesse, samuti SAML autentimisvastuse vastuvõtmist, lahtivõtmist ja valideerimist RIA konnektorteenusest.

Lisaks nende funktsioonide täitmiseks vajalik:
1. teenuse kasutajate haldus
2. teenuse andmesalvestus
    nt koostatud identsustõendite säilitamine, seansside säilitamine (ühekordse sisselogimise lisamisel)
3. logimine
4. kasutusstatistika tootmine.

Tarkvaravajadustesse tõlgituna tähendab see:
- OpenID Connect serveri teegi (või toote)
- SAML kliendi teegi
- autentimistõendite ja sessioonide hoidmise andmebaasi kasutamist.

## Komisjoni väljatöötatud tarkvara

Peame arvesse võtma ka Euroopa Komisjoni tarkvara. Euroopa Komisjon on välja töötanud tarkvara, eIDAS tehnilise spetsifikatsiooni nn etalonteostuse [1]. See tarkvara koosneb neljast komponendist:
1. eIDAS konnektorteenuse tarkvara
2. eIDAS vahendusteenuse tarkvara
3. e-teenuse demorakendus (_Demo Service Provider_)
4. autentimise demorakendus (_Demo Identity Provider_).

Komponendid 1 ja 2 (konnektorteenus ja vahendusteenus) koos kannavad nime eIDAS-Node.

Lisaks avaldas Komisjon ktoobris 2017 konnektorteenuste ja eIDAS siseriiklike autentimisteenuste ehitajatele suunatud juhendi "eIDAS Node National IdP and SP Integration Guide" [2]. Juhendis antakse nõu, kuidas konnektorteenuse tarkvarale siseriiklikku otsa ehitada, sh kuidas ülalnimetatud demorakenduste osi soovi korral ära kasutada.

Liikmesriik ei ole kohustatud Komisjoni tarkvara kasutama. Tähtis on teostada eIDAS tehnilisele spetsifikatsioonile [3] vastav liides.

Meie plaan on kasutada eIDAS Node tarkvara.

## Alternatiivid

1. Jätkata CAS-ga.<br>
  1a teha fork.<br>
  1b hoida meie lisandused sellised, et (tõenäoliselt) saaksime mõistliku arenduskuluga kasutada CAS-i tulevasi versiooniuuendusi.
2. Lülituda ümber tarkvarale, mida suudame mõista ja  kohandada täpselt nii, nagu tahame (1).<br>
  2a MITREid Connect + mõni SAML-i kliendi teek<br>
  2b mõni muu tarkvarakomplekt.<br>

(1) Võib olla on see üldse saavutamatu ideaal. Windows 10, Android uued versioonid tulevad, tahame seda või ei taha; need tarkvarad ole ei täielikult hoomatavad ega saa neid ka täiesti oma tahtmise järgi seada.
{:.vaike }

## Võrdluskogemus

Minu kogemus on individuaalne ja paratamatult piiratud. Mõned punktid siiski:
  - tööriista tundmaõppimine võtab aega. Näiteks Jekylli koos seonduvate tehnoloogiatega omandasin u aasta.
  - "võimas", paljude omadustega tööriist ei tarvitse olla parim. Näiteks OpenID Connect klientrakenduse kirjutamiseks ei ole vaja spetsiaalselt teeki. Piisab tavalistest programmeerimiskeele võimalustest ja standardsetest teekidest (HTTP, JWT teek). Võib leida OpenID Connect klienditeeke, kus eesmärgiks on olnud programmeerimiskeelte uute võimaluste demonstreerimine ja protokolli kõigi võimaluste lahendamine; peab mõtlema, kas vajad kõike seda.
  - tööriist ei tohi tööd tõkestada, selles mõttes, et seab piiranguid või kirjutab ette, mida teha. Nt Jekyll-is ei ole ükski konstruktsioon kohustuslik - võib kirjutada puhta HTML-i ja Jekyll laseb selle läbi.
  - tarkvara _hoarding_ pole hea mõte. See, et tasuta saab, pole kogu tõde ega alati kasulik.
  - keerukuse, aga ka turbe seisukohalt on hea praktika tarkvarasse võtta ainult see, mida vajame (vrdl `import` ja `require` laused).
  - esimene valik pole alati parim. Node.js platvormil olen teegi mitu korda välja vahetanud. (Hea indikaator on npm näidatav teegi kasutusstatistika).

## Kokkuvõte

Peame mõtlema ja arutama. Ei ole ka ainult nii, et tellija ütleb, kuidas tahab. Tähtis on ka kuidas arendajale töötada meeldiks.

## Viited

[1] eIDAS-Node software releases. [https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS-Node+-+Current+release](https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS-Node+-+Current+release).<br>
[2] eIDAS Node National IdP and SP Integration Guide. v 1.0 16.10.2017, [link dokumendile](https://ec.europa.eu/cefdigital/wiki/download/attachments/46992189/eIDAS-Node%20National%20IdP%20and%20SP%20Integration%20Guide.pdf?version=1&modificationDate=1507819905716&api=v2).<br>
[3] eIDAS Technical specification v.1.1, [https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS+Profile](https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS+Profile).<br>
[4] Apereo CAS. https://github.com/apereo/cas.<br>
[5] MITREid Connect. [https://github.com/mitreid-connect/](https://github.com/mitreid-connect/).<br>
[6] Tomas Petricek (2015) „Library patterns Why frameworks are evil“, [http://tomasp.net/blog/2015/library-frameworks/](http://tomasp.net/blog/2015/library-frameworks/).<br>

