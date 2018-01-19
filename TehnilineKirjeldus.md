---
permalink: TehnilineKirjeldus
---

# Tehniline kirjeldus
{: .no_toc}

- TOC
{:toc}

Vt ka: [Sonastik](Sonastik), [Viited](Viited)

## 1 Ülevaade

Käesolev dokument esitab teenuse tehnilised omadused. Fookus on OpenID Connect protokolli rakendamisel tehtud valikutel, erinevustel ja täiendustel OpenID Connect protokolliga võrreldes. Esitatakse päringute näited. Liidestuja peab kindlasti tutvuma ka OpenID Connect protokolliga [Core]. Liidestuja peab erilist tähelepanu pöörama, et kõik protokollikohased kontrollid saaksid tehtud - turvaelemendi `state` ja kui kasutatakse, siis ka `nonce` kontroll, identsustõendi kontroll jm. 

Autentimisteenus on Riigi Infosüsteemi Ameti uus pakutav teenus, millega asutus saab oma e-teenusesse lisada mobiil-ID ja ID-kaardi kasutaja autentimise toe. Järgmistes arendusjärkudes lisatakse teiste autentimismeetodite, sh piiriülese eIDAS-autentimise tugi.

TARA teenuse aluseks on OpenID Connect protokoll [Core], mis omakorda põhineb OAuth 2.0 protokollil. Võrreldes OpenID Connect protokolliga on tehtud järgmised valikud, erisused ja täiendused:

1. teenus toetab ainult volituskoodi (_authorization code_) kasutusvoogu.
2. kogu teave autenditud kasutaja kohta edastatakse rakendusele identsustõendis (_ID token_). Access token-it ja UserInfo otspunkti kaudu kasutaja atribuutide andmist ei toetata.
3. rakendusele edastatakse ka eIDAS autentimistase, kui see on teada (`acr` väites).
4. teenus toetab kasutajaliidese keele-eelistuse andmist autentimispäringus (`locale` parameetriga)
4. autentimismeetodi valib kasutaja autentimisteenuses.

Joonis 1 esitab teenuse ülevaatelise skeemi

<img src='img/KuvaVoog.PNG' style='width: 600px;'>

Joonis 1

## 2 Kasutaja liikumistee

Joonis 2 esitab kasutaja liikumise kuvade kaupa.

1 Tegevus algab kasutajale e-teenust osutavas klientrakenduses. Kasutajale esitatakse kuva, millel on nupp "Logi sisse" vms.

2 Vajutusega nupule "Logi sisse" suunatakse kasutaja TARA-teenusesse, autentimismeetodi valiku kuvale. Siin võib kasutaja valida: m-ID autentimise (3a); ID-kaardiga autentimise (4a); eIDAS-autentimise (8); tagasipöördumise klientrakendusse (1).

3a Mobiilinumbri ja isikukoodi sisestamine.

3b Kontrollkoodi kuvamine.

3c Kinnituse ootamine.

4a ID-kaardiga autentimine algab kasutajale teabe kuvamisega autentimisserdi kohta. Kasutaja kinnitab serdivaliku.

4b Kasutaja sisestab PIN1-koodi (ID-kaart). Tulemuseks on autenditult suunamine tagasi klientrakendusse (6) või veateate lehele (5).

5 Veateate lehelt saab kasutaja minna tagasi autentimimeetodi valikusse (2) ja seal kas üritada uuesti, võimalik, et teise autentimismeetodiga või katkestada autentimise ja minna tagasi klientrakendusse.

6 Autenditud kasutaja suunatakse tagasi klientrakendusse. Klientrakendus annab kasutajale asjakohasel viisil teada, et sisselogimine õnnestus.

7a Kasutaja saab anda tagasisidet teenuse kohta. Selleks on eraldi sakil avatav vorm, kuhu pääseb autentimismeetodi valiku kuval oleva lingi abil.

7b Kasutajal on võimalik esitada vearaportit. Selleks on eraldi sakil avatav vorm. Enne vormi on soovitused tüüpvigade iseseisvaks lahendamiseks.

7c Kasutajal on võimalik saada täiendavat teavet TARA-teenuse kohta. Teave kuvatakse eraldi sakil, sinna saab liikuda autentimismeetodi valiku kuval oleva lingi abil.

8 eIDAS autentimine toimub välisriigi süsteemis. 

<img src='img/KasutajaVoog.PNG' style='width: 600px;'>

Joonis 2. Kasutaja liikumistee kuvade kaupa

