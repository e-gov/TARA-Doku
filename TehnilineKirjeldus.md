---
permalink: TehnilineKirjeldus
---

# Tehniline kirjeldus
{: .no_toc}

- TOC
{:toc}

Vt ka: [Sonastik](Sonastik), [Viited](Viited)

## 1 Ülevaade

Autentimisteenus on Riigi Infosüsteemi Ameti uus pakutav teenus, millega asutus saab oma e-teenusesse lisada mobiil-ID kasutaja autentimise toe.

Autentimismeetodina toetab teenus esialgu mobiil-ID-d. Järgmistes arendusjärkudes lisatakse teiste autentimismeetodite - ID-kaart, eIDAS jt - tugi.

Teenuse tähtsamad tunnusjooned:
1. autentimisprotsessi aluseks on OpenID Connect 1.0 protokoll [Core], täpsemalt volituskoodi  (_authorization code_) kasutusvoog. Arvestatud on ka protokolli avaliku sektori profiiliga [iGOV].
2. kogu teave autenditud kasutaja kohta edastatakse rakendusele identsustõendis (_ID token_).
3. rakendusele edastatakse ka eIDAS autentimistase, kui see on teada.
4. autentimismeetodi valib kasutaja autentimisteenuses.

## 2 Autentimisprotsess

<img src='img/VOOG.PNG' style='width: 480px;'>

Autentimisprotsess koosneb 5 sammust:
1. Autentimispäringu saatmine
2. Autentimismeetodi valik
3. Autentimine
4. Tagasisuunamine
5. Identsustõendi küsimine.

### 2.1 Autentimispäringu saatmine

Kasutaja vajutab nupule "Logi sisse" vms. Rakendus võib ka ise algatada autentimise.

Rakendus moodustab OpenID Connect autentimispäringu ja saadab sirvijale korralduse kasutaja suunamiseks autentimisteenusesse (HTTP _redirect_). Autentimispäringu näide:

````
GET https://auth.ria.ee/login?

redirect_uri=https%3A%2F%2eteenindus.asutus.ee%2FCallback&
scope=user&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Autentimispäringu elemendid (kõik elemendid on kohustuslikud):

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee (_path_) | `https://tara.eesti.ee/authorize` |               |
| tagasipöördumis-URL    | `https://et...ee/Callback` | tagasipöördumis-URL-i valib asutus ise |
| autentimise skoop `scope`; toetatud on väärtus `openid` | `scope=openid`               |        |
| turvakood `state` | `state=hkMVY7vjuN7xyLl5`     | klientrakenduse serveripool peab genereerima ja päringus esitama turvakoodi, mis aitab tagada, et erinevate kasutajate autentimised sassi ei lähe ja ründaja ei saa protsessi vahele sekkuda. Turvakood peegeldatakse tagasi vastuses; klientrakendus peab kontrollima, et saab vastuses sama turvakoodi, mille saatis päringus.  |
| autentimise tulemuse serverile edastamise viis `response_type`; toetatud on volituskoodi viis `code` | `response_type=code` |   |
| rakenduse identifikaator `client_id` | `client_id=58e7...` | rakenduse identifikaatori annab RIA asutusele klientrakenduse registreerimisel autentimisteenuse kasutajaks |

### 2.2 Autentimismeetodi valik

Kasutaja saabudes autentimisteenusesse avaneb talle leht "TARA isikutuvastusteenus". Lehel on toetatavad autentimismeetodid.

Teenuse I arendusjärgus toetatakse ühte autentimismeetodit - mobiil-ID-d.

Kasutaja valib autentimismeetodi.

### 2.3 Autentimine

Kasutaja läbib autentimisprotseduuri, vastavalt valitud autentimismeetodile. 

### 2.4 Tagasisuunamine

Autentimisrakendus suunab kasutaja tagasi rakendusse (rakenduse poolt kaasa antud naasmisaadressile), andes kaasa volituskoodi (_authorization code_). Tehniliselt tehakse tagasisuunamine HTTP päringuga. Näide:

````
HTTP GET https://eteenindus.asutus.ee/Callback?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````

Tagasisuunamispäringu elemendid:

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| tagasisuunamis-URL | `https://eteenindus.asutus.ee
/Callback?` | ühtib autentimispäringus saadetuga |
| volituskood `code` | `code=71ed579...`  | ingl _authorization code_. Volituskood on ühekordne “lubatäht” identsustõendi saamiseks |
| turvakood `state`            | `state=OFfVLKu0kNbJ2EZk`     |  unikaalne identifikaator () |

### 2.5 Identsustõendi küsimine

Rakendus küsib autentimisteenuselt, volituskoodi esitades,  identsustõendi (_ID token_). Tõendiküsimispäringu näide:

