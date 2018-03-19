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

Käesolev dokument esitab teenuse tehnilised omadused, samuti annab soovitusi e-teenuse liidestamiseks.

Fookus on OpenID Connect protokolli rakendamisel tehtud valikutel, erinevustel ja täiendustel OpenID Connect protokolliga võrreldes. Esitatakse päringute näited. Liidestuja peab kindlasti tutvuma ka OpenID Connect protokolliga [Core]. Liidestuja peab erilist tähelepanu pöörama, et kõik protokollikohased kontrollid saaksid tehtud - turvaelemendi `state` ja kui kasutatakse, siis ka `nonce` kontroll, identsustõendi kontroll jm. 

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
    - kasutaja valib sihtriigi
    - kasutaja suunatakse läbi eIDAS Node võrgu sihtriigi autentimissüsteemi
    - kasutaja autendib ennast sihtriigi autentimisvahendiga
    - eduka autentimise korral (ning kui sihtriigi autentimisvahendi tase on piisav) edasi samm 6, vea korral - samm 7
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

Rakendus moodustab OpenID Connect autentimispäringu ja saadab sirvikule korralduse kasutaja suunamiseks autentimisteenusesse (HTTP _redirect_). Autentimispäringu näide (URL-i _query_-osa on loetavuse huvides jagatud mitmele reale):

````
GET https://tara.ria.ee/oidc/authorize?

redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback&
scope=openid&
state=hkMVY7vjuN7xyLl5&|
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Autentimispäringu elemendid:

