---
permalink: Liitumisjuhend
---

# Liitumisjuhend

Käesolev juhend kirjeldab teenust kasutava asutuse ja teenusepakkuja (RIA) tegevusi TARA autentimisteenuse kasutuselevõtul.

Vt ka: [Sonastik](Sonastik), [Viited](Viited)

## Selgitada välja liidetavav e-teenus (või -teenused)

Asutus selgitab välja, kas ja millistes oma e-teenustes soovib TARA autentimisteenust kasutada. Selleks palume:
- tutvuda TARA-teenuse [ärikirjeldusega](Arikirjeldus), teenustaseme leppega (SLA-ga), vajadusel ka [tehnilise kirjeldusega](TehnilineKirjeldus)
- heita pilk ka teenuse [teekaardiga](https://e-gov.github.io/TARA-Doku/#teekaart)
- vajadusel pidada nõu RIA-ga, `help@ria.ee`.

## Kavandada ja teostada arendus
Asutus kavandab ja teostab teenuse kasutamiseks vajalikud arendustööd. 
  - klientrakenduse täiendamine OpenID Connect protokolli kohase klientkomponendiga
  - hinnanguline töömaht: kogenud arendajal u 2 päeva, kui OpenID Connect-i pole varem teinud, siis 2 nädalat
  - aluseks [tehniline kirjeldus](TehnilineKirjeldus)
  - kasulikku võib leida [makettrakendusest](https://github.com/e-gov/TARA-Client).

## Liituda TARA testteenusega
Asutus esitab taotluse testteenusega liitumiseks. Taotluse võib esitada juba enne arenduse algust. Taotluses palume teatada:
- e-teenus või -teenused, mille kasutajaid soovitakse TARA abil autentida
- kasutajate arvu prognoos
- nõustumine teenuse SLA-ga; kui on vajadus kõrgema SLA järele, siis rääkida RIA-ga läbi
- klientrakenduse nime ettepanek -`client_id` OpenID Connect protokolli kohaselt
- klientrakenduse testversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt
- klientrakenduse haldaja kontaktandmed (e-post, telefon)

Taotlus esitatakse ja edasine suhtlus teenuse haldamisel käib läbi RIA kasutajatoe, `help@ria.ee`.

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse salasõna `client_secret`. Salasõna on ette nähtud identsustõendi päringute allkirjastamiseks
- avab asutuse klientrakenduse testversioonile juurdepääsu testteenusele.

Testteenuse URL-id:

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara-test.ria.ee/oidc/.well-known`, `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara-test.ria.ee/oidc/jwks` |
| autentimine (_authorization_) | `https://tara-test.ria.ee/authorize` | 
| tõendiväljastus (_token_) | `https://tara-test.ria.ee/token` | 

## Testida liidestust
- Asutus testib liidestust
- RIA abistab võimalike probleemide lahendamisel.

## Liituda TARA toodanguteenusega
Eduka testimise järel asutus esitab taotluse toodanguteenuse avamiseks klientrakendusele. Taotluses näidata:
- klientrakenduse toodanguversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt.

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse toodanguversiooni salasõna `client_secret`
- avab asutuse klientrakenduse toodanguversioonile juurdepääsu toodanguteenusele.

Toodanguteenuse URL-id:

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara.ria.ee/oidc/.well-known`, `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara.ria.ee/oidc/jwks` |
| autentimine (_authorization_) | `https://tara.ria.ee/authorize` | 
| tõendiväljastus (_token_) | `https://tara.ria.ee/token` | 