````
POST /token HTTP/1.1
Host: tara.ria.ee/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenindus.asutus.ee%2FCallback&
client_id=58e7ba35aab5b4f1671a&
client_secret={client_secret}
````

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.ria.ee/token` |   |
| `grant_type`  | `grant_type=authorization_code` | nõutav väärtus `authorization_code` |
| `code` | `code=Splx...` | autentimisteenuselt saadud volituskood | 
| `redirect_uri` | `redirect_uri=https%3A%2F` | autentimispäringus saadetud ümbersuunamis-URI |
| `client_id` | `client_id=58e7...` | klientrakenduse ID |
| `client_secret` | `client_secret={client_secret}` | klientrakendusele antud salasõna |

Autentimisteenus kontrollib, et identsustõendit küsib õige rakendus, koostab identsustõendi ning tagastab need rakendusele. Näide:

````
{  
   "sub":"11412090004",
   "amr":[  
      "mID"
   ],
   "iss":"http:\/\/localhost:8080\/cas\/oidc",

   "profile_attributes":{  
      "personalCode":"11412090004",
      "firstName":"MARY ÄNN",
      "lastName":"O\u2019CONNEŽ-ŠUSLIK",
      "mobileNumber":"+37200000766"
   },
   "preferred_username":"openIdDemo",
   "nonce":"qrstuvwxyzabcdef",
   "aud":"openIdDemo",
   "nbf":1505818497,
   "state":"abcdefghijklmnop",
   "exp":1505847597,
   "iat":1505818797,
   "jti":"0e12bf29-2a3b-4a81-a85e-973d0a2303d1"
}
````

Identsustõendis esitatakse järgmised väljad (_claims_). Identsustõend võib sisaldada muid Open ID Connect protokolli kohaseid välju, kuid neid teenuses ei kasutata. 
JWT väljade tähenduse kohta vt vajadusel [https://www.iana.org/assignments/jwt/jwt.xhtml](https://www.iana.org/assignments/jwt/jwt.xhtml).

| Identsustõendi element | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| `at_hash`              |                             | pääsutõendi räsi (ei kasutata) |
| `aud`                  | `"aud":"openIdDemo"` | autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `sub`                  | `"sub":"EE11412090004"` | autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator). Isikukood antakse eesliitega `EE`  |
| `nbf`                  | `"nbf":"Wed Sep 27 11:47:22 EEST 2017"` | tõendi kehtivuse algusaeg (_Not Before_) |
| `amr`                  | `"amr":["mID"]` | kasutaja autentimiseks kasutatud autentimismeetod (_Authentication Method Reference_) |
| `iss`              | `"iss":"https://tara.ria.ee"` | tõendi väljastaja (TARA-teenus) |
| `profile_attributes`   | `"profile_attributes": {"given_name":"MARY ÄNN", "family_name":"O’CONNEŽ-ŠUSLIK", "mobile_number":"+37200000766"}` | autenditud isikut kirjeldavad andmed|
| `given_name`              | `"given_name":"MARY ÄNN"` | autenditud kasutaja eesnimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `family_name`              | `"family_name":"O’CONNEŽ-ŠUSLIK"` | autenditud kasutaja perekonnanimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `mobile_number`          | `"mobile_number":"+37200000766"` | m-ID kasutaja autentimisel kasutatud telefoninumber |
| `state`            | `"state":"abcdefghijklmnop"` | turvaelement  |
| `exp`              | `"exp":1505847597` | tõendi aegumisaeg |
| `iat`              | `"iat":1505818797` | tõendi väljaandmisaeg |
| `nonce`                 | `"nonce":"qrstuvwxyzabcdef"` | turvaelement |
| `acr`              | `"acr": "http://eidas.europa.eu/LoA/low"` | autentimistase, vastavalt eIDAS tasemetele (_Authentication Context Class Reference_). Elementi ei kasutata, kuid autentimistase ei kohaldu või pole teada |
| `jti`              | `"jti":"0e12bf29-2a3b-4a81-a85e-973d0a2303d1"` | tõendi identifikaator |

Rakendus loob saadud identsustõendi alusel kasutajaga seansi. Seansi loomine ja pidamine on rakenduse kohustus. Kuidas seda teha, ei ole autentimisteenuse skoobis.

## 3 Autentimistasemed

Klientrakendusele väljastatakse identiteeditõendis (_ID token_) ka usaldustase, millega autentimine läbi viidi (autentimistase), kui kasutatud autentimismeetodile on usaldustase määratud. 

eIDAS autentimistasemed [tasemed], ingl _level of assurance_ esitatakse tehniliselt URI-dega, vastavalt eIDAS spetsifikatsioonile [eIDAS]:
- madal - `http://eidas.europa.eu/LoA/low`
- märkimisväärne - `http://eidas.europa.eu/LoA/substantial`
- kõrge - `http://eidas.europa.eu/LoA/high`

Autentimistase esitatakse JWT väites (_claim_) `acr` (_Authentication Context Class Reference_). Näiteks:

