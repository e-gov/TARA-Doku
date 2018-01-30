---
permalink: TehnilineKirjeldus
---

Märkus. Piiriülese autentimise (eIDAS) tugi lisatakse teenuse 3. arendusjärgu lõppedes. Arenduses olevad võimalused on tähistatud &\#128679; // // &\#128679;.

# Tehniline kirjeldus
{: .no_toc}
v 0.3, 30.01.2018

- TOC
{:toc}

Vt ka: [Sonastik](Sonastik), [Viited](Viited)

## 1 Ülevaade

Käesolev dokument esitab teenuse tehnilised omadused. Fookus on OpenID Connect protokolli rakendamisel tehtud valikutel, erinevustel ja täiendustel OpenID Connect protokolliga võrreldes. Esitatakse päringute näited. Liidestuja peab kindlasti tutvuma ka OpenID Connect protokolliga [Core]. Liidestuja peab erilist tähelepanu pöörama, et kõik protokollikohased kontrollid saaksid tehtud - turvaelemendi `state` ja kui kasutatakse, siis ka `nonce` kontroll, identsustõendi kontroll jm. 

Autentimisteenus on Riigi Infosüsteemi Ameti uus pakutav teenus, millega asutus saab oma e-teenusesse lisada mobiil-ID ja ID-kaardi kasutaja autentimise toe. &\#128679; // Arenduses on piiriülene (eIDAS-)autentimine. // &\#128679; Järgmistes arendusjärkudes lisatakse teiste autentimismeetodite (pangalingid, Smart-ID) tugi.

TARA teenuse aluseks on OpenID Connect protokoll ([Viited](Viited), [Core]), mis omakorda põhineb OAuth 2.0 protokollil. Võrreldes OpenID Connect protokolliga on tehtud järgmised valikud, erisused ja täiendused:

1. teenus toetab ainult volituskoodi (_authorization code_) kasutusvoogu.
2. kogu teave autenditud kasutaja kohta edastatakse rakendusele identsustõendis (_ID token_). Access token-it ja UserInfo otspunkti kaudu kasutaja atribuutide andmist ei toetata.
3. rakendusele edastatakse ka eIDAS autentimistase, kui see on teada (`acr` väites).
4. teenus toetab kasutajaliidese keele-eelistuse andmist autentimispäringus (`locale` parameetriga)
4. autentimismeetodi valib kasutaja autentimisteenuses.

TARA pakub nii siseriiklikku kui ka (3. arendusjärgu lõppedes) piiriülest autentimist. eIDASe kontekstis (vt joonis 1) teostab TARA kasutusvood "Eestlase autentimine Eesti e-teenuses" (joonisel - 1) ja "Eesti e-teenust kasutava välismaalase autentimine" (joonisel 3a).

<img src='img/SUURPILT.PNG' style='width:700px'>

Joonis 1. eIDAS taristu

## 2 Autentimisprotsess

1. Kasutaja on e-teenust osutavas klientrakenduses.
    - kasutaja võib olla nii eestlane kui ka välismaalane
    - kasutajale esitatakse kuva, millel on nupp "Logi sisse" vms
    - kasutaja vajutab "Logi sisse".
