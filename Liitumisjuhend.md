---
permalink: Liitumisjuhend
---

# Liitumisjuhend

Käesolev juhend kirjeldab asutuse tegevusi TARA autentimisteenusega liitumisel.

## 1 liidetavat e-teenuse (või -teenuste) väljaselgitamine

Asutus selgitab, kas ja millistes e-teenustes RIA autentimisteenust soovitakse kasutada. Selleks:
- tutvuda TARA-teenuse [ärikirjeldusega](Arikirjeldus), teenustaseme leppega (SLA-ga), vajadusel ka [tehnilise kirjeldusega]()
- kasulik võib olla tutvuda teenuse [teekaardiga](https://e-gov.github.io/TARA-Doku/#teekaart).

## 2 liidestamistöö kavandamine ja teostamine
Asutus kavandab ja teostab teenuse kasutamiseks vajalikud arendustööd klientrakenduses.
  - autentimiskomponendi täiendamine OpenID Connect-ga või väljavahetamine
  - hinnanguline töömaht: kogenud arendajal u 2 päeva, kui OpenID Connect-i pole varem teinud, siis 2 nädalat.

## 3 testteenusega liitumine
Asutus esitab taotluse testteenusega liitumiseks. Taotluses näidata:
- e-teenus või -teenused, mille kasutajaid soovitakse TARA abil autentida
- kasutajate arvu prognoos
- nõusolek SLA-ga; kui on vajadus kõrgema SLA järele, siis rääkida varakult RIA-ga läbi
- klientrakenduse nime ettepanek (`client_id` OpenID Connect protokolli kohaselt)
- tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt
- klientrakenduse haldaja kontaktandmed (e-post, telefon)

Taotlus esitatakse ja edasine suhtlus teenuse haldamisel käib läbi RIA kasutajatoe, `help@ria.ee`.

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse salasõna `client_secret`. Salasõna on ette nähtud identsustõendi päringute allkirjastamiseks
- avab asutusele juurdepääsu testteenusele.

Testteenuse URL-id:

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara-test.ria.ee/oidc/.well-known`, `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara-test.ria.ee/oidc/jwks` |
| autentimine (_authorization_) | `https://tara-test.ria.ee/authorize` | 
| tõendiväljastus (_token_) | `https://tara-test.ria.ee/token` | 

## 4 liidestuse testimine
- Asutus testib liidestust.
- RIA abistab võimalike probleemide lahendamisel

## 5 toodanguteenusega liitumine
Eduka testimise järel asutus esitab taotluse toodanguteenuse avamiseks klientrakendusele. Taotluses näidata:
- klientrakenduse toodanguversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt

RIA avab toodanguteenuse.
