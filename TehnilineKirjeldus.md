---
permalink: TehnilineKirjeldus
---

# Tehniline kirjeldus
{: .no_toc}

- TOC
{:toc}

Vt ka:
- [Sonastik](Sonastik)

## Ülevaade




## Autentimistasemed

Klientrakendusele väljastatakse identiteeditõendis (_ID token_) ka usaldustase, millega autentimine läbi viidi (autentimistase), kui kasutatud autentimismeetodile on usaldustase määratud. 

eIDAS autentimistasemed [tasemed], ingl _level of assurance_ esitatakse tehniliselt URI-dega, vastavalt eIDAS spetsifikatsioonile [eIDAS]:
- madal - `http://eidas.europa.eu/LoA/low`
- märkimisväärne - `http://eidas.europa.eu/LoA/substantial`
- kõrge - `http://eidas.europa.eu/LoA/high`

Autentimistase esitatakse JWT väites (_claim_) `acr` (Authentication Context Class Reference). Näiteks:

`"acr": "http://eidas.europa.eu/LoA/low"`

Kui autentimistase ei ole teada, siis väidet ei esitata.

JWT väidete kohta vt ka [https://www.iana.org/assignments/jwt/jwt.xhtml](https://www.iana.org/assignments/jwt/jwt.xhtml).

## Minimaalne autentimistase

Rakendus võib autentimispäringus esitada minimaalse autentimistaseme, mille korral kasutaja e-teenusesse lubatakse. Minimaalne autentimistase esitatakse parameetriga `loamin=väärtus`, kus väärtus on autentimistaseme eIDAS-kood. Näiteks:
{: .note}

`loamin=`http://eidas.europa.eu/LoA/high`

Kasutajale esitatakse autentimisteenuses ainult need autentimismeetodid, mille autentimistase on võrdne või suurem päringus esitatud minimaalsest autentimistasemest.
{: .note}

## Protokoll

Klientrakendus kasutab teenust OpenID Connect protokolli järgi. Täpsemalt on valitud volituskoodi voog (_authorization flow_) ja avaliku sektori profiil [iGOV].

## Klientrakenduse registreerimine

````
  {
  "@class": "org.apereo.cas.services.OidcRegisteredService",
  "clientId": "openIdDemo",
  "clientSecret": "secret",
  "serviceId": "https://localhost:8451/oauth/response",
  "signIdToken": true,
  "name": "openIdDemo",
  "id": 322,
  "evaluationOrder": 100,
  "bypassApprovalPrompt": true,
  "jsonFormat": true,
  "ssoEnabled": false,
  "generateRefreshToken": false,
  "description": "openIdDemo",
  "scopes": [
    "java.util.HashSet", [ "openid",  "profile" ]
  ],
  "attributeReleasePolicy": {
    "@class": "org.apereo.cas.services.ReturnAllAttributeReleasePolicy",
    "principalAttributesRepository":
    {
    "@class": "org.apereo.cas.authentication.principal.DefaultPrincipalAttributesRepository",
    "expiration": 2,
    "timeUnit": "HOURS"
    }
  }
}
````

## Autentimisprotsess

Autentimisprotsessi aluseks on OpenID Connect 1.0 protokoll, täpsemalt volituskoodi  (_authorization code_) kasutusvoog.

1. ***Autentimispäringu saatmine***

Kasutaja vajutab nupule "Logi sisse" vms. Rakendus võib ka ise algatada autentimise.

Rakendus moodustab OpenID Connect autentimispäringu ja saadab sirvijale korralduse kasutaja suunamiseks autentimisteenusesse (HTTP _redirect_). Autentimispäringu näide:

````
HTTP GET https://auth.ria.ee/login?
redirect_uri=https%3A%2F%2eteenindus.asutus.ee%2FCallback&
scope=user&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Ümbersuunamispäringu elemendid:

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.eesti.ee/login` |               |
| tagasipöördumis-URL    | `https://eteenindus.asutus.ee/Callback` | tagasipöördumis-URL-i valib asutus ise |
| autentimise skoop      | `scope=openid`               |        |
| turvakood              | `state=hkMVY7vjuN7xyLl5`     | rakenduse serveripool genereerib turvakoodi, mis aitab tagada, et erinevate kasutajate autentimised sassi ei lähe ja ründaja ei saa protsessi vahele sekkuda |
| autentimise tulemuse serverile edastamise viis; toetatud on volituskoodi viis `code` | `response_type=code` |   |
| rakenduse identifikaator `client_id` | `client_id=58e7ba35aab5b4f1671a` | rakenduse identifikaatori annab RIA asutusele e-teenuse registreerimisel autentimisteenuse kasutajaks |

2. ***Autentimismeetodi valik***

Kasutaja saabudes autentimisteenusesse avaneb talle leht "TARA isikutuvastusteenus". Lehel on toetatavad autentimismeetodid.

Teenuse I arendusjärgus toetatakse ühte autentimismeetodit - mobiil-ID-d.

Kasutaja valib autentimismeetodi.

3. ***Autentimine***

Kasutaja läbib autentimisprotseduuri, vastavalt valitud autentimismeetodile. 

4. ***Tagasisuunamine***

Autentimisrakendus suunab kasutaja tagasi rakendusse (rakenduse poolt kaasa antud naasmisaadressile), andes kaasa volituskoodi (_authorization code_). Tehniliselt tehakse tagasisuunamine HTTP päringuga. Näide:

````
HTTP GET https://eteenindus.asutus.ee/Callback?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````

Tagasisuunamispäringu elemendid:

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| tagasisuunamis-URL | https://eteenindus.asutus.ee/Callback? | ühtib autentimispäringus saadetuga |
| volituskood `code` | `code=71ed5797c3d957817d31`  | ingl _authorization code_. Volituskood on ühekordne “lubatäht” identsustõendi saamiseks |
| turvakood `state`            | `state=OFfVLKu0kNbJ2EZk`     |  unikaalne identifikaator () |

5. ***Identsustõendi küsimine***

Rakendus küsib autentimisteenuselt, volituskoodi esitades,  identsustõendi (_ID token_).

Autentimisteenus kontrollib, et identsustõendit küsib õige rakendus, koostab identsustõendi ning tagastab need rakendusele. Näide:

````
{  
   "at_hash":"fUW7LGn0churo+TXbC4T0A==",
   "sub":"11412090004",
   "amr":[  
      "AcceptUsersAuthenticationHandler"
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

| Identsustõendi element | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| `at_hash`              | | |
| `sub`                  | "sub":"11412090004" | autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator) |
| `amr`                  | "amr":["AcceptUsersAuthenticationHandler"] | kasutaja autentimiseks kasutatud autentimismeetod |
| `iss`              | "iss":"http:\/\/localhost:8080\/cas\/oidc"  | tõendi väljastaja (TARA-teenus) |
| `profile_attributes`              | | |
| `personalCode`         | "personalCode":"11412090004" | autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator) |
| `firstName`              | "firstName":"MARY ÄNN" | autenditud kasutaja eesnimi |
| `lastName`              | "lastName":"O\u2019CONNEŽ-ŠUSLIK"| autenditud kasutaja perekonnanimi |
| `mobileNumber`          | "mobileNumber":"+37200000766" | |
| `nonce`                 | "nonce":"qrstuvwxyzabcdef" | |
| `aud`              | "aud":"openIdDemo" | autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `state`            | `"state":"abcdefghijklmnop"` |   |
| `exp`              | "exp":1505847597 | tõendi aegumisaeg |
| `iat`              | "iat":1505818797 | tõendi väljaandmisaeg |
| `acr`              | `"acr": "http://eidas.europa.eu/LoA/low"` | autentimistase, vastavalt eIDAS tasemetele|
| `jti`              | "jti":"0e12bf29-2a3b-4a81-a85e-973d0a2303d1" | tõendi identifikaator |
| `nbf`              | "nbf":1505818497|  _not before_, aeg, enne mida tõend ei kehti |

Rakendus loob saadud identsustõendi alusel kasutajaga seansi. Seansi loomine ja pidamine on rakenduse kohustus. Kuidas seda teha, ei ole autentimisteenuse skoobis.


## Tehniline platvorm

Süsteem on ehitatud Apereo CAS platvormile [CAS].

## Komponendid

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

## Arendusjärgud

***I arendusjärgus*** teostatakse: OpenID Connect haldur, teenusehalduri tööriist, klientrakenduste register, m-ID autentija.

***II arendusjärgus*** teostatakse: eIDAS autentimine; muud autentimismeetodid.
 