_Märkus. Võrdluseks Suomi.fi [autentimisteenus](https://www.suomi.fi/etusivu/) ("Kirjaudu sisään")._

## 3 Autentimisprotsess

<img src='img/VOOG.PNG' style='width: 480px;'>

Joonis 3. Tehniline ülevaade

Autentimisprotsess koosneb 5 sammust:
1. Autentimispäringu saatmine
2. Autentimismeetodi valik
3. Autentimine
4. Tagasisuunamine
5. Identsustõendi küsimine.

### 3.1 Autentimispäringu saatmine

Kasutaja vajutab nupule "Logi sisse" vms. Rakendus võib ka ise algatada autentimise.

Rakendus moodustab OpenID Connect autentimispäringu ja saadab sirvikule korralduse kasutaja suunamiseks autentimisteenusesse (HTTP _redirect_). Autentimispäringu näide:

````
GET https://tara.eesti.ee/authorize?

redirect_uri=https%3A%2F%2eteenindus.asutus.ee%2FCallback&
scope=openid&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Autentimispäringu kohustusliku elemendid:

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee (_path_) | `https://tara.eesti.ee/authorize` | `/authorize` on TARA-teenuse OpenID Connect-kohane autentimisotspunkt (termin 'autoriseerimine' pärineb alusprotokollist OAuth 2.0).  |
| tagasipöördumis-URL    | `https://eteenus.asutus.ee/tagasi` | tagasipöördumis-URL-i valib asutus ise. Tagasipöördumis-URL võib sisaldada _query_-osa. Tärgile `?` järgnevas osas omakorda `?` kasutamine ei ole lubatud. |
| autentimise skoop `scope`; toetatud on väärtus `openid` | `scope=openid`               |        |
| turvakood `state` | `state=hkMVY7vjuN7xyLl5`     | klientrakenduse serveripool peab genereerima ja päringus esitama turvakoodi. Turvakood aitab tagada, et erinevate kasutajate autentimised ei lähe sassi ja ründaja ei saa protsessi vahele sekkuda. Turvakood peegeldatakse vastuses tagasi; klientrakendus peab kontrollima, et saab vastuses sama turvakoodi, mille saatis päringus.  |
| `response_type` | `response_type=code` | autentimise tulemuse serverile edastamise viis. Toetatud on volituskoodi viis `code`. |
| rakenduse identifikaator `client_id` | `client_id=58e7...` | rakenduse identifikaatori annab RIA asutusele klientrakenduse registreerimisel autentimisteenuse kasutajaks |
| kasutajaliidese keele valik `locale` | `locale=et` | toetatakse keeli `et`, `en`, `ru`. Vaikimisi on kasutajaliides eesti keeles. Kasutaja saab keelt ise valida. |

Autentimispäringus võib lisada taasesitusründeid vältida aitava parameetri `nonce`, vastavalt protokollile [Core], jaotis 3.1.2.1.  Authentication Request. Parameeter `nonce` ei ole kohustuslik.

### 3.2 Autentimismeetodi valik

Kasutaja saabudes autentimisteenusesse avaneb talle leht "TARA isikutuvastusteenus". Kasutajale esitatakse toetatavad autentimismeetodid.

Teenuse I arendusjärgus toetatakse ühte autentimismeetodit - mobiil-ID-d.

Kasutaja valib autentimismeetodi.

### 3.3 Autentimine

Kasutaja läbib autentimisprotseduuri, vastavalt valitud autentimismeetodile. 

### 3.4 Tagasisuunamine

Autentimisteenus suunab kasutaja tagasi rakendusse (klientrakenduse poolt kaasa antud naasmisaadressile), andes kaasa volituskoodi (_authorization code_). Tehniliselt tehakse tagasisuunamine HTTP _redirect_-päringuga. Näide:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````

Tagasisuunamispäringu elemendid:

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| tagasisuunamis-URL | `https://eteenus.asutus.ee
/tagasi?` | ühtib autentimispäringus saadetuga |
| volituskood `code` | `code=71ed579...`  | ingl _authorization code_. Volituskood on ühekordne “lubatäht” identsustõendi saamiseks |
| turvakood `state`            | `state=OFfVLKu0kNbJ2EZk`     |  juhusõne |

Märkus. Kasutaja võib e-teenusesse tagasi pöörduda ka ilma autentimismeetodit valimata ja autentimist läbi tegemata (link "Tagasi teenusepakkuja juurde").

### 3.5 Identsustõendi küsimine

Rakendus küsib autentimisteenuselt, volituskoodi esitades,  identsustõendi (_ID token_). Tõendiküsimispäringu näide:

````
POST /token HTTP/1.1
Host: tara.ria.ee/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi
````

| POST päringu keha element | näide                    |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.ria.ee/token` |   |
| `grant_type`  | `grant_type=authorization_code` | nõutav väärtus `authorization_code` |
| `code` | `code=Splx...` | autentimisteenuselt saadud volituskood | 
| `redirect_uri` | `redirect_uri=https%3A%2F` | autentimispäringus saadetud ümbersuunamis-URI |
| 

Päringus tuleb anda `Authorization` päis, väärtusega, mis moodustatakse sõnast `Basic`, tühikust ja Base64 kodeeringus stringist `<client_id>:<client_secret>` (vt RFC 2617 HTTP Authentication: Basic and Digest Access Authentication, jaotis 2 Basic Authentication Scheme).

Autentimisteenus kontrollib, et identsustõendit küsib õige rakendus, seejärel koostab identsustõendi ning tagastab selle rakendusele. Näide:

````
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

| Identsustõendi element | näide, selgitus     |
|:-----:|-------------------------------------------|
| `aud`                  | `"aud":"openIdDemo"` - autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `sub`                  | `"sub":"EE11412090004"` - autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator). Isikukood antakse eesliitega `EE`  |
| `nbf`                  | `"nbf":"Wed Sep 27 11:47:22 EEST 2017"` - tõendi kehtivuse algusaeg (_Not Before_) |
| `amr`                  | `"amr":["mID"]` - kasutaja autentimiseks kasutatud autentimismeetod (_Authentication Method Reference_) |
| `iss`              | `"iss":"https://tara.ria.ee"` - tõendi väljastaja (TARA-teenus); testteenuse puhul `"iss":"https://tara-test.ria.ee"` |
| `profile_attributes`   | `"profile_attributes": {"given_name":"MARY ÄNN", "family_name":"O’CONNEŽ-ŠUSLIK", "mobile_number":"+37200000766"}` - autenditud isikut kirjeldavad andmed|
| `given_name`              | `"given_name":"MARY ÄNN"` - autenditud kasutaja eesnimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `family_name`              | `"family_name":"O’CONNEŽ-ŠUSLIK"` - autenditud kasutaja perekonnanimi (testnimi, valitud täpitähtede jm eritärkide sisalduvuse pärast) |
| `mobile_number`          | `"mobile_number":"+37200000766"` - m-ID kasutaja autentimisel kasutatud telefoninumber |
| `state`            | `"state":"abcdefghijklmnop"` - turvaelement  |
| `exp`              | `"exp":1505847597` - tõendi aegumisaeg |
| `iat`              | `"iat":1505818797` - tõendi väljaandmisaeg |
| `nonce`                 | `"nonce":"qrstuvwxyzabcdef"` - turvaelement |
| `acr`              | `"acr": "http://eidas.europa.eu/LoA/low"` - autentimistase, vastavalt eIDAS tasemetele (_Authentication Context Class Reference_). Elementi ei kasutata, kui autentimistase ei kohaldu või pole teada |
| `jti`              | `"jti":"0e12bf29-2a3b-4a81-a85e-973d0a2303d1"` - identsustõendi identifikaator |

Identsustõend võib sisaldada muid OpenID Connect protokolli kohaseid välju, kuid neid teenuses ei kasutata. 
JWT väljade tähenduse kohta vt vajadusel [https://www.iana.org/assignments/jwt/jwt.xhtml](https://www.iana.org/assignments/jwt/jwt.xhtml).
Kui identsustõendit ei pärita `5` minuti jooksul, siis identsustõend aegub ja autentimisprotsessi tuleb korrata.

Rakendus loob saadud identsustõendi alusel kasutajaga seansi. Seansi loomine ja pidamine on rakenduse kohustus. Kuidas seda teha, ei ole autentimisteenuse skoobis.

## 4 Autentimistasemed

Klientrakendusele väljastatakse identsustõendis ka usaldustase, millega autentimine läbi viidi (autentimistase) - juhul, kui kasutatud autentimismeetodile on usaldustase määratud. 

eIDAS autentimistasemed [tasemed], ingl _level of assurance_ esitatakse tehniliselt URI-dega, vastavalt eIDAS spetsifikatsioonile [eIDAS]:
- madal - `http://eidas.europa.eu/LoA/low`
- märkimisväärne - `http://eidas.europa.eu/LoA/substantial`
- kõrge - `http://eidas.europa.eu/LoA/high`.

Autentimistase esitatakse JWT väites (_claim_) `acr` (_Authentication Context Class Reference_). Näiteks:

`"acr": "http://eidas.europa.eu/LoA/low"`.

Kui autentimistase ei ole teada, siis väidet ei esitata.

Autentimistasemete kohta lähemalt vt [autentimistasemed].

## 5 Autentimismeetodid

Autentimisteenus pakub järgmisi autentimismeetodeid:

|  kood | nimetus |
|-------|---------|
| `mID` | mobiil-id [DSS] |
| `idcard` | Eesti ID-kaart |

Märkus. Autentimismeetodite lisandudes tabel täieneb. Vt ka [RFC8176].

Klientrakendusele avatud autentimismeetodid määratakse klientrakenduse registreerimisel.

Isiku autentimiseks kasutatud autentimismeetod või -meetodid näidatakse identsustõendis, väljas `amr`. 

## 6 Isikuandmete vorming

Eduka autentimise korral edastatakse isikuandmed so autenditud isikut identifitseerivad ja kirjeldavad andmed identsustõendis klientrakendusele. Kuna teenus hakkab toetama ka piiriülest eIDAS-autentimismeetodit, esitatakse isikuandmed eIDAS nõuetele vastavas, ühtlustatud vormingus - ka siis, kui autentimisel kasutati muud autentimismeetodit. (Vt [eIDAS SAML Attribute Profile] jaotis 2.2 "Attributes for Natural Persons"). Autentimismeetodist tulevad isikuandmed vajadusel teisendatakse eIDAS nõuetele vastavasse vormingusse.

### 6.1 Isikuandmete teisendus mobiil-ID-ga autentimisel

|  DigiDocService poolt väljastatud andmed | eIDAS atribuut | väljastatakse identsustõendis |
|------------------------------------------|----------------|-------------------------------|
|    ?                                     | PersonIdentifier, vt _idem_, jaotis 2.2.3 | OpenID Connect väide (_claim_) `sub`, väärtuseks isikukood, eesliitega `EE` |
|    ?                                     | FamilyName, vt _idem_, jaotis 2.2.4 | OpenID Connect väide (_claim_) `family_name` (vt [Core], jaotis 5.1 "Standard Claims") |
|    ?                                     | GivenName, vt _idem_, jaotis 2.2.5 | OpenID Connect väide (_claim_) `given_name` (vt [Core], jaotis 5.1 "Standard Claims") |
|                                                      | - | `mobileNumber` |

## 7 Klientrakenduse registreerimine

Klientrakendus registreeritakse TARA-teenuses RIA teenusehalduri poolt. Registreerimine on eelduseks juurdepääsu saamisele teenusele. Registreerimine tehakse test- ja toodanguteenuse jaoks eraldi. Toodanguteenuses registreerimise eelduseks on liidestuse edukas testimine testteenuses.

Klientrakenduse registreerimise kohta lähemalt vt [CAS OpenID Connect].

## 8 Tehniline ülesehitus

_Märkus. Järgnev hõlmab ka teenuse edasisi arendusjärke._

Süsteem on ehitatud Apereo CAS platvormile, versioon 5.1 [CAS].

<img src='img/TEGELIK.PNG' style='width: 600px;'>

Joonis 4. Tehniline ülesehitus

TARA autentimisteenuse teostavad järgmised tarkvarakomponendid:
- Teenuseosutaja taristusse paigaldatavad:
  - _Open ID Connect haldur_ teostab liidese klientrakenduse poole.
  - _Teenusehalduri tööriista_ abil määratleb teenusehaldur millised klientrakendused ja kuidas TARA-teenust kasutavad. Vt [CAS Service Management].
  - _Klientrakenduste register_ hoitakse klientrakenduste seadistusteavet.
  - _m-ID autentija_ teostab liidese Sertifitseerimiskeskuse DigiDoc teenusega.
  - _eIDAS autentija_ teostab välismaalase autentimise eIDAS taristu kaudu, vastavalt eIDAS nõuetele [eIDAS].
- Teenusekasutaja taristusse paigaldatavad:
  - Open ID Connect klienditeek vms  
- Testimiseks kasutatavad:
  - _Klientrakenduse makett_ (_mock-up_).

***I arendusjärgus*** teostatakse: OpenID Connect haldur, teenusehalduri tööriist, klientrakenduste register, m-ID autentija.

***II jj arendusjärkudes*** teostatakse muud autentimismeetodid.

## 9 Otspunktid

Testteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) |  `https://tara-test.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara-test.ria.ee/oidc/jwks` |
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

Märkus. Otspunktide kohta lähemalt vt [CAS OpenID Connect].