`"acr": "http://eidas.europa.eu/LoA/low"`

Kui autentimistase ei ole teada, siis väidet ei esitata.

Autentimistasemete kohta lähemalt vt [autentimistasemed].

## 4 Autentimismeetodid

Autentimisteenus pakub järgmisi autentimismeetodeid:

|  kood | nimetus |
|-------|---------|
| `mID` | mobiil-id [DSS] |

Märkus. Autentimismeetodite lisandudes tabel täieneb. Vt ka [RFC8176].

Klientrakendusele avatud autentimismeetodid määratakse klientrakenduse registreerimisel.

Isiku autentimiseks kasutatud autentimismeetod või -meetodid näidatakse identsustõendis, väljas `amr`. 

## 5 Isikuandmete vorming

Eduka autentimise korral edastatakse isikuandmed so autenditud isikut identifitseerivad ja kirjeldavad andmed identsustõendis klientrakendusele. Kuna teenus hakkab toetama ka piiriülest eIDAS-autentimismeetodit, esitatakse isikuandmed eIDAS nõuetele vastavas, ühtlustatud vormingus - ka siis, kui autentimisel kasutati muud autentimismeetodit. (Vt [eIDAS SAML Attribute Profile] jaotis 2.2 "Attributes for Natural Persons"). Autentimismeetodist tulevad isikuandmed vajadusel teisendatakse eIDAS nõuetele vastavasse vormingusse.

### 5.1 Isikuandmete teisendus mobiil-ID-ga autentimisel

|  DigiDocService poolt väljastatud andmed | eIDAS atribuut | väljastatakse identsustõendis |
|------------------------------------------|----------------|-------------------------------|
|    ?                                     | PersonIdentifier, vt _idem_, jaotis 2.2.3 | OpenID Connect väide (_claim_) `sub`, väärtuseks isikukood, eesliitega `EE` |
|    ?                                     | FamilyName, vt _idem_, jaotis 2.2.4 | OpenID Connect väide (_claim_) `family_name` (vt [Core], jaotis 5.1 "Standard Claims") |
|    ?                                     | GivenName, vt _idem_, jaotis 2.2.5 | OpenID Connect väide (_claim_) `given_name` (vt [Core], jaotis 5.1 "Standard Claims") |
|                                                      | - | `mobileNumber` |

## 6 Klientrakenduse registreerimine

Klientrakendus registreeritakse TARA-teenuses RIA teenusehalduri poolt. Registreerimine on eelduseks juurdepääsu saamisele teenusele. Registreerimine tehakse test- ja toodanguteenuse jaoks eraldi. Toodanguteenuses registreerimise eelduseks on liidestuse edukas testimine testteenuses.

Märkus. Klientrakenduse registreerimise kohta lähemalt vt [CAS OpenID Connect].

````
{
  "issuer": "https://sso-fe1.arendus.kit",
  "scopes_supported": [
    "openid"
  ],
  "response_types_supported": [
    "code"
  ],
  "subject_types_supported": [
    "public"
  ],
  "claim_types_supported": [
    "normal"
  ],
  "claims_supported": [
    "sub",
    "firstName",
    "lastName",
    "mobileNumber",
    "personalCode"
  ],
  "grant_types_supported": [
    "authorization_code"
  ],
  "id_token_signing_alg_values_supported": [
    "none",
    "RS256"
  ],
  "jwks_uri": "https://sso-fe1.arendus.kit/oidc/jwks",
  "authorization_endpoint": "https://sso-fe1.arendus.kit/oidc/authorize",
  "token_endpoint": "https://sso-fe1.arendus.kit/oidc/accessToken",
  "userinfo_endpoint": "https://sso-fe1.arendus.kit/oidc/profile",
  "registration_endpoint": "https://sso-fe1.arendus.kit/oidc/register",
  "end_session_endpoint": "https://sso-fe1.arendus.kit/logout"
}
````

## 7 Tehniline ülesehitus

_Märkus. Järgnev hõlmab ka teenuse edasisi arendusjärke._

Süsteem on ehitatud Apereo CAS platvormile, versioon 5.1 [CAS].

<img src='img/TEGELIK.PNG' style='width: 600px;'>

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

## 8 Otspunktid

Testteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara-test.ria.ee/oidc/.well-known`, `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara-test.ria.ee/oidc/jwks` |
| kliendi registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | `https://tara-test.ria.ee/authorize` | 
| tõendiväljastus (_token_) | `https://tara-test.ria.ee/token` | 

Toodanguteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara.ria.ee/oidc/.well-known`, `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara.ria.ee/oidc/jwks` |
| kliendi registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | `https://tara.ria.ee/authorize` | 
| tõendiväljastus (_token_) | `https://tara.ria.ee/token` | 

Märkus. Otspunktide kohta lähemalt vt [CAS OpenID Connect].