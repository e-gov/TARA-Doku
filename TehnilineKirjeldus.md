---
permalink: TehnilineKirjeldus
---

# Tehniline kirjeldus
{: .no_toc}

- TOC
{:toc}

Vt ka:
- [Sonastik](Sonastik)

## Autentimistasemed

Klientrakendusele väljastatakse identiteeditõendis (_ID token_) ka usaldustase, millega autentimine läbi viidi (autentimistase), kui kasutatud autentimismeetodile on usaldustase määratud. 

eIDAS autentimistasemed [tasemed], ingl _level of assurance_ esitatakse tehniliselt URI-dega, vastavalt eIDAS spetsifikatsioonile [eIDAS]:
- madal - `http://eidas.europa.eu/LoA/low`
- märkimisväärne - `http://eidas.europa.eu/LoA/substantial`
- kõrge - `http://eidas.europa.eu/LoA/high`

Autentimistase esitatakse JWT väites (_claim_) `acr` (level of assurance). Näiteks:

`"acr": "http://eidas.europa.eu/LoA/low"`

Kui autentimistase ei ole teada, siis väidet ei esitata.

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

## Autentimisvoog

ID token:

````
  {
    "jti": "fb38d930-9255-49df-8bed-d2621e38c44e",
    "iss": "http://localhost:8080/cas/oidc",
    "aud": "openIdDemo",
    "exp": 1505540348,
    "iat": 1505511548,
    "nbf": 1505511248,
    "sub": "11412090004",
    "amr": [
        "AcceptUsersAuthenticationHandler"
    ],
    "state": "abcdefghijklmnop",
    "nonce": "qrstuvwxyzabcdef",
    "at_hash": "28ihFzdPCM449jKibXq4dg==",
    "preferred_username": "openIdDemo"
  }
````

## Tehniline platvorm

Süsteem on ehitatud Apereo CAS platvormile [CAS].

 