---
EestiAut
---

# Eesti autentimisteenus. Tarkvara ülevaade ja hinnang
{: .no_toc}

[https://github.com/ria-eidas/IdP](https://github.com/ria-eidas/IdP)

"Eesti autentimisteenus" on RIA eIDAS Node külge ühendatud rakendus, eIDAS-e terminoloogias _identity provider_, mis autendib välisriigist autentimisel suunatud Eesti ID-kaardi või m-ID kasutaja.

- TOC
{:toc}

## Alustarkvara

Rakenduse tarkvaras on aluseks võetud eIDAS Node (v 1.4) tarkvarakomponendid:
- eidas-commons
- eidas-configmodule
- eidas-encryption
- eidas-light-commons
- eidas-parent
- eidas-saml-engine

Seos Prantsusmaaga [https://github.com/france-connect/eidas-identity-provider](https://github.com/france-connect/eidas-identity-provider)?

## Juhtprogramm

Rakenduse juhtprogramm (ohjur) on klass `IdPMainController` (paketis `ee.ria.IdP`). Juhtprogrammis on meetodid rakenduse poole HTTP pöördumiste käsitlemiseks:

- HTTP `GET` päring rakenduse metateabe otspunkti `/metadata`. Vt jaotis "Metateabe väljastamine".

- HTTP `POST` päring `/auth`. Vt jaotis "Autentimise alustamine".

- HTTP `POST` päring `/idauth`. Vt jaotis "ID-kaardiga autentimine".

Samas on ka Eesti autentimisviise teostavad meetodid:

- ID-kaardiga autentimine
  - `readClientCertificate` ja
  - `parseClientCertificate`

- m-ID-ga autentimine
  - HTTP `POST` päringut `/midwelcome` käsitlev meetod `showMobileIdStart`
  - HTTP `POST` päringut `/midauth` käsitlev meetod `startMobileIdAuth`
  - HTTP `POST` päringut `/midcheck` käsitlev meetod
`showMobileIdCheck`
  - HTTP `GET` päringut `/midstatus` käsitlev meetod
`getMobileIdStatus`

## Metateabe väljastamine

SAML-metateave on XML-fail, mis väljastatakse vastuseks HTTP `GET` päringule rakenduse metateabe otspunkti `/metadata`.

Metateabe vastus koostatakse järgmiste klasside ja meetodite abil:
- Paketis `ee.ria.IdP.metadata` on määratletud liides `MetaDataI`, seda teostab klass `MetaDataImpl`, milles on üksainus meetod - `generateMetadata`.
- XML-vormingus vastuse kokkupanemiseks kasutatakse
paketi `eu.eidas.auth.engine.metadata` klassi `EidasMetadata` meetodit `generator` ja klassi `MetadataConfigParams` meetodit `builder`.
- Paketis `ee.ria.IdP.metadata` on ka liides `MetaDataConfigurationI`, milles on meetodid metateabe XML-i koostamiseks vajalike väärtuste sisselugemisest metateabe seadistusest:
    - `getLocalCountry`
    - `getMetaDataUrl`
    - `getPostBindingUrl`
    - `getTechnicalContact`
    - `getSupportContact`
    - `getOrganization`
    - `getSigningMethods`
    - `getDigestMethods`
    - `getEncryptionAlgorithms`

Küsimused:
- `tokenCache` roll

## Autentimise alustamine

HTTP `POST` päringuga otspunkti `/auth` saabub kasutaja autentimisele. Päring töödeldakse meetodiga `showWelcome`, mille tähtsaimaks lauseks on meetodi `parseRequest` väljakutse.

Paketis `ee.ria.IdP.eidas` on liides `EidasIdPI`, milles määratletud meetodid
- `parseRequest` - autentimispäringu parsimine ja kontroll
- `buildAuthenticationResponse` - eduka autentimise vastuse koostamine
- `buildErrorResponse` - ebaeduka autentimise vastuse koostamine.

Liidest teostab klass `EidasIdPImpl`.

`parseRequest` tuumtoiming on:

````
authRequest = protocolEngine.unmarshallRequestAndValidate(Base64.decode(samlRequest),
                    metaDataConfiguration.getLocalCountry());

checkRequestAttributes(authRequest);

authRequest = addRequestCallback(authRequest);
````

Sissetulnud päring dekodeeritakse ja valideeritakse. `protocolEngine` on eIDAS-tarkvarast. Meetodiga `checkRequestAttributes` kontrollitakse, kas meie teenus toetab päringus nõutud kohustuslikke atribuute. Kui ei, siis antakse veateade (InvalidAuthRequest). NB! Kahtlus, et siin ei ole atribuutide toe loogikast (mis on üldse segasevõitu) valesti tõlgendatud! Meetod `addRequestCallback` võtab kas autentimispäringust või päringu saatja metateabest aadressi, kuhu vastus saadetakse - `AssertionConsumerUrl`.

Kasutajaliides on tehtud JSP abil. Siin on töö ilmselt veel pooleli.

Kasutajale kuvatakse "Welcome" ja pakutakse valida, kas  autenditakse ID-kaardiga või m-ID-ga.

## ID-kaardiga autentimine

Meetodiga `readClientCertificate` loetakse ID-kaardilt sert. Seejärel meetodiga `parseClientCertificate` parsitakse sert ja loetakse välja vajalikud isikuandmed. Moodustatakse struktuur `EENaturalPerson naturalPerson`, mis on sisendiks meetodile `buildAuthenticationResponse`. Vea korral (kaart mitte lugejas vms) moodustakse vastus meetodiga `buildErrorResponse`.

## Eduka autentimise vastuse koostamine

Vastus koostatakse meetodis `buildAuthenticationResponse`. Meetodis kasutatakse eIDAS-tarkvara. Meetod ise on suhteliselt lühike.

Praegu tagastakse neli kohustuslikku atribuuti (isikukood, ees- ja perekonnanimi, sünniaeg). (See viitab veelkord ebaselgusele, kas kohustuslikke atribuute peab küsima ja kas kohustuslikke atribuute, mida ei küsita, peab väljastama (või tohib väljastada).)

Äriregistri päringut praegu teostatud ei ole, kuid repos on SOAP teegid, ilmselt selleks otstarbeks. (Arhitektuuriline küsimus: kas X-teed siia tihedalt külge keevitada?)

## Krüpto

Paketis `ee.ria.IdP.crypto` on klassid:
- `IdPEncryption` - laiendab eIDAS-tarkvara klassi `AbstractSamlEngineEncryption`
- `IdPkeyStore` - mahukas klass
- `IdPSigner` - laiendab eIDAS-tarkvara klassi `AbstractProtocolSigner`, sisuliselt ainult toetatavate algoritmide määramisega.