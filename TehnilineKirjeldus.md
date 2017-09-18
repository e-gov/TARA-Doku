---
permalink: TehnilineKirjeldus
---

# Tehniline kirjeldus
{: .no_toc}

- TOC
{:toc}

## Mõisted

- _TARA autentimisteenus_, lühidalt TARA-teenus, Riigi Infosüsteemi Ameti  osutatav teenus, millega asutus saab oma e-teenusesse lisada mobiil-ID kasutaja autentimise toe. Teenuse II arendusjärgus lisatakse eIDAS jt autentimismeetodite tugi, hiljem võimalik, et ka ühekordse sisselogimise (SSO) tugi.<br>
- _e-teenus_ - kodanikule, aga ka välismaalasele osutatav elektrooniline teenus.<br>
- _klientrakendus_ - TARA autentimisteenust kasutav süsteem.<br>
- _eestlane_ - Eesti eID kasutaja.<br>
- _välismaalane_ -  Euroopa Liidu teise riigi eID kasutaja.<br>
- _registreerimine_ - klientrakenduse registreerimine TARA-teenuse kasutajaks, vastavalt OpenID Connect protokolli nõuetele.<br>
- _RIA_, Riigi Infosüsteemi Amet, osutab TARA-teenust.

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
  - _Kliendirakenduse makett_ (_mock-up_).

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

## Viited

[eIDAS] eIDAS Technical specification v.1.1, [https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS+Profile](https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/eIDAS+Profile).<br>
[CAS] [Apereo CAS - Enterprise Single Sign On](https://github.com/apereo/cas)
[CAS Service Management]<br>[https://apereo.github.io/cas/4.2.x/installation/Service-Management.html](https://apereo.github.io/cas/4.2.x/installation/Service-Management.html)<br>
[iGOV] OpenID Connect avaliku sektori profiil [http://openid.net/specs/openid-igov-openid-connect-1_0-02.html](http://openid.net/specs/openid-igov-openid-connect-1_0-02.html)

 