2. Klientrakendus suunab kasutaja TARA-teenusesse (sirviku ümbersuunamiskorralduse abil)
    - ümbersuunamis-URL-is on autentimispäring
        - autentimispäringu koostamise kohta vt jaotis [Autentimispäring](#3-autentimisparing)
    - kasutajale avaneb autentimismeetodi valiku kuva. Siin võib kasutaja:
        - valida mobiil-ID-ga autentimise (samm 3)
        - valida ID-kaardiga autentimise (samm 4)
        - valida piiriülese (eIDAS-) autentimise (samm 5)
            - sh riigi, mille eID-d ta kasutab (valib õige "lipukese")
        - pöörduda tagasi klientrakendusse.
3. Mobiil-ID-ga autentimine
    - kasutaja sisestab mobiilinumbri ja isikukoodi
    - kasutaja mobiilseadmele kuvatakse kontrollkood
    - kinnituse ootamine
    - eduka autentimise korral edasi samm 6, vea korral - samm 7 
4. ID-kaardiga autentimine
    - algab kasutajale teabe kuvamisega autentimisserdi kohta
    - kasutaja kinnitab serdivaliku
    - kasutaja sisestab PIN1-koodi.
    - eduka autentimise korral edasi samm 6, vea korral - samm 7
5. Piiriülene (eIDAS-) autentimine
6. Autenditud kasutaja
    - suunatakse tagasi klientrakendusse (vt jaotis [Tagasisuunamine](#4-tagasisuunamine))
    - klientrakendus pärib TARA serverilt identsustõendi (vt jaotis [Identsustõend](#5-identsustoend)).
        - identsustõend (_identity token_) on allkirjastatud tõend eduka autentimise kohta
            - identsustõendis sisalduvad autentimisel tuvastatud, kasutaja andmed (atribuudid)
    - klientrakendus annab kasutajale asjakohasel viisil teada, et sisselogimine õnnestus.
7. Veateate lehelt
    - saab kasutaja minna tagasi autentimismeetodi valikusse ja seal kas üritada uuesti, võimalik, et teise autentimismeetodiga
    - või katkestada autentimise ja minna tagasi klientrakendusse.

Kasutajal on võimalik:
- anda tagasisidet teenuse kohta
    - selleks on eraldi sakil avatav vorm, kuhu pääseb autentimismeetodi valiku kuval oleva lingi abil
- esitada vearaportit
    - selleks on eraldi avatav vorm. Enne vormi on soovitused tüüpvigade iseseisvaks lahendamiseks
- saada täiendavat teavet TARA-teenuse kohta.

## 3 Autentimispäring

Kasutaja vajutab nupule "Logi sisse" vms. Rakendus võib ka ise algatada autentimise.

Rakendus moodustab OpenID Connect autentimispäringu ja saadab sirvikule korralduse kasutaja suunamiseks autentimisteenusesse (HTTP _redirect_). Autentimispäringu näide:

````
GET https://tara.eesti.ee/authorize?

redirect_uri=https%3A%2F%2eteenindus.asutus.ee%2FCallback&
scope=openid&
state=hkMVY7vjuN7xyLl5&|
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Autentimispäringu elemendid:

| URL-i element          | kohustuslik | näide                       |  selgitus     |
|------------------------|:---------- :|-----------------------------|---------------|
| protokoll, host ja tee (_path_) | jah | `https://tara.eesti.ee/authorize` | `/authorize` on TARA-teenuse OpenID Connect-kohane autentimisotspunkt (termin 'autoriseerimine' pärineb alusprotokollist OAuth 2.0).  |
| tagasipöördumis-URL | jah | `https://eteenus.asutus.ee/tagasi` | tagasipöördumis-URL-i valib asutus ise. Tagasipöördumis-URL võib sisaldada _query_-osa. Tärgile `?` järgnevas osas omakorda `?` kasutamine ei ole lubatud. |
| autentimise skoop `scope`; toetatud on väärtus `openid` | jah | `scope=openid`               |        |
| turvakood `state` | `state=hkMVY7vjuN7xyLl5` | jah | klientrakenduse serveripool peab genereerima ja päringus esitama turvakoodi. Turvakood aitab tagada, et erinevate kasutajate autentimised ei lähe sassi ja ründaja ei saa protsessi vahele sekkuda. Turvakood peegeldatakse vastuses tagasi; klientrakendus peab kontrollima, et saab vastuses sama turvakoodi, mille saatis päringus.  |
| `response_type` | `response_type=code` | jah | autentimise tulemuse serverile edastamise viis. Toetatud on volituskoodi viis `code`. |
| rakenduse identifikaator `client_id` | jah | `client_id=58e7...` | rakenduse identifikaatori annab RIA asutusele klientrakenduse registreerimisel autentimisteenuse kasutajaks |
| kasutajaliidese keele valik `locale` | ei | `locale=et` | toetatakse keeli `et`, `en`, `ru`. Vaikimisi on kasutajaliides eesti keeles. Kasutaja saab keelt ise valida. |
| `nonce` | ei | `fsdsfwrerhtry3qeewq` | taasesitusründeid vältida aitav parameeter, vastavalt protokollile ([Viited](Viited), [Core], jaotis 3.1.2.1. Authentication Request). Parameeter `nonce` ei ole kohustuslik. |
| &\#128679; // `attrs` // &\#128679; | ei | `attrs=FirstName-FamilyName` | eIDAS-atribuudid, vt allpool. |
| &\#128679; // `loa` // &\#128679; | ei | `loa=high` | minimaalne tagatistase. Võimalikud väärtused: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). |

&\#128679; //

Välismaalase suunamisel TARA-sse autentimisele tuleb arvestada, et TARA suunab välismaalase edasi, tema koduriigi autentimisteenusesse. Sealt tulev vastus sisaldab suuremat või väiksemat hulka atribuute kasutaja kohta (nt perekonnanimi, aadress, sugu jne). Atribuudid ei tule iseenesest, vaid klientrakendus peab neid küsima.

Samuti peab arvestama, et kasutaja koduriigi autentimisteenus ja eIDAS-taristu vahepealsed sõlmed ei tarvitse toetada kõigi atribuutide edastamist. Riigid on kokku leppinud, et alati saab küsida ja teise riigi autentimisteenus on kohustatud väljastama füüsilise isiku kohta 4 atribuuti - ees- ja perekonnanimi, sünniaeg, isikukood vm identifikaator. Juriidilise isiku kohta väljastatakse alati 2 atribuuti - registrikood vm identifikaator, juriidiline nimi. Need on nn kohustuslikud atribuudid. Lisaks on kokku lepitud rida mittekohustuslikke atribuute. Need on järgmised:

Füüsiline isik

| inimloetav nimi | väljastamine kohustuslik | tehniline nimi |
|-----------------|:------------------------:|----------------|
| `FamilyName` | jah | `http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName` |
| `FirstName` | jah | `http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName` |
| `DateOfBirth` | jah | `http://eidas.europa.eu/attributes/naturalperson/DateOfBirth` |
| `PersonIdentifier` | jah | `http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier` |

| inimloetav nimi | väljastamine kohustuslik | tehniline nimi |
|-----------------|:------------------------:|----------------|
| `BirthName` | ei | `http://eidas.europa.eu/attributes/naturalperson/BirthName` |
| `PlaceOfBirth` | ei | `http://eidas.europa.eu/attributes/naturalperson/PlaceOfBirth` |
| `CurrentAddress` | ei | `http://eidas.europa.eu/attributes/naturalperson/CurrentAddress` |
| `Gender` | ei | `http://eidas.europa.eu/attributes/naturalperson/Gender` |

Juriidiline isik

| inimloetav nimi | väljastamine kohustuslik | tehniline nimi |
|-----------------|:------------------------:|----------------|
| `LegalPersonIdentifier`   | jah | `http://eidas.europa.eu/attributes/legalperson/LegalPersonIdentifier` |
| `LegalName` | jah         | `http://eidas.europa.eu/attributes/legalperson/LegalName` |

| inimloetav nimi | väljastamine kohustuslik | tehniline nimi |
|-----------------|:------------------------:|----------------|
| `LegalAddress` | ei | `http://eidas.europa.eu/attributes/legalperson/LegalPersonAddress` |
| `VATRegistration` | `http://eidas.europa.eu/attributes/legalperson/VATRegistrationNumber` |
| `TaxReference` | ei |  `http://eidas.europa.eu/attributes/legalperson/TaxReference` |
| `LEI` | ei |  `http://eidas.europa.eu/attributes/legalperson/LEI` |
| `EORI` | ei |  `http://eidas.europa.eu/attributes/legalperson/EORI` |
| `SEED` | ei |  `http://eidas.europa.eu/attributes/legalperson/SEED` |
| `SIC` | ei |  `http://eidas.europa.eu/attributes/legalperson/SIC` |
| `D-2012-17-EUIdentifier` | `http://eidas.europa.eu/attributes/legalperson/D-2012-17-EUIdentifier` |

Atribuutide küsimiseks tuleb TARA-sse suunamise URL-le lisada parameeter soovitavate atribuutide nimekirjaga:

`attrs=PersonIdentifier-FirstName-FamilyName-CurrentAddress`

Kui atribuute URL-is määratud ei ole, hoolitseb TARA ise selle eest, et välismaa autentimisteenusest küsitakse füüsilise isiku 4 kohustuslikku atribuuti (ees- ja perekonnanimi, sünniaeg, isikukood vm identifikaator).

Küsida ei ole mõtet rohkem atribuute kui e-teenuse osutamiseks vaja läheb. eIDAS-taristus autentimisel küsitakse kasutajalt nõusolekut isikuandmete saatmiseks teise riigi e-teenusele.

// &\#128679;

## 4 Tagasisuunamine

OpenID Connect protokolli kohaselt autentimisteenus suunab kasutaja tagasi rakendusse (klientrakenduse poolt kaasa antud naasmisaadressile), andes kaasa volituskoodi (_authorization code_). Tehniliselt tehakse tagasisuunamine HTTP _redirect_-päringuga. Näide:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````

Tagasisuunamispäringu elemendid:

| URL-i element          | näide                       |  selgitus     |
| tagasisuunamis-URL | `https://eteenus.asutus.ee
/tagasi?` | ühtib autentimispäringus saadetuga |
| volituskood `code` | `code=71ed579...`  | ingl _authorization code_. Volituskood on ühekordne “lubatäht” identsustõendi saamiseks |
| turvakood `state`            | `state=OFfVLKu0kNbJ2EZk`     |  juhusõne |

Kasutaja võib e-teenusesse tagasi pöörduda ka ilma autentimismeetodit valimata ja autentimist läbi tegemata (link "Tagasi teenusepakkuja juurde"). See võimalus on mõeldud juhuks, kui kasutaja vajutas klientrakenduses "Logi sisse", kuid tegelikult ei soovi sisse logida. Teenusega liitumise taotluses peab asutus RIA-le teada andma URL-i, kuhu kasutaja "Tagasi teenuspakkuja juurde" vajutamisel suunatakse. NB! OpenID Connect protokolli kohane tagasisuunamis-URL ja siin nimetatud URL on erineva tähendusega.

## 5 Identsustõend

Tagasisuunamise (vt eelmine jaotis) järel küsib klientrakendus küsib TARA serverilt identsustõendi (_ID token_).

Päringus tuleb anda `Authorization` päis, väärtusega, mis moodustatakse sõnast `Basic`, tühikust ja Base64 kodeeringus stringist `<client_id>:<client_secret>` (vt RFC 2617 HTTP Authentication: Basic and Digest Access Authentication, jaotis 2 Basic Authentication Scheme).

Päringu kehas tuleb esitada:

| POST päringu keha element | näide                    |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.ria.ee/token` |   |
| `grant_type`  | `grant_type=authorization_code` | nõutav väärtus `authorization_code` |
| `code` | `code=Splx...` | autentimisteenuselt saadud volituskood | 
| `redirect_uri` | `redirect_uri=https%3A%2F` | autentimispäringus saadetud ümbersuunamis-URI |
| 

Näide:

````
POST /token HTTP/1.1
Host: tara.ria.ee/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi
````

TARA server kontrollib, et identsustõendit küsib õige rakendus, seejärel koostab identsustõendi ning tagastab selle klientrakendusele. Näide:

````json
{  
   "aud": "openIdDemo",
   "sub": "EE11412090004",
   "nbf": 1505818497,
   "amr":[  
      "mID"
   ],
   "iss":"https://tara.ria.ee",
   "profile_attributes":{  
      "given_name":"MARY ÄNN",
      "family_name":"O’CONNEŽ-ŠUSLIK",
      "mobile_Number":"+37200000766"
   },
   "state":"abcdefghijklmnop",
   "exp":1505847597,
   "iat":1505818797,
   "nonce":"qrstuvwxyzabcdef",
   "jti":"0e12bf29-2a3b-4a81-a85e-973d0a2303d1"
}
````

Identsustõendis esitatakse järgmised väljad (_claims_).

| identsustõendi element | näide, selgitus     |
|:-----:|-------------------------------------------|
| `aud`                  | `"aud":"openIdDemo"` - autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `sub`                  | `"sub":"EE11412090004"` - autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator). Isikukood antakse eesliitega `EE`  |
| `nbf` (_Not Before_)   | `"nbf":"Wed Sep 27 11:47:22 EEST 2017"` - tõendi kehtivuse algusaeg |
| `amr` (_Authentication Method Reference_) | `"amr":["mID"]` - kasutaja autentimiseks kasutatud autentimismeetod. Võimalikud väärtused: `mID` - mobiil-ID, `idcard` - Eesti ID-kaart, `eIDAS` - piiriülene |
| `iss`              | `"iss":"https://tara.ria.ee"` - tõendi väljastaja (TARA-teenus); testteenuse puhul `"iss":"https://tara-test.ria.ee"` |
| `profile_attributes`   | `"profile_attributes": {"given_name":"MARY ÄNN", "family_name":"O’CONNEŽ-ŠUSLIK", "mobile_number":"+37200000766"}` - autenditud isikut kirjeldavad andmed &\#128679; // sh eIDAS atribuudid (vt ka allpool esindamise kohta) // &\#128679; |
| `given_name`              | `"given_name":"MARY ÄNN"` - autenditud kasutaja eesnimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `family_name`              | `"family_name":"O’CONNEŽ-ŠUSLIK"` - autenditud kasutaja perekonnanimi (testnimi, valitud täpitähtede jm eritärkide sisalduvuse pärast) |
| `mobile_number`          | `"mobile_number":"+37200000766"` - m-ID kasutaja autentimisel kasutatud telefoninumber |
| `state`            | `"state":"abcdefghijklmnop"` - turvaelement  |
| `exp`              | `"exp":1505847597` - tõendi aegumisaeg |
| `iat`              | `"iat":1505818797` - tõendi väljaandmisaeg |
| `nonce`                 | `"nonce":"qrstuvwxyzabcdef"` - turvaelement |
| `acr` Authentication Context Class Reference) | `"acr": "low"` - autentimistase, vastavalt eIDAS tasemetele. Võimalikud väärtused: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Elementi ei kasutata, kui autentimistase ei kohaldu või pole teada |
| `jti`              | `"jti":"0e12bf29... "` - identsustõendi identifikaator |

&\#128679; // Esindaja andmed eIDAS-autentimisel

Küsida ei saa, kuid võidakse väljastada:

füüsilise isiku esindaja atribuudid

| inimloetav nimi | tehniline nimi |
|---------------------------|-----------------------------------|
| `RepresentativeBirthName` | `http://eidas.europa.eu/attributes/naturalperson/representative/BirthName` |
| `RepresentativeCurrentAddress` | `http://eidas.europa.eu/attributes/naturalperson/representative/CurrentAddress` |
| `RepresentativeFamilyName` | `http://eidas.europa.eu/attributes/naturalperson/representative/CurrentFamilyName` |
| `RepresentativeFirstName` | `http://eidas.europa.eu/attributes/naturalperson/representative/CurrentGivenName` |
| `RepresentativeDateOfBirth` | `http://eidas.europa.eu/attributes/naturalperson/representative/DateOfBirth` |
| `RepresentativeGender` | `http://eidas.europa.eu/attributes/naturalperson/representative/Gender` |
| `RepresentativePersonIdentifier` | `http://eidas.europa.eu/attributes/naturalperson/representative/PersonIdentifier` |
| `RepresentativePlaceOfBirth` | `http://eidas.europa.eu/attributes/naturalperson/representative/PlaceOfBirth` |

juriidilise isiku esindaja atribuudid

| inimloetav nimi | tehniline nimi |
|---------------------------|-----------------------------------|
| `RepresentativeD-2012-17-EUIdentifier` | `http://eidas.europa.eu/attributes/legalperson/representative/D-2012-17-EUIdentifier` |
| `RepresentativeEORI` | `http://eidas.europa.eu/attributes/legalperson/representative/EORI` |
| `RepresentativeLEI` | `http://eidas.europa.eu/attributes/legalperson/representative/LEI` |
| `RepresentativeLegalAddress` | `http://eidas.europa.eu/attributes/legalperson/representative/LegalAddress` |
| `RepresentativeLegalName` | `http://eidas.europa.eu/attributes/legalperson/representative/LegalName` |
| `RepresentativeLegalAddress` | `http://eidas.europa.eu/attributes/legalperson/representative/LegalPersonAddress` |
| `RepresentativeLegalPersonIdentifier` | `http://eidas.europa.eu/attributes/legalperson/representative/LegalPersonIdentifier` |
| `RepresentativeSEED` | `http://eidas.europa.eu/attributes/legalperson/representative/SEED` |
| `RepresentativeSIC` | `http://eidas.europa.eu/attributes/legalperson/representative/SIC` |
| `RepresentativeTaxReference` | `http://eidas.europa.eu/attributes/legalperson/representative/TaxReference` |
| `RepresentativeVATRegistration` | `http://eidas.europa.eu/attributes/legalperson/representative/VATRegistration` |
| `RepresentativeVATRegistration` | `http://eidas.europa.eu/attributes/legalperson/representative/VATRegistrationNumber` |

// &\#128679;

Identsustõend võib sisaldada muid OpenID Connect protokolli kohaseid välju, kuid neid teenuses ei kasutata. 
JWT väljade tähenduse kohta vt vajadusel [https://www.iana.org/assignments/jwt/jwt.xhtml](https://www.iana.org/assignments/jwt/jwt.xhtml).
Kui identsustõendit ei pärita `5` minuti jooksul, siis identsustõend aegub ja autentimisprotsessi tuleb korrata.

Rakendus loob saadud identsustõendi alusel kasutajaga seansi. Seansi loomine ja pidamine on rakenduse kohustus. Kuidas seda teha, ei ole autentimisteenuse skoobis.

## 6 Otspunktid

Testteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) |  [https://tara-test.ria.ee/oidc/.well-known/openid-configuration](https://tara-test.ria.ee/oidc/.well-known/openid-configuration) &#9989; |
| teenuse avalik allkirjastamisvõti | [https://tara-test.ria.ee/oidc/jwks](https://tara-test.ria.ee/oidc/jwks) &#9989; |
| kliendi registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | `https://tara-test.ria.ee/oidc/authorize` | 
| tõendiväljastus (_token_) | `https://tara-test.ria.ee/oidc/token` | 

Toodanguteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara.ria.ee/oidc/jwks` |
| kliendi registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | `https://tara.ria.ee/oidc/authorize` | 
| tõendiväljastus (_token_) | `https://tara.ria.ee/oidc/token` | 

## Muutelugu

| Versioon, kuupäev | Muudatus |
|-----------------|--------------|
| 0.3, 30.01.2018   | Lisatud piiriülene autentimine (eIDAS) |
| 0.2, 28.11.2017   | Lisatud ID-kaardiga autentimine |
| 0.1, 10.10.2017   | Mobiil-ID-ga autentimine. |

