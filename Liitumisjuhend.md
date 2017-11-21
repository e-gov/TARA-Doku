---
permalink: Liitumisjuhend
---

{% include Issue.html %}

# Liitumisjuhend
{: .no_toc}

- TOC
{:toc}

Käesolev juhend kirjeldab teenust kasutava asutuse ja teenusepakkuja (RIA) tegevusi TARA autentimisteenuse kasutuselevõtul.

Vt ka: [Sonastik](Sonastik), [Viited](Viited)

## 1 Selgitada välja liidetavav e-teenus (või -teenused)

Asutus selgitab välja, kas ja millistes oma e-teenustes soovib TARA autentimisteenust kasutada. Selleks palume:
- tutvuda TARA-teenuse [ärikirjeldusega](Arikirjeldus), teenustaseme leppega (SLA-ga), vajadusel ka [tehnilise kirjeldusega](TehnilineKirjeldus)
- heita pilk ka teenuse [teekaardiga](https://e-gov.github.io/TARA-Doku/#teekaart)
- vajadusel pidada nõu RIA-ga, `help@ria.ee`.

## 2 Kavandada ja teostada arendus
Asutus kavandab ja teostab teenuse kasutamiseks vajalikud arendustööd. 
  - klientrakenduse täiendamine OpenID Connect protokolli kohase klientkomponendiga
  - hinnanguline töömaht: kogenud arendajal u 2 päeva, kui OpenID Connect-i pole varem teinud, siis 2 nädalat
  - aluseks [tehniline kirjeldus](TehnilineKirjeldus)
  - kasulikku võib leida [makettrakendusest](https://github.com/e-gov/TARA-Client).

Eriti tähele panna:<br>
1) identsustõendit tuleb nõuetekohaselt kontrollida, vt [ID token validation](http://openid.net/specs/openid-connect-core-1_0.html#ImplicitIDTValidation) [Core]<br>
2) parameeter `state` tuleb siduda sessiooniga, vt [OpenID Connect Basic Client Implementer's Guide 1.0](https://openid.net/specs/openid-connect-basic-1_0.html), jaotis "2.1.1.1 Request Parameters".  

## 3 Liituda TARA testteenusega
Asutus esitab taotluse testteenusega liitumiseks. Taotluse võib esitada juba enne arenduse algust. Taotluses palume teatada:<br>
1) teenus, millega soovitakse liituda (test- või toodanguteenus)<br>
2) kinnitus, et liituja on välja arendanud omapoolse liidese ja seda TARA testteenuse vastu testinud (toodanguteenusega liitumise puhul)<br>
3) e-teenus või -teenused, mille kasutajaid soovitakse TARA abil autentida<br>
4) kasutajate arvu prognoos<br>
5) kohustumus kasutada teenust eesmärgipäraselt. Sh testteenust kasutada ainult testimiseks, mitte toodangus autentimiseks<br>
6) nõustumus teenustasemega (SLA-ga); kui on vajadus kõrgema SLA järele, siis rääkida RIA-ga läbi - vt [TARA testkeskkonna teenustase (SLA)](SLATest)<br>
7) klientrakenduse identifikaatori ettepanek -`client_id` OpenID Connect protokolli kohaselt<br>
8) klientrakenduse testversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt<br>
9) autentimismeetod või meetodid, mida soovitakse kasutada<br>
10) klientrakenduse haldaja kontaktandmed (e-post, telefon, isikukood).

Taotlus esitatakse ja edasine suhtlus teenuse haldamisel käib läbi RIA kasutajatoe, `help@ria.ee`.

[Taotluse vorm](TaotluseVorm)

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse salasõna `client_secret`. Salasõna on ette nähtud identsustõendi päringute allkirjastamiseks
- avab asutuse klientrakenduse testversioonile juurdepääsu testteenusele.

## 4 Testida liidestust
- Asutus testib liidestust
- RIA abistab võimalike probleemide lahendamisel.

## 5 Liituda TARA toodanguteenusega
Eduka testimise järel asutus esitab taotluse toodanguteenuse avamiseks klientrakendusele. Taotluses näidata:
- klientrakenduse toodanguversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt.

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse toodanguversiooni salasõna `client_secret`
- avab asutuse klientrakenduse toodanguversioonile juurdepääsu toodanguteenusele.

## 6 URL-id

Testteenuse URL-id:

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara-test.ria.ee/oidc/.well-known`, `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara-test.ria.ee/oidc/jwks` |
| autentimine (_authorization_) | `https://tara-test.ria.ee/authorize` | 
| tõendiväljastus (_token_) | `https://tara-test.ria.ee/token` | 

Toodanguteenuse URL-id:

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | `https://tara.ria.ee/oidc/.well-known`, `https://tara.ria.ee/oidc/.well-known/openid-configuration` |
| teenuse avalik allkirjastamisvõti | `https://tara.ria.ee/oidc/jwks` |
| autentimine (_authorization_) | `https://tara.ria.ee/authorize` | 
| tõendiväljastus (_token_) | `https://tara.ria.ee/token` | 

----

_Viimati muudetud: 21.11.2017_