| URL-i element          | kohustuslik | näide                       |  selgitus     |
|------------------------|:---------- :|-----------------------------|---------------|
| protokoll, host ja tee (_path_) | jah | `https://tara.ria.ee/oidc/authorize` | `/authorize` on TARA-teenuse OpenID Connect-kohane autentimisotspunkt (termin 'autoriseerimine' pärineb alusprotokollist OAuth 2.0).  |
| tagasi-pöördumis-URL `redirect_uri` | jah | `redirect_uri=https%3A%2F%2Feteenus.asutus.ee/tagasi` | tagasipöördumis-URL-i valib asutus ise. Tagasipöördumis-URL võib sisaldada _query_-osa. Tärgile `?` järgnevas osas omakorda `?` kasutamine ei ole lubatud. Vajadusel kasutada [URLi kodeerimist](https://en.wikipedia.org/wiki/Percent-encoding) |
| autentimise skoop `scope` | jah | `scope=openid` | kohustuslik on väärtus `openid`; <br><br>ülepiirilise autentimise korral on võimalik lisada täpsustavaid lisaväärtusi täiendavate isikuandmete pärimiseks. Lisaväärtused tuleb eraldada tühikuga. Tühikud esitada kasutades URL kodeeringut (RFC 3986). Näide: `scope=openid%20eidas:place_of_birth%20eidas:gender`        |
| turvakood `state` | jah | `state=hkMVY7vjuN7xyLl5` | klientrakenduse serveripool peab genereerima ja päringus esitama turvakoodi. Turvakood on igas päringus erinev, sobiva pikkusega - nt 16 sümbolit - juhusõne, mis aitab tagada, et erinevate kasutajate autentimised ei lähe sassi ja ründaja ei saa protsessi vahele sekkuda. Turvakood peegeldatakse vastuses tagasi; klientrakendus peab kontrollima, et saab vastuses sama turvakoodi, mille saatis päringus.  |
| `response_type` | jah | `response_type=code` | määrab autentimise tulemuse serverile edastamise viisi. Toetatud on volituskoodi viis (OpenID Connect protokolli _authorization flow_), selle tähiseks on väärtus `code`. |
| rakenduse identifikaator `client_id` | jah | `client_id=58e7...` | rakenduse identifikaatori annab RIA asutusele klientrakenduse registreerimisel autentimisteenuse kasutajaks |
| kasutajaliidese keele valik `locale` | ei | `locale=et` | toetatakse keeli `et`, `en`, `ru`. Vaikimisi on kasutajaliides eesti keeles. Kasutaja saab keelt ise valida. |
| `nonce` | ei | `fsdsfwrerhtry3qeewq` | taasesitusründeid vältida aitav unikaalne parameeter, vastavalt protokollile ([Viited](Viited), [Core], jaotis 3.1.2.1. Authentication Request). Parameeter `nonce` ei ole kohustuslik. |
| `acr_values` | ei | `acr_values=substantial` | minimaalne nõutav autentimistase vastavalt eIDAS tasemetele. Lubatud määrata üks väärtus järgmisest loetelust: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Kui määramata, siis vaikimisi high. |


&\#128679; //

### Lisainformatsiooni küsimine välisriigi kodaniku puhul

Välisriigi isiku puhul on võimalik pärida täiendavaid isikuandmeid. Lisaandmete küsimiseks tuleb TARA-sse suunamise URL-s täiendada scope parameetri väärtust soovitud isikuandmete atribuutidega. Loetelu kõigist võimalikest lisaväärtustest on toodud identsustõendi peatükis.

Välismaalase suunamisel TARA-sse autentimisele tuleb arvestada, et TARA suunab välismaalase edasi tema koduriigi autentimisteenusesse. Sealt tulev vastus sisaldab suuremat või väiksemat hulka atribuute kasutaja kohta (nt perekonnanimi, aadress, sugu jne). Atribuudid ei tule iseenesest, vaid klientrakendus peab neid küsima.

Samuti peab arvestama, et kasutaja koduriigi autentimisteenus ja eIDAS-taristu vahepealsed sõlmed ei tarvitse toetada kõigi atribuutide edastamist. Riigid on kokku leppinud, et alati saab küsida ja teise riigi autentimisteenus on kohustatud väljastama füüsilise isiku kohta 4 atribuuti - ees- ja perekonnanimi, sünniaeg, isikukood vm identifikaator. Juriidilise isiku kohta väljastatakse alati 2 atribuuti - registrikood vm identifikaator, juriidiline nimi. Need on nn kohustuslikud atribuudid. Lisaks on kokku lepitud rida mittekohustuslikke atribuute. Need on järgmised:

Kui atribuute URL-is määratud ei ole, hoolitseb TARA ise selle eest, et välismaa autentimisteenusest küsitakse füüsilise isiku 4 kohustuslikku atribuuti (ees- ja perekonnanimi, sünniaeg, isikukood vm identifikaator).

Küsida ei ole mõtet rohkem atribuute kui e-teenuse osutamiseks vaja läheb. eIDAS-taristus autentimisel küsitakse kasutajalt nõusolekut isikuandmete saatmiseks teise riigi e-teenusele.

Näide scope parameetri kasutusest:
`scope=openid%20eidas:legal_person_identifier%20eidas:legal_address%20eidas:lei`

&\#128679; //

## 4 Tagasisuunamine

OpenID Connect protokolli kohaselt autentimisteenus suunab kasutaja tagasi rakendusse (klientrakenduse poolt kaasa antud naasmisaadressile), andes kaasa volituskoodi (_authorization code_). Tehniliselt tehakse tagasisuunamine HTTP _redirect_-päringuga. Näide:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````

Tagasisuunamispäringu elemendid:

| URL-i element          | näide                       |  selgitus     |
| protokoll, host ja tee (_path_) | `https://eteenus.asutus.ee/tagasi` | ühtib autentimispäringus saadetud `redirect_uri` väärtusega |
| `code` | `code=71ed579...`  | volituskood (_authorization code_) on ühekordne “lubatäht” identsustõendi saamiseks |
| `state`            | `state=OFfVLKu0kNbJ2EZk`     | turvakood. Autentimispäringus saadetud turvakood peegeldatakse tagasi |

Kasutaja võib e-teenusesse tagasi pöörduda ka ilma autentimismeetodit valimata ja autentimist läbi tegemata (link "Tagasi teenusepakkuja juurde"). See võimalus on mõeldud juhuks, kui kasutaja vajutas klientrakenduses "Logi sisse", kuid tegelikult ei soovi sisse logida. Teenusega liitumise taotluses peab asutus RIA-le teada andma URL-i, kuhu kasutaja "Tagasi teenuspakkuja juurde" vajutamisel suunatakse. NB! OpenID Connect protokolli kohane tagasisuunamis-URL ja siin nimetatud URL on erineva tähendusega.

## 5 Identsustõend

Tagasisuunamise (vt eelmine jaotis) järel küsib klientrakendus küsib TARA serverilt identsustõendi (_ID token_).

Päringus tuleb anda `Authorization` päis, väärtusega, mis moodustatakse sõnast `Basic`, tühikust ja Base64 kodeeringus stringist `<client_id>:<client_secret>` (vt RFC 2617 HTTP Authentication: Basic and Digest Access Authentication, jaotis 2 Basic Authentication Scheme).

Päringu kehas tuleb esitada:

| POST päringu keha element | näide                    |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.ria.ee/oidc/token` |   |
| `grant_type`  | `grant_type=authorization_code` | nõutav väärtus `authorization_code` |
| `code` | `code=Splx...` | autentimisteenuselt saadud volituskood | 
| `redirect_uri` | `redirect_uri=https%3A%2F` | autentimispäringus saadetud ümbersuunamis-URI |
| 

Näide:

````
POST /token HTTP/1.1
Host: tara.ria.ee/oidc/token
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

| identsustõendi element | kohustuslik | näide, selgitus     |
|:-----------------------|:-----------:|---------------------|
| `aud`                  | jah |`"aud":"openIdDemo"` - autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `sub`                  | jah | `"sub":"EE11412090004"` - autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator) koos kodaniku riigikoodi eesliitega (riigikoodid vastavalt ISO 3166-1 alpha-2 standardile). |
| `nbf` (_Not Before_)   | jah |`"nbf":"Wed Sep 27 11:47:22 EEST 2017"` - tõendi kehtivuse algusaeg |
| `amr` (_Authentication Method Reference_) | jah | `"amr":["mID"]` - kasutaja autentimiseks kasutatud autentimismeetod. Võimalikud väärtused: `mID` - mobiil-ID, `idcard` - Eesti ID-kaart, `eIDAS` - piiriülene |
| `iss`              | jah | `"iss":"https://tara.ria.ee"` - tõendi väljastaja (TARA-teenus); testteenuse puhul `"iss":"https://tara-test.ria.ee"` |
| `profile_attributes`   | jah | `"profile_attributes": {"given_name":"MARY ÄNN", "family_name":"O’CONNEŽ-ŠUSLIK", "mobile_number":"+37200000766"}` - autenditud isikut kirjeldavad andmed &\#128679; // sh eIDAS atribuudid (vt ka allpool täiendavate andmete küsimise ja isiku esindamise kohta) // &\#128679; |
| `profile_attributes.given_name`              | jah | `"given_name":"MARY ÄNN"` - autenditud kasutaja eesnimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `profile_attributes.family_name`              | jah | `"family_name":"O’CONNEŽ-ŠUSLIK"` - autenditud kasutaja perekonnanimi (testnimi, valitud täpitähtede jm eritärkide sisalduvuse pärast) |
| `profile_attributes.mobile_number`          | ei | `"mobile_number":"+37200000766"` - m-ID kasutaja autentimisel kasutatud telefoninumber |
| `profile_attributes.date_of_birth`          | ei | Isiku sünnikuupäev ISO_8601 formaadis. Tagastatakse ainult eIDAS autentimisel. |
| `profile_attributes_nonlatin` | ei | Sisaldab JSON objekti mitteladinakeelsetest profiiliatribuutidest (vt allpool translitereerimine.). Väärtustatud ainult eIDAS autentimisel. |
| `state`            | jah | `"state":"abcdefghijklmnop"` - turvaelement  |
| `exp`              | jah | `"exp":1505847597` - tõendi aegumisaeg |
| `iat`              | jah | `"iat":1505818797` - tõendi väljaandmisaeg |
| `nonce`                 | ei | `"nonce":"qrstuvwxyzabcdef"` - turvaelement |
| `acr` Authentication Context Class Reference) | ei | `"acr": "low"` - autentimistase, vastavalt eIDAS tasemetele. Võimalikud väärtused: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Elementi ei kasutata, kui autentimistase ei kohaldu või pole teada |
| `jti`              | jah | `"jti":"0e12bf29... "` - identsustõendi identifikaator |


NB! Järgnevad identsustõendi väärtused esitatakse ainult juhul kui on tegemist ülepiirilise autentimisega ning kasutaja on nende väljade esitamist identsustõendis ise taotlenud läbi spetsiifilise `scope` parameetri väärtuse autentimispäringus.

EIDAS atribuudi nimele vastava täpsema kirjelduse leiab eIDAS SAML atribuutide profiilist [Viited](Viited), [eIDAS SAML Attribute Profile v1.1-2].

Füüsiline isik

| identsustõendi element | scope väärtus | väljastamine kohustuslik |  eIDAS atribuut |
|-----------------|-----------------|:------------------------:|----------------|
| `profile_attributes.birth_name` | `eidas:birth_name`  | ei | `BirthName` |
| `profile_attributes.place_of_birth` | `eidas:place_of_birth` | ei | `PlaceOfBirth` |
| `profile_attributes.current_address` | `eidas:current_address` | ei | `CurrentAddress` |
| `profile_attributes.gender` | `eidas:gender` | ei | `Gender` |

Juriidiline isik

| identsustõendi element | scope väärtus | väljastamine kohustuslik |  eIDAS atribuut |
|-----------------|-----------------|:------------------------:|----------------|
| `eidas:legal_person_identifier` | `profile_attributes.legal_person_identifier` | jah | `LegalPersonIdentifier` |
| `eidas:legal_name` | `profile_attributes.legal_name` | jah         | `LegalName` |

| identsustõendi element | scope väärtus | väljastamine kohustuslik | eIDAS atribuut |
|-----------------|-----------------|:------------------------:|----------------|
| `profile_attributes.legal_address` | `eidas:legal_address` | ei | `LegalAddress` |
| `profile_attributes.legal_person_address` | `eidas:legal_person_address` | ei | `LegalPersonAddress` |
| `profile_attributes.vat_registration` | `eidas:vat_registration` | ei | `VATRegistrationNumber` |
| `profile_attributes.tax_reference` | `eidas:tax_reference`  | ei |  `TaxReference` |
| `profile_attributes.LEI` | `eidas:lei`  | ei |  `LEI` |
| `profile_attributes.EORI` | `eidas:eori`  | ei |  `EORI` |
| `profile_attributes.SEED` | `eidas:seed`  | ei |  `SEED` |
| `profile_attributes.SIC` | `eidas:sic` | ei |  `SIC` |
| `profile_attributes.D-2012-17-EUIdentifier` | `eidas:d-2012-17-eu_identifier`  | ei | `D-2012-17-EUIdentifier ` |

Küsida ei saa, kuid võidakse väljastada:

füüsilise isiku esindaja atribuudid (väli identsustõendis - eIDAS atribuut)

- `profile_attributes.representative_birth_name` - `RepresentativeBirthName`
- `profile_attributes.representative_current_address` - `RepresentativeCurrentAddress`
- `profile_attributes.representative_family_name` - `RepresentativeFamilyName`
- `profile_attributes.representative_first_name` - `RepresentativeFirstName`
- `profile_attributes.representative_date_of_birth` - `RepresentativeDateOfBirth`
- `profile_attributes.representative_gender` - `RepresentativeGender`
- `profile_attributes.representative_person_identifier` - `RepresentativePersonIdentifier`
- `profile_attributes.representative_place_of_birth` - `RepresentativePlaceOfBirth`

juriidilise isiku esindaja atribuudid (väli identsustõendis - eIDAS atribuut)

- `profile_attributes.representative_d-2012-17-eu_identifier` - `RepresentativeD-2012-17-EUIdentifier`
- `profile_attributes.representative_eori` - `RepresentativeEORI`
- `profile_attributes.representative_lei` - `RepresentativeLEI`
- `profile_attributes.representative_legal_address` - `RepresentativeLegalAddress`
- `profile_attributes.representative_legal_name` - `RepresentativeLegalName`
- `profile_attributes.representative_legal_address` - `RepresentativeLegalAddress`
- `profile_attributes.representative_legal_person_identifier` - `RepresentativeLegalPersonIdentifier`
- `profile_attributes.representative_seed` - `RepresentativeSEED`
- `profile_attributes.representative_sic` - `RepresentativeSIC`
- `profile_attributes.representative_tax_reference` - `RepresentativeTaxReference`
- `profile_attributes.representative_vat_registration` - `RepresentativeVATRegistration`

Translitereerimine:

NB! Kõigi eelpool toodud eIDAS spetsiifilistele identsustõendi väärtused peavad olema esitatud ladinapärasel kujul, kuid sellele lisaks võivad sihtriigid soovi korral esitada nimekuju ka originaalsel kujul. Juhul kui välisriik otsustab saata ka algse, mitteladinakeelse kuju, esitatakse antud atribuudi nime ja väärtuse paarid ka `profile_attributes_nonlatin` blokis.

Näide identsustõendis profiilielementide translitereerimisest (isiku eesnimi ja perenimi on esitatud ladina ja kreekakeelsel kujul):
````json
{
   ...
   
   "profile_attributes":{
      "given_name":"Alexander",
      "family_name":"Onassis",
      "date_of_birth":"1981-01-12"
   },
   "profile_attributes_nonlatin":{
      "given_name":"Αλέξανδρος",
      "family_name":"Ωνάσης"}
   }
   
   ...
}
````

// &\#128679;

Identsustõend võib sisaldada muid OpenID Connect protokolli kohaseid välju, kuid neid teenuses ei kasutata. 
JWT väljade tähenduse kohta vt vajadusel [https://www.iana.org/assignments/jwt/jwt.xhtml](https://www.iana.org/assignments/jwt/jwt.xhtml).
Kui identsustõendit ei pärita `5` minuti jooksul, siis identsustõend aegub ja autentimisprotsessi tuleb korrata.

Rakendus loob saadud identsustõendi alusel kasutajaga seansi. Seansi loomine ja pidamine on rakenduse kohustus. Kuidas seda teha, ei ole autentimisteenuse skoobis.

## 6 Otspunktid

Testteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) |  [https://tara-test.ria.ee/oidc/.well-known/openid-configuration](https://tara-test.ria.ee/oidc/.well-known/openid-configuration) |
| teenuse avalik allkirjastamisvõti | [https://tara-test.ria.ee/oidc/jwks](https://tara-test.ria.ee/oidc/jwks) |
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

## 7 Soovitusi liidestajale

TARA-ga liidestamine on lihtne. Siiski on vaja töid kavandada ja hoolikalt teostada. Liidestamise protsess näeb välja järgmine. Asutus peaks välja selgitama, kas ja millistes oma e-teenustes soovib TARA kasutada. Selleks tuleks tutvuda TARA [ärikirjeldusega](Arikirjeldus), teenustaseme leppega (SLA-ga), käesoleva [tehnilise kirjeldusega](TehnilineKirjeldus). Võib heita pilgu teenuse [teekaardile](https://e-gov.github.io/TARA-Doku/#teekaart). Vajadusel pidada nõu RIA-ga, `help@ria.ee`.

Seejärel kavandada ja teostada teenuse kasutamiseks vajalikud arendustööd. 
  - klientrakenduse täiendamine OpenID Connect protokolli kohase klientkomponendiga
  - hinnanguline töömaht: kogenud arendajal u 2 päeva; kui OpenID Connect-i pole varem teinud, siis 2 nädalat
  - aluseks käesolev [tehniline kirjeldus](TehnilineKirjeldus)
  - kasulikku võib leida [makettrakendusest](https://github.com/e-gov/TARA-Client).

Eriti tähele panna:<br>
1) identsustõendit tuleb nõuetekohaselt kontrollida, vt [ID token validation](http://openid.net/specs/openid-connect-core-1_0.html#ImplicitIDTValidation) [Core]<br>
2) parameeter `state` tuleb siduda sessiooniga, vt [OpenID Connect Basic Client Implementer's Guide 1.0](https://openid.net/specs/openid-connect-basic-1_0.html), jaotis "2.1.1.1 Request Parameters".  

Arenduse valmides tuleb liidest testida. Selleks kasutatakse TARA testteenust. Asutus esitab taotluse testteenusega liitumiseks. Taotluse võib esitada juba enne arenduse algust. Taotluses teata asutus:<br>
1) teenuse, millega soovitakse liituda (test- või toodanguteenus)<br>
2) kinnituse, et liituja on välja arendanud omapoolse liidese ja seda TARA testteenuse vastu testinud (toodanguteenusega liitumise puhul)<br>
3) e-teenuse või -teenused, mille kasutajaid soovitakse TARA abil autentida<br>
4) kasutajate arvu prognoosi<br>
5) kohustumuse kasutada teenust eesmärgipäraselt. Sh testteenust kasutada ainult testimiseks, mitte toodangus autentimiseks<br>
6) nõustumuse teenustasemega (SLA-ga)<br>
7) klientrakenduse identifikaatori ettepanek -`client_id` OpenID Connect protokolli kohaselt<br>
8) klientrakenduse testversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt<br>
9) klientrakenduse testversiooni tagasisuunamis-URL juhuks, kui kasutaja soovib autentimist katkestada<br>
10) autentimismeetod või meetodid, mida soovitakse kasutada<br>
11) klientrakenduse haldaja kontaktandmed (e-post, telefon, isikukood).

Taotlus esitatakse ja edasine suhtlus teenuse haldamisel käib läbi RIA kasutajatoe, `help@ria.ee`. Vt lähemalt [RIA autentimisteenuste lehel](https://www.ria.ee/ee/autentimisteenused.html).

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse salasõna `client_secret`. Salasõna on ette nähtud identsustõendi päringute allkirjastamiseks
- avab asutuse klientrakenduse testversioonile juurdepääsu testteenusele.

Järgneb liidestuse testimine. RIA abistab siin võimalike probleemide lahendamisel.

Liitumine TARA toodanguteenusega. Eduka testimise järel asutus esitab taotluse toodanguteenuse avamiseks klientrakendusele. Taotluses näidatakse klientrakenduse toodanguversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt jm andmed

RIA, rahuldades taotluse, väljastab asutusele klientrakenduse toodanguversiooni salasõna `client_secret` ja avab asutuse klientrakenduse toodanguversioonile juurdepääsu toodanguteenusele.


## Muutelugu

| Versioon, kuupäev | Muudatus |
|-----------------|--------------|
| 0.3, 30.01.2018   | Lisatud piiriülene autentimine (eIDAS) |
| 0.2, 28.11.2017   | Lisatud ID-kaardiga autentimine |
| 0.1, 10.10.2017   | Mobiil-ID-ga autentimine. |

