---
permalink: TehnilineKirjeldus
---

# Tehniline kirjeldus
{: .no_toc}
v 1.24, 09.05.2023

- TOC
{:toc}

## 1 Ülevaade

Käesolev dokument määratleb autentimisteenuse TARA tehnilised omadused ja annab soovitusi klientrakenduse e-teenusega liidestamiseks.

Autentimisteenus TARA on Riigi Infosüsteemi Ameti poolt pakutav teenus, millega asutus saab oma e-teenusesse lisada erinevate autentimismeetodite toe:

- ID-kaart
- Mobiil-ID
- Smart-ID
- EU eID (piiriülene autentimine läbi eIDAS taristu)

Tehniline kirjeldus on suunatud TARA liidestajatele (arendajatele). Lugejalt eeldame HTTP protokolli tundmist. Kasulik, kuid mitte vajalik on  OpenID Connect või OAuth 2.0 kogemus. Lugeja peab olema valmis vajadusel hankima lisateavet OpenID Connect protokolli originaaltekstist [Core].

Kui leiate vea või soovite midagi küsida, siis palun pöörduge meie kasutajatoe poole: `help@ria.ee`.

Tehnilises kirjelduses on püütud järgida ühtset terminoloogiat. Sõnaseletusi leiab [sonastikust](Sonastik) ja [viidatud](Viited) materjalides. Arvestama peab, et OpenID Connect, OAuth 2.0 jm mõistesüsteemid ei ole ideaalselt ühtlustatud. Näiteks, TARAga liidestuvat, e-teenust pakkuvat asutuse infosüsteemi nimetame siin "klientrakenduseks". OAuth ja mõneski muus kontekstis on klientrakendus aga "teenusepakkuja" (_service provider_).

### 1.1 OpenID Connect

Autentimisteenus TARA põhineb OpenID Connect protokollil ([Viited](Viited), [Core]), mis omakorda tugineb OAuth 2.0 protokollile. OpenID Connect ja OAuth 2.0 on ulatuslikud, paljude võimalustega standardid.

TARA-s on nimetatud protokollidest valitud piiritletud alamhulk ning tehtud mõned kohandused. Peamised valikud ja kohandused OpenID Connect täisprotokolliga võrreldes on järgmised:

- Teenus toetab volituskoodi voogu(_authorization code flow_). Volituskoodi voogu peetakse kõige turvalisemaks ja sellisena on avalike teenuste jaoks sobiv.
- Kogu teave autenditud kasutaja kohta edastatakse rakendusele identsustõendis (_ID token_).
- Rakendusele edastatakse ka eIDAS autentimistase, kui see on teada (`acr` väites).
- Teenus toetab kasutajaliidese keele-eelistuse andmist autentimispäringus (parameetriga `ui_locales`).
- Autentimismeetodi valib kasutaja autentimisteenuses või liidestunud infosüsteem (parameetri `scope` abil).
- Piiriülene autentimine, vastavalt eIDAS tehnilisele spetsifikatsioonile.
- Klientrakenduse dünaamilist registreerimist ei toetata. Klientrakenduse registreerimine toimub RIA-s eraldi protseduuriga.
- Ühekordset sisselogimist (SSO) ja seansihaldust (_session management_) ei toetata. Ühekordse sisselogimise teenuse jaoks kasutada Riigi SSO teenust (GovSSO) mille dokumentatsioon on leitav [siit](https://e-gov.github.io/GOVSSO/).

Sõltuvalt kasutajate tagasisidest võib TARA funktsionaalsus laieneda.

### 1.2 Siseriiklik ja piiriülene autentimine

TARA võimaldab nii siseriiklikku kui ka piiriülest autentimist. See tähendab, et autentida saab nii Eesti e-identimissüsteemi (ID-kaardi, Mobiil-ID jms kasutaja) kui ka EL teise liikmesriigi e-identimissüsteemi kasutaja.

eIDASe kontekstis teostab TARA kasutusvood "Eestlase autentimine Eesti e-teenuses" ja "Eesti e-teenust kasutava välismaalase autentimine" (joonis 1). Sh on võimalik välismaalase autentimist Eesti e-teenuse jaoks kasutada nii, et TARA kasutajaliidest kasutajale ei kuvata vaid kasutaja suunatakse otse välisriigi autentimisteenusesse (loe automaatse suunamise kohta p4.1.4)

<p style='text-align:left;'><img src='img/YLEVAADE.png' style='width:600px'></p>

Joonis 1. Siseriiklik ja piiriülene autentimine 

## 2 Autentimisprotsess kasutaja vaatest

1 Kasutaja on e-teenust osutavas klientrakenduses.

- kasutaja võib olla nii eestlane kui ka välismaalane
- kasutajale esitatakse kuva, millel on nupp "Logi sisse" vms
- kasutaja vajutab "Logi sisse".

2 Klientrakendus suunab kasutaja TARA-teenusesse (sirviku ümbersuunamiskorralduse abil)

- ümbersuunamis-URL-is on autentimispäring.

3 Kasutajale avaneb autentimismeetodi valiku kuva.  Siin võib kasutaja:

- valida ID-kaardiga autentimise (samm 4)
- valida Mobiil-ID-ga autentimise (samm 5)
- valida Smart-ID-ga autentimise (samm 6)
- valida EU eID-ga autentimise (piiriülese autentimise) (samm 7)
- pöörduda tagasi klientrakendusse.

4 ID-kaardiga autentimine

- algab kasutajale teabe kuvamisega autentimissertifikaadi kohta (vajalik võib olla sertifikaadi valik kasutaja poolt)
- kasutaja sisestab PIN1-koodi
- eduka autentimise korral edasi samm 8, vea korral samm 9.

5 Mobiil-ID-ga autentimine

- kasutaja sisestab mobiilinumbri ja isikukoodi
- kasutaja mobiilseadmele kuvatakse kontrollkood
- kinnituse ootamine
- eduka autentimise korral edasi samm 8, vea korral samm 9.

6 Smart-ID-ga autentimine

- kasutaja sisestab Eesti isikukoodi
- kasutaja mobiilseadmele kuvatakse kontrollkood
- kinnituse ootamine
- eduka autentimise korral edasi samm 8, vea korral samm 9.

7 EU eID-ga (piiriülene) autentimine

- kasutaja valib sihtriigi
- kasutaja suunatakse läbi eIDAS taristu välisriigi autentimisteenusesse
- kasutaja autendib end välisriigi autentimisvahendiga
- eduka autentimise korral (ning kui välisriigi autentimisvahendi tase on piisav) edasi samm 8
- vea korral samm 9.

NB! EU eID autentimise korral on võimalik sihtriigi valik teha ka otse TARA-ga liidestunud infosüsteemis ning saata soovitud riigi kood autentimispäringuga. Sellisel juhul kasutajale TARA kasutajaliidest ei kuvata ja tehakse automaatne edasisuunamine läbi eIDAS taristu otse välisriigi autentimisteenusesse, kus kasutaja autendib end välisriigi autentimisvahendiga. Eduka autentimise korral (ning kui välisriigi autentimisvahendi tase on piisav) edasi samm 8

8 Autenditud kasutaja

- suunatakse tagasi klientrakendusse
- klientrakendus pärib TARA serverilt identsustõendi
- identsustõend (_identity token_) on allkirjastatud tõend eduka autentimise kohta
- identsustõendis sisalduvad autentimisel tuvastatud kasutaja andmed (atribuudid)
- klientrakendus annab kasutajale asjakohasel viisil teada, et sisselogimine õnnestus.

9 Veateate lehelt

- saab kasutaja minna tagasi autentimismeetodi valikusse
- ja seal kas üritada uuesti, võimalik, et teise autentimismeetodiga
- või katkestada autentimise ja minna tagasi klientrakendusse.

10 Lisaks on kasutajal võimalik:

- saada täiendavat teavet TARA-teenuse kohta.

## 3 Autentimisvoog tehnilises vaates

Kirjeldame detailselt suhtluse sirviku, klientrakenduse serverikomponendi ja TARA serverikomponendi vahel.

Need kolm osapoolt suhtlevad HTTP päringute ja vastuste abil.

Käime läbi peamised päringud ja nende vastused (joonis 2).

<p style='text-align:center;'><img src='img/VOOG-01.PNG' style='width:400px'></p>

Joonis 2. Autentimispäring

Voog algab sirvikust. Klientrakendusest on sirvikusse laetud leht, millel kasutaja saab vajutada "Logi sisse" või alustada autentimist muul viisil.

Kasutaja nupuvajutuse tulemusena saadab sirvik klientrakendusele (täpsemalt klientrakenduse serverikomponendile) HTTP päringu **1a**. Klientrakendus võib autentimist alustada ka ise, kasutaja muu toimingu peale.

Klientrakendus koostab autentimispäringu. Autentimispäringu koosseis on kirjeldatud eraldi jaotises allpool. Klientrakendus saadab sirvikusse päringuvastuse **1b**. Päringuvastus sisaldab HTTP ümbersuunamiskorraldust (_redirect_) ja autentimispäringut.

Sirvik täidab ümbersuunamiskorralduse. See toimub nii, et sirvik võtab päringuvastusest **1b** autentimispäringu ja saadab  selle TARA serverikomponendile, päringuna **2a**. 

TARA serverikomponent, saades autentimispäringu **2a**, koostab autentimismeetodi valiku lehe ja saadab selle päringuvastusena **2b** sirvikusse.

Kasutajale kuvatakse autentimismeetodite valiku leht. Jätkame voo kirjeldamisega joonisel 3.

<p style='text-align:center;'><img src='img/VOOG-02.PNG' style='width:400px'></p>

Joonis 3. Tagasisuunamispäring

Kasutaja valib autentimismeetodi. Valik edastatakse TARA serverikomponendile HTTP päringuga **3a**.

Järgneb autentimisdialoog, vastavalt kasutaja poolt valitud autentimismeetodile. Autentimisdialoogis võidakse sirviku ja TARA serverikomponendi vahel vahetada mitmeid sõnumeid ja teha mitmeid ümbersuunamisi. Näiteks piiriülese autentimise korral saadetakse kasutaja mitmete ümbersuunamistega välisriigi autentimisteenusesse.
Neid päringuid ja vastuseid tähistame joonisel "...".

Autentimisdialoog jõuab lõpule ja kasutaja on vaja suunata tagasi klientrakendusse.

TARA serverikomponent saadab sirvikusse HTTP päringuvastuse **3b**, milles on ümbersuunamiskorraldus kasutaja tagasisuunamiseks klientrakendusse. 

Sirvik täidab ümbersuunamiskorralduse **3b**, saates klientrakenduse serverikomponendile HTTP päringu **4a** (tagasisuunamispäringu). 

Tagasisuunamispäringus sisaldub õnnestunud autentimise korral volituskood (_authorization code_). Tagasisuunamispäring on täpsemalt kirjeldatud eraldi jaotises allpool.

Volituskood lunastatakse autenditud isiku isikukoodi, nime jm isikuandmete vastu eraldi päringu tegemisega TARA serverikomponendi poole (joonis 4).   

<p style='text-align:center;'><img src='img/VOOG-03.PNG' style='width:400px'></p>

Joonis 4. Identsustõendipäring

Klientrakenduse serverikomponent saadab TARA serverikomponendile identsustõendipäringu **5a**. Identsustõendipäringus esitab klientrakendus tagasisuunamispäringus saadud volituskoodi. Klientrakendus tõendab oma ehtsust, lisades päringusse salasõna (_client secret_). Identsustõendipäring on nn _backend_-päring - see ei käi läbi sirviku.

TARA serverikomponent, saades identsustõendipäringu **5a**, kontrollib salasõna ja väljastab vastuses **5b** identsustõendi. Identsustõend sisaldab andmeid autentimise fakti (autentimise ajamoment, autentimismeetod) ja tuvastatud isiku kohta (isikukood, ees- ja perekonnanimi, piiriülese autentimise korral ka eraldi sünniaeg jm andmed). TARA serverikomponent allkirjastab identsustõendi. Identsustõend on täpsemalt kirjeldatud eraldi jaotises allpool.

Klientrakendus saab identsustõendi (**5b**). Rünnete vältimiseks peab klientrakendus kontrollima, et identsustõend on tõesti TARA poolt välja antud, klientrakendusele suunatud ja ajaliselt kehtiv.

Sellega on autentimine tehtud. Klientrakendus teab nüüd kasutaja isikut.

Tavaliselt loob klientrakendus seejärel kasutajaga seansi. Seansi loomine ei puutu enam TARA kompetentsi. 

Klientrakendus saadab sirvikusse HTTP vastuse **4b**, näiteks lehe "Sisse logitud".

## 4 Päringud

### 4.1 Autentimispäring

Autentimispäring on HTTP GET päring, millega kasutaja suunatakse klientrakendusest TARA-sse autentima.

Autentimispäringu näide (URL-i _query_-osa on loetavuse huvides jagatud mitmele reale):

````
GET https://tara.ria.ee/oidc/authorize?

redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback&
scope=openid&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Autentimispäringu elemendid:

| URL-i element          | kohustuslik | näide                       |  selgitus     |
|------------------------|:---------- :|-----------------------------|---------------|
| protokoll, host ja tee (_path_) | jah | `https://tara.ria.ee/oidc/authorize` | `/authorize` on TARA-teenuse OpenID Connect-kohane autentimisotspunkt (termin 'autoriseerimine' pärineb alusprotokollist OAuth 2.0). |
| `redirect_uri` | jah | `redirect_uri=https%3A%2F%2F` `eteenus.asutus.ee%2Ftagasi` | Tagasisuunamis-URL. Tagasisuunamis-URL-i valib asutus ise. Tagasisuunamis-URL võib sisaldada _query_-osa. <br><br>Vajadusel kasutada [URLi kodeerimist](https://en.wikipedia.org/wiki/Percent-encoding). <br><br>URI-i [fragmendi osa](https://tools.ietf.org/html/rfc3986#section-3.5) (`#` märk ja sellele järgnev osa) kasutamine [ei ole lubatud](https://tools.ietf.org/html/rfc6749#section-3.1.2). |
| `scope` | jah | `scope=openid`<br><br>`scope=openid%20eidas` <br><br>`scope=openid%20idcard%20mid` | Autentimise skoop.<br><br>`openid` on kohustuslik (seda nõuab OpenID Connect protokoll).<br><br> Skoopidega `idcard`, `mid`, `smartid`, `eidas` (ja `eidasonly`) saab nõuda, et kasutajale näidatakse ainult soovitud autentimismeetodeid. Vt jaotis 4.1.4 Autentimismeetodite valikuline kasutus.<br><br>Skoobiga `email` saab nõuda, et identsustõendis väljastatakse kasutaja e-posti aadress. Vt jaotis 4.1.2 E-posti aadressi küsimine.<br><br>Piiriülesel autentimisel saab kasutada lisaskoope sihtriigi valiku täpsustamiseks, et kasutaja suunata otse välisriigi autentimisteenusesse või täiendavate isikuandmete pärimiseks (vt jaotis 4.1.4 ja 4.1.1).<br><br>Mitme skoobi kasutamisel tuleb skoobid eraldada tühikutega. Tühik esitatakse seejuures URL-kodeeringus (`%20`) ([RFC 3986](https://www.ietf.org/rfc/rfc3986.txt)). Skoobi väärtused on tõstutundlikud. Lubatud on ainult käesolevas dokumendis kirjeldatud skoobid, teiste väärtuste korral tagastatakse viga koodiga `invalid_scope`. |
| `state` | jah | `state=hkMVY7vjuN7xyLl5` | Võltspäringuründe (_cross-site request forgery_, CSRF) vastane turvakood. `state` moodustamise ja kontrollimise kohta vt lähemalt jaotis "Võltspäringuründe vastane kaitse". |
| `response_type` | jah | `response_type=code` | Määrab autentimise tulemuse serverile edastamise viisi. Toetatud on volituskoodi viis (OpenID Connect protokolli _authorization flow_), selle tähiseks on väärtus `code`. |
| `client_id` | jah | `client_id=58e7...` | Rakenduse identifikaator. Rakenduse identifikaatori annab RIA asutusele klientrakenduse registreerimisel autentimisteenuse kasutajaks. |
| ~~`locale`~~ | ~~ei~~ | ~~`locale=et`~~ | ~~Kasutajaliidese keele valik. Toetatakse keeli `et`, `en`, `ru`. Vaikimisi on kasutajaliides eesti keeles. Kasutaja saab keelt ise valida.~~ <br><br>Märkus. Parameetrit toetatakse kuni juuli 2019 lõpuni (kasuta `ui_locales` parameetrit selle asemel). |
| `ui_locales` | ei | `ui_locales=et` | Kasutajaliidese keele valik. Toetatakse keeli `et`, `en`, `ru`. Vaikimisi on kasutajaliides eesti keeles. Kasutaja saab keelt ise valida. |
| `nonce` | ei | `fsdsfwrerhtry3qeewq` | Taasesitusründeid vältida aitav unikaalne parameeter, vastavalt protokollile ([Viited](Viited), [Core], jaotis 3.1.2.1. Authentication Request). Parameeter `nonce` ei ole kohustuslik. |
| `acr_values` | ei | `acr_values=high` | Nõutav minimaalne eIDAS autentimistase isikutuvastuseks kasutatavale autentimismeetodile (loe rohkem jaotisest "eIDAS autentimistasemed"). Lubatud on määrata üks väärtus järgmisest loetelust: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Kui määramata, siis vaikimisi `substantial` (märkimisväärne). <br><br>Kui `acr_values` väärtus on määratud, kuvab TARA kasutajale ainult autentimismeetodid, mille tase on sama või kõrgem kui `acr_values` väärtus. Piiriüleste autentimisvahendite korral edastatakse nõue välisriigi eIDAS autentimisteenusele. <br><br>TARA-lt saadud identsustõendis tuleb `acr` väite vastavust kontrollida nõutud minimaalsele tasemele (vt jaotis 5.1 Identsustõendi kontrollimine). |

#### 4.1.1 Atribuutide küsimine välismaalase kohta

Välismaalase autentimisel suunab TARA välismaalase tema koduriigi autentimisteenusesse. Euroopa Komisjoni määrusega on riigid kokku leppinud, et teise riigi autentimisteenus on alati kohustatud väljastama füüsilise isiku kohta neli atribuuti: 1) eesnimi; 2)  perekonnanimi; 3) sünniaeg; 4) isikukood vm identifikaator.

Füüsilise isiku autentimise korral tagastatakse TARAs alati nn **kohustuslikud atribuudid** (eesnimi, perenimi, sünniaeg ja isiku identifikaator). Täiendavate lisaatribuutide pärimist ning juriidilise isiku autentimist läbi TARA hetkel ei toeta.

#### 4.1.2 E-posti aadressi küsimine

Skoobiga `email` saab nõuda, et identsustõendis väljastatakse kasutaja e-posti aadress. See võimalus on suunatud asutustele, kelle klientrakendus nõuab kasutaja autentimisel e-postiaadressi kindlakstegemist. Skoop `email` tuleb lisada põhiskoobile `openid`. Identsustõendis väljastatakse väited (_claims_) `email` ja `email_verified`. Näiteks:

```
"sub": "EE60001019906",
"email": "60001019906@eesti.ee",
"email_verified": false
```

Väite `email` väärtus loetakse kasutaja autentimissertifikaadi SAN laiendist (RFC822 tüüpi Subject Alternative Name väljast). E-posti aadress väljastatakse ainult juhul, kui kasutaja autenditakse Eesti ID-kaardiga. Klientrakenduses tuleb kindlasti arvestada, et kasutaja ei tarvitse olla oma e-posti suunanud - s.t sellel aadressil saadetud kiri ei tarvitse kasutajani jõuda.

Väite `email_verified` väärtus on alati `false`. See tähendab, et TARA ei kontrolli ega väljasta teavet, kas kasutaja on oma eesti.ee e-posti aadressi suunanud või mitte. (Vastav funktsionaalsus võib lisanduda tulevikus).

#### 4.1.3 Mobiilinumbri küsimine

Skoobiga `phone` saab nõuda, et Mobiil-ID'ga autentimisel väljastatakse identsustõendis kasutaja telefoninumber. See võimalus on suunatud asutustele, kes kasutavad Mobiil-ID allkirjastamist, mis nõuab sisendina ka kasutaja telefoninumbrit. Skoop `phone` tuleb lisada põhiskoobile `openid`. Identsustõendis väljastatakse väited (_claims_) `phone_number` ja `phone_number_verified`. Näiteks:

```
"sub": "EE60001019906",
"phone_number": "+37200000766",
"phone_number_verified": true
```

Väite `phone_number` väärtus on E.164 formaadis telefoninumber koos riikliku suunakoodiga.

Väite `phone_number_verified` väärtus on alati `true`. See tähendab, et `phone_number` väites väljastatud telefoninumbri kuulumine autenditud isikule on autentimisprotsessi käigus kontrollitud.

Väited `phone_number` ja `phone_number_verified` väljastatakse ainult juhul, kui kasutaja autentimiseks kasutatakse Mobiil-ID'd.

#### 4.1.4 Autentimismeetodite valikuline kasutus

Vaikimisi kuvatakse kasutajale kõik toetatud ja eIDAS autentimistaseme poolt lubatud autentimismeetodid. Soovi korral on kuvatavaid autentimisvalikuid võimalik juhtida `scope` parameetri väärtuste abil. Sobivaid autentimismeetodeid on võimalik kombineerida, koostades loetelu soovitud autentimismeetoditest (lubatud väärtuste nimekiri on toodud tabelis 1).

Autentimismeetodite valikulise kasutuse korral on täiendava turvameetmena vajalik identsustõendis `amr` väite kontroll (loe ka jaotis 5.1 Identsustõendi kontrollimine).

Tabel 1 - autentimisvalikute kuvamine

| Parameetri scope väärtus | Selgitus                       |
|--------------------------|--------------------------------|
| `idcard` | Eesti ID-kaardiga autentimise lubamine |
| `mid` | Mobiil-ID autentimise lubamine |
| `smartid` | Smart-ID autentimise lubamine |
| `eidas` | Piiriülese autentimise valiku lubamine |
| `eidasonly` | Ainult piiriülese autentimise valiku lubamine. <br><br>NB! `eidasonly` kasutuse korral ignoreeritakse alati kõiki ülejäänud autentimisvalikute eelistusi. |
| `eidas:country:xx` | Piiriülese autentimise riik (TARA riigi valiku lehte ei kuvata). Töötab ainult koos `eidasonly` autentimisvalikuga. Lubatud skoopide nimekiri on teenuseteabe otspunktis. |

Näide 1: Kõik autentimismeetodid
`scope=openid`

Näide 2: Ainult ID-kaardi ja Mobiil-ID kasutus
`scope=openid%20idcard%20mid`

Näide 3: Ainult piiriülene autentimine
`scope=openid%20eidas`

Näide 4: Piiriülene autentimine ilma TARA kasutajaliideses riigi valikuta. Riik valitakse autentimist alustanud infosüsteemis.
`scope=openid%20eidasonly%20eidas%3Acountry%3Abe`

### 4.2 Tagasisuunamispäring

Tagasisuunamispäring on HTTP GET päring, millega kasutaja suunatakse autentimise järel TARA-st tagasi klientrakendusse.

Tagasisuunamine tehakse klientrakenduse poolt autentimispäringus kaasa antud naasmisaadressile. Tagasisuunamispäringus edastab TARA klientrakendusele volituskoodi (_authorization code_), mille alusel klientrakendus pärib (eraldi päringuga) TARA-lt autenditud isiku isikukoodi, nime jm atribuudid. Tehniliselt tehakse tagasisuunamine HTTP _redirect_-päringuga.

Tagasisuunamispäringu näide:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````

Tagasisuunamispäringu elemendid:

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee (_path_) | `https://eteenus.asutus.ee/tagasi` | Ühtib autentimispäringus saadetud `redirect_uri` väärtusega. |
| `code` | `code=71ed579...`  | Volituskood (_authorization code_) on ühekordne “lubatäht” identsustõendi saamiseks. |
| `state`            | `state=OFfVLKu0kNbJ2EZk`     | Võltspäringuründe vastane turvakood. Autentimispäringus saadetud turvakood peegeldatakse tagasi. `state` moodustamise ja kontrollimise kohta vt lähemalt jaotis "Võltspäringuründe vastane kaitse". |

Päring võib sisaldada muid URL-i parameetreid, mida klientrakendus peab ignoreerima.

**Veateade tagasisuunamispäringus.** Kui TARA ei suutnud autentimispäringut töödelda - päring kas oli vigane, kasutaja otsustas autentimise katkestada või tekkis muu viga, siis saadab TARA tagasisuunamispäringus veakoodi (URL-i parameeter `error`) ja veakirjelduse (URL-i parameeter `error_description`).

Juhul kui kasutaja katkestab TARA-s autentimise ("Tagasi teenusepakkuja juurde" link esilehel), tagastatakse veakood `user_cancel`.  

Muude veakoodide tagastamisel lähtub TARA OpenID Connect standardist (loe võimalike veakoodide kohta [siit](https://openid.net/specs/openid-connect-core-1_0.html#AuthError) ja [siit](https://tools.ietf.org/html/rfc6749#section-4.1.2.1)) ja veakirjelduse tekst esitatakse alati inglisekeelsena. 


Tagastatakse ka `state`, kuid volituskoodi (`code`) ei saadeta. Nt:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
error=invalid_scope&
error_description=Required+scope+%3Copenid%3E+not+provided.+
TARA+do+not+allow+this+request+to+be+processed&
state=qnYY56Ra8QF7IUzqvw+PPLzMKoHtQkuUWbV/wcrkvdU=
````

Tagasisuunamispäringu vigade korral on tavaliselt probleem liidestuses ja `error_description` parameetris esitatavat veakirjeldust ei ole vaja otse kasutajale kuvada. Klientrakenduses tuleks kontrollida, kas saadeti veakood. 

**Autentimise katkestamine**. Kasutaja võib e-teenusesse tagasi pöörduda ka ilma autentimismeetodit valimata ja autentimist läbi tegemata (link "Tagasi teenusepakkuja juurde"). See võimalus on mõeldud juhuks, kui kasutaja vajutas klientrakenduses "Logi sisse", kuid tegelikult ei soovi sisse logida. 

Autentimise katkestamise korral suunatakse kasutaja tagasi teenusepakkuja juurde tagasisuunamispäringuga, mille veakood on `user_cancel` <sup>1</sup> 

<sup>1</sup> Alates augustist 2020 ei nõua RIA teenusega liitumise taotluses asutuselt enam eraldi URL-i tagasipöördumisurli kasutaja katkestamise jaoks (URL, kuhu suunatakse juhul kui kasutaja vajutab "Tagasi teenuspakkuja juurde". NB! OpenID Connect protokolli kohane tagasisuunamis-URL ja siin nimetatud URL on erineva tähendusega).

### 4.3 Identsustõendipäring

Identsustõendipäring on HTTP POST päring, millega klientrakendus pärib TARA serverilt identsustõendi (_ID token_).

Identsustõendipäringu näide (HTTP POST päringu keha on loetavuse huvides jagatud mitmele reale):

````
POST /oidc/token HTTP/1.1
Host: tara.ria.ee
Content-Type: application/x-www-form-urlencoded
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi
````

Identsustõendipäringus tuleb esitada salasõna. Selleks tuleb päringusse lisada `Authorization` päis (_request header_), väärtusega, mis moodustatakse sõnast `Basic`, tühikust ja Base64 kodeeringus stringist `<client_id>:<client_secret>` (vt _RFC 2617 HTTP Authentication: Basic and Digest Access Authentication_, jaotis 2 _Basic Authentication Scheme_).

HTTP POST päringu keha peab olema esitatud OpenID Connect protokolli kohaselt serialiseeritud [kujul](https://openid.net/specs/openid-connect-core-1_0.html#FormSerialization).

Päringu kehas tuleb esitada järgnevad parameetrid:

| POST päringu keha element | näide                    |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.ria.ee/oidc/token` |   |
| `grant_type`  | `grant_type=authorization_code` | Protokollikohane nõutav väärtus `authorization_code`. |
| `code` | `code=Splx...` | Autentimisteenuselt saadud volituskood. | 
| `redirect_uri` | `redirect_uri=https%3A%2F` | Autentimispäringus saadetud ümbersuunamis-URI. |

#### 4.3.1 Identsustõend

TARA server kontrollib, et identsustõendit küsiv klientrakendus on TARAs registreeritud. Seejärel väljastab päringu vastuses (_HTTP response body_) identsustõendi.

Päringu vastus on JSON-struktuur, milles on neli elementi (vt järgnev tabel). 

| element | selgitus |
|:-------:|----------|
| `access_token` | OAuth 2.0 pääsutõend. Pääsutõendiga saab klientrakendus pärida `userinfo` otspunktist autenditud isikut kirjeldavad andmed.<br><br>TARA väljastab küll pääsutõendi, kuid soovitame pääsutõendit kasutada ainult siis, kui nn "karbitoote" liidestamisel ei ole võimalust kasutada identsustõendit (vt allpool). Kõik autenditud isikut kirjeldavad andmed väljastatakse juba identsustõendis. Identsustõendi kasutamine on eelistatud ja ka teoreetiliselt turvalisem, kuna identsustõend on allkirjastatud, `userinfo` otspunkti väljund aga mitte |
| `token_type` | Väärtusega `bearer`. OAuth 2.0 pääsutõendi tüüp |
| `expires_in` | OAuth 2.0 pääsutõendi aegumiskestus |
| `id_token` | identsustõend. Veebitõend esitatakse nn kompaktselt serialiseeritud kujul (vt [JWS Compact Serialization](https://tools.ietf.org/html/rfc7515#section-3.1)) | 

Päringu vastus võib sisaldada muid välju, mida klientrakendus peab ignoreerima.

Identsustõend on TARA poolt väljastatav tõend autentimise fakti kohta.

Identsustõend väljastatakse [veebitõendi](https://jwt.io/) (JSON Web Token, JWT) vormingus.

Identsustõend on alati [allkirjastatud](https://tools.ietf.org/html/rfc7515#section-5.2). 

Näide (identsustõendi sisu e _payload_):

````json
{
  "jti": "0c597356-3771-4315-a129-c7bc1f02a1b2",
  "iss": "https://tara-test.ria.ee",
  "aud": "TARA-Demo",
  "exp": 1530295852,
  "iat": 1530267052,
  "nbf": 1530266752,
  "sub": "EE60001019906",
  "profile_attributes": {
    "date_of_birth": "2000-01-01",
    "family_name": "O’CONNEŽ-ŠUSLIK TESTNUMBER",
    "given_name": "MARY ÄNN"
  },
  "amr": [
    "mID"
  ],
  "state": "1OnH3qwltWy81fKqcmjYTqnco9yVQ2gGZXws/DBLNvQ=",
  "nonce": "",
  "at_hash": "X0MVjwrmMQs/IBzfU2osvw=="
}
````

Identsustõendis väljastatakse järgmised väited (_claims_).

| identsustõendi element (väide) | näiteväärtus, selgitus     |
|:-----------------------|---------------------|
| `jti` (_JSON Token Identifier_) | `0c597356... ` - identsustõendi identifikaator |
| `iss` (_Issuer_)       | `https://tara.ria.ee` - tõendi väljastaja (TARA-teenus); testteenuse puhul `https://tara-test.ria.ee` |
| `aud` (_Audience_)     | `TARA-Demo` - autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `exp` (_Expires_) | `1530295852` - tõendi aegumisaeg, Unix _epoch_ vormingus |
| `iat` (_Issued At_) | `1530267052` - tõendi väljaandmisaeg, Unix _epoch_ vormingus |
| `nbf` (_Not Before_)   | `1530266752` - tõendi kehtivuse algusaeg, Unix _epoch_ vormingus |
| `sub` (_Subject_)      | `EE60001019906` - autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator) koos kodaniku riigikoodi eesliitega (riigikoodid vastavalt ISO 3166-1 alpha-2 standardile). NB! eIDAS identifikaator võib olla maksimaalselt kuni 256 tähemärki. |
| `profile_attributes`   | autenditud isikut kirjeldavad andmed |
| `profile_attributes`<br>`.date_of_birth` | `2000-01-01` - autenditud kasutaja sünnikuupäev ISO_8601 formaadis. Tagastatakse ainult Eesti isikukoodiga isikute puhul ning eIDAS autentimisel |
| `profile_attributes`<br>`.given_name` | `MARY ÄNN` - autenditud kasutaja eesnimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `profile_attributes`<br>`.family_name` | `O’CONNEŽ-ŠUSLIK` - autenditud kasutaja perekonnanimi (testnimi, valitud täpitähtede jm eritärkide sisalduvuse pärast) |
| `profile_attributes`<br>`_translit` | Sisaldab JSON objekti ladina tähestikus profiiliatribuutidest (vt allpool translitereerimine.). Väärtustatud ainult eIDAS autentimisel |
| `amr` (_Authentication Method Reference_) | `mID` - kasutaja autentimiseks kasutatud autentimismeetod. Võimalikud väärtused: `mID` - mobiil-ID, `idcard` - Eesti ID-kaart, `eIDAS` - piiriülene, `smartid` - Smart-ID  |
| `state` | `abcdefghijklmnop` - turvaelement. Autentimispäringu `state` parameetri väärtus.  |
| `nonce` | `qrstuvwxyzabcdef` - turvaelement. Autentimispäringu `nonce` parameetri väärtus. Väärtustatud ainult juhul kui autentimispäringus saadeti `nonce` parameeter. |
| `acr` (_Authentication Context Class Reference_) | `high` - autentimistase, vastavalt eIDAS tasemetele. Võimalikud väärtused: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Elementi ei kasutata, kui autentimistase ei kohaldu või pole teada |
| `at_hash` | `X0MVjwrmMQs/IBzfU2osvw==` - pääsutõendi räsi. TARA-s ei kasutata |
| `email` | `60001019906@eesti.ee` - kasutaja e-posti aadress. Väljastatakse ainult  Eesti ID-kaardiga kasutaja autentimisel. Loetakse kasutaja autentimissertifikaadi SAN laiendist (RFC822 tüüpi `Subject Alternative Name` väljast) |
| `email_verified` | `false` - tähendab, et e-posti aadressi kuulumine kasutajale on tuvastatud. TARA väljastab alati väärtuse `false`. See tähendab, et TARA ei kontrolli ega väljasta teavet, kas kasutaja on oma eesti.ee e-postiaadressi suunanud või mitte. |
| `phone_number`| `+37200000766` - kasutaja telefoninumber. Väljastatakse ainult  Eesti Mobiil-ID'ga kasutaja autentimisel. Telefoninumber esitatakse E.164 formaadis koos riikliku suunakoodiga. | 
| `phone_number_verified` | `true` - tähendab, et telefoninumbri kuulumine kasutajale on tuvastatud. TARA väljastab alati väärtuse `true`. |

Identsustõend võib sisaldada muid OpenID Connect protokolli kohaseid välju, kuid neid teenuses ei kasutata. 

Klientrakendus peab identsustõendile järgi tulema kohe tagasisuunamispäringu saamisel. Kui identsustõendit ei pärita `30` sekundi jooksul, siis volituskood (_authorization code_) aegub ja autentimisprotsessi tuleb korrata.

### 4.4 Kasutajainfopäring

Kasutajainfopäring võimaldab kehtiva  `OAuth 2.0` pääsutõendi alusel küsida infot autenditud kasutaja kohta. Päring peab olema esitatud HTTP GET meetodil. Kehtiva pääsutõendi korral väljastatakse JSON vormingus vastus.

Pääsutõend tuleb esitada kasutajainfot väljastavale otspunktile [Bearer Token meetodil](https://tools.ietf.org/html/rfc6750#section-2.1) HTTP päises (soovituslik) või [URLi parameetrina](https://tools.ietf.org/html/rfc6750#section-2.3).

Näide 1 - pääsutõendi edastamine `Authorization` päises:
````
GET /oidc/profile HTTP/1.1
Host: tara.ria.ee
Authorization: Bearer AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0
````

Näide 2 - pääsutõendi edastamine `access_token` parameetrina :
````
GET /oidc/profile?access_token=AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0 HTTP/1.1
Host: tara.ria.ee
````

Kehtiva pääsutõendi korral väljastatakse JSON vormingus vastus.

Näide:

````json
{
  "amr": [
    "mID"
  ],
  "date_of_birth": "2000-01-01",
  "family_name": "O’CONNEŽ-ŠUSLIK TESTNUMBER",
  "given_name": "MARY ÄNN",
  "sub": "EE60001019906",
  "auth_time": 1550735597
}
```` 

Vastuses esitatavad väited väljastatakse identsustõendi alusel. 

| json element (väide) | väljastamine kohustuslik | selgitus | 
|:-----------------------|---------------------|-------------------|
| `auth_time` | jah | Kasutaja eduka autentimise ajahetk. Unix *epoch* vormingus |
| `sub` (_Subject_) | jah | Vormingult ja tähenduselt sama, mis `sub` identsustõendis |
| `given_name` | jah | Vormingult ja tähenduselt sama, mis `profile_attributes.given_name` identsustõendis |
| `family_name` | jah | Vormingult ja tähenduselt sama, mis `profile_attributes.family_name` identsustõendis |
| `amr` | jah | Vormingult ja tähenduselt sama, mis `amr` identsustõendis |
| `date_of_birth` |  ei <sup>1</sup> | Vormingult ja tähenduselt sama, mis `profile_attributes.date_of_birth` identsustõendis |
| `email` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `email` identsustõendis |
| `email_verified` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `email_verified` identsustõendis |
| `phone_number` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `phone_number` identsustõendis |
| `phone_number_verified` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `phone_number_verified` identsustõendis |
| `acr` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `acr` identsustõendis |

 <sup>1</sup> Väljastatakse ainult juhul, kui antud väide on esitatud ka identsustõendis.

Päringu vastus võib sisaldada muid välju, mida klientrakendus peab ignoreerima.

**Vigade käsitlemine**

Juhul kui kasutajainfo otspunktile esitatav pääsutõend puudub või on aegunud tagastatakse veakood ja lühikirjeldus `WWW-Authenticate` päises vastavalt [OpenID Connect Core 1.0 spetsifikatsioonile](https://openid.net/specs/openid-connect-core-1_0.html#UserInfoError)

Näide:
````
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer error="invalid_token",error_description="The access token has expired"
````

## 5 Turvatoimingud

### 5.1 Identsustõendi kontrollimine

Klientrakendus peab identsustõendit kindlasti kontrollima. Teostada tuleb kõik protokollikohased (OpenID Connect ja selle alusprotokoll OAuth 2.0) kontrollid.
{: .adv}

Kontrollida tuleb:

- tõendi allkirja
- tõendi väljaandjat
- tõendi adressaati
- tõendi ajalist kehtivust
- kasutaja autentinud autentimismeetodit tõendis
- ülepiirilise autentimise korral eIDAS autentimistaset tõendis

Lähemalt nendest kontrollidest allpool. Vajadusel saate täpsemat teavet OpenID Connect ja OAuth 2.0 protokollikirjeldustest.

#### 5.1.1 Allkirja kontrollimine

Identsustõend väljastatakse autentimisteenuse TARA poolt allkirjastatult. Allkiri vastab JWT standardile.

Allkirjaalgoritmina kasutab TARA `RS256`. Klientrakendus peab suutma vähemalt selle algoritmiga antud allkirja kontrollida. Allkirja kontrollimine on otstarbekas teostada standardse JWT teegiga, mis toetaks kõiki JWT algoritme. Seda vähetõenäoliseks, kuid siiski võimalikuks juhuks, kui `RS256`-s peaks avastatama turvanõrkus, mis tingib algoritmi vahetamise.

Allkirja kontrollimisel tuleb kasutada TARA avalikku allkirjavõtit (edaspidi "võti"). Võtme saate võtmeväljastuse otspunktist (vt allpool jaotis "Otspunktid"). 

Võti on tüüpiliselt stabiilne. Vahetame võtit vastavalt turvasoovitustele, kuid ei ole välistatud erakorraline võtmevahetus võtme korrumpeerumise korral. Võtmevahetuse protsessis järgime  [OpenID Connect](https://openid.net/specs/openid-connect-core-1_0.html#RotateSigKeys) standardit.

Võtmel on identifikaator (`kid`). Võtmeidentifikaatorite osas järgime [OpenID Connect](https://openid.net/specs/openid-connect-core-1_0.html#Signing) ja OAuth 2.0 soovitatud praktikat, mis teeb võimalikuks võtmevahetuse ilma teenuse katkestuseta. 

Võtmevahetusel on lühikese perioodi jooksul võtmeväljastuse otspunktist nähtavad kaks võtit, kumbki oma identifikaatoriga.

Identsustõendi päringu vastuses väljastab TARA klientrakendusele ka `kid` (JWT päise (_header_) element `kid`). See `kid` osutab võtmele, mida peate väljastatud tõendi allkirja kontrollimisel kasutama.

Soovitame võtit puhverdada (koos `kid` väärtusega), kuna see vähendab TARA serveri poole tehtavate päringute arvu. Kuid aktsepteeritav on ka võtme pärimine igas autentimises.

**Eeltoodust lähtuvalt peab allkirja kontrollimisel teostama järgnevad tegevused:**

1 - JWT päisest lugema element `kid` väärtuse.

2.1 - Juhul kui võtit ei puhverdata teostama päringu võtmeväljastuse otspunktile ning võtma JWT päisest saadud `kid` väärtusele vastava võtme.

2.2 - Võtme puhverdamisel tuleb seda teha koos `kid` väärtusega. Valideerimisel võrrelda vastusest saadud `kid` väärtust puhverdatud võtme `kid` väärtusega. Juhul kui väärtused kattuvad kasutada puhverdatud võtit. Mittekattumise korral tuleb teostada päring võtmeväljastuse otspunktile ning võtta JWT päisest saadud `kid` väärtusele vastav võti ning puhverdada.

3 - Teostama allkirja kontrolli JWT päises oleva `kid` väärtusele vastava võtmega.

Ülalesitatu teostamist võib mõjutada, kas liidestate TARA-ga "karbitoodet", üritate hakkama saada mõne OpenID Connect teegi seadistamisega või programmeerite liidestuse ise. Teegid ja karbitooted ei tarvitse puhverdamist toetada.

NB! Kindlasti tasuks vältida võtme käsitsi kirjutamist klientrakenduse konfiguratsiooni. Võtme vahetusest teatatakse tüüpiliselt küll ette (va kiirest turvavajadusest tingitud vahetus), kuid selline lahendus vajab klientrakenduse poolset käsitsi seadistust ning toob kaasa teenuse katkemise perioodiks mil TARA kasutab juba uut võtit aga klientrakendusse ei ole uut võtit seadistatud.

#### 5.1.2 Otspunktide TLS ühenduse kontrollimine

Klientrakendusest TARA poole sooritatavatel päringutel (kõikidesse otspunktidesse ehk teenuseteabe otspunkti, võtmeväljastusotspunkti, tõendiväljastusotspunkti) tuleb TLS ühenduse algatamisel sooritada korrektselt kõik vajalikud kontrollid. Soovitav on neid mitte ise implementeerida, vaid kasutada mõnda HTTPS või TLS ühenduste funktsionaalsusega teeki.

TLS ühenduse usaldusahelaks peab määrama ainult [DigiCert Global Root CA](https://cacerts.digicert.com/DigiCertGlobalRootCA.crt.pem) juursertifikaadi. Soovitav ei ole usaldada teiste CA-de sertifikaate ega tervet operatsioonisüsteemi CA sertifikaatide hoidlat. Vajadusel võib TLS ühenduse usaldusahelaks määrata DigiCert Global Root CA asemel [lõppsertifikaadi](https://github.com/e-gov/TARA-Doku/blob/master/certificates/star_ria_ee_valid_until_2023-11-22.crt), mis vahetub vähemalt iga aasta tagant.

HTTPS või TLS ühenduste funktsionaalsusega teek peab iga ühenduse algatamisel teostama järgnevad kontrollid:
* kontrollima, kas moodustub valiidsete allkirjadega sertifikaadiahel, mis lõpeb [DigiCert Global Root CA](https://cacerts.digicert.com/DigiCertGlobalRootCA.crt.pem) juursertifikaadiga;
* kontrollima, kas päringus olev _hostname_ (`tara.ria.ee` või `tara-test.ria.ee`) vastab serveri esitatud sertifikaadis olevale CN väljale või sisaldub SAN väljal;
* kontrollima ahela kõikidel sertifikaatidel kehtivuse alguse ja lõpu väärtuseid;
* kontrollima sertifikaatides defineeritud _constraint_'e (basic, name, key usage, critical extensions).

#### 5.1.3 Tõendi väljaandja kontrollimine

Identsustõendi elemendi `iss` väärtus peab olema `https://tara-test.ria.ee` (TARA testkeskkonna puhul) või `https://tara.ria.ee` (TARA toodangukeskkonna puhul).

#### 5.1.4 Tõendi adressaadi kontrollimine

Klientrakendus peab kontrollima, et saadud tõend on välja antud just temale. Selleks veenduda, et identsustõendi elemendi `aud` väärtus ühtib klientrakendusele registreerimisel väljaantud kliendinimega (_Client ID_).

#### 5.1.5 Tõendi ajalise kehtivuse kontrollimine

Kontrollitakse kolme identsustõendis sisalduva elemendi abil - `iat`, `nbf`, `exp`. Klientrakendus kasutab kontrollimisel oma kellaaega. Kontrollida tuleks, et: 

1) "not before" ajamoment on kätte jõudnud:

`nbf <= jooksev_aeg + kellade_lubatud_erinevus` 

2) "expired" ajamoment ei ole kätte jõudnud:

`exp > jooksev_aeg - kellade_lubatud_erinevus`.

`kellade_lubatud_erinevus` väärtus valida ise. Need kontrollid on vajaliku rünnete ja sassiminekute vältimiseks.

TARA põhimõte on, et identsustõendile tuleb järgi tulla kohe, 30 sekundi jooksul. Selle aja ületamisel identsustõendit ei väljastatagi.

#### 5.1.6 Autentimismeetodi kontrollimine

Juhul kui kasutusel on autentimismeetodite valikuline kuvamine (vt jaotis 4.1.4 Autentimismeetodite valikuline kasutus), peab identsustõendis veenduma, et identsustõendi `amr` väites (_authentication method reference_) toodud autentimismeetod on lubatud.  Vastasel juhul võetakse vahendajaründe risk, kus autentimispäringu `scope` parameetri manipuleerimise läbi on võimalik kasutajal autentida meetodiga, mis pole liidestuja süsteemis aktsepteeritav (nt ID-kaardiga autentimise asemel kasutatakse Smart-ID-d).

Näide: Kui autentimispäringus on `scope` parameetris määratud ainult ID-kaart, tuleb veenduda, et identsustõendi `amr` väide sisaldaks koodi `idcard` (koodide nimekiri on toodud jaotises 4.3.1 Identsustõend).

#### 5.1.7 Minimaalse lubatud eIDAS autentimistaseme kontrollimine

Välistamaks ligipääsu soovitust madalama turvalisusastmega autentimisvahenditele, peab autentimisel kontrollima, et identsustõendi `acr` väites esitatud autentimistase ei oleks väiksem minimaalsest lubatud autentimistasemest (loe autentimistasemete kohta [siit](https://www.ria.ee/media/1971/download)).

Näiteks, kui liidestuja soovib kasutada vaid kõrge eIDAS autentimistasemega autentimisvahendeid ja täpsustab `acr_values` parameetris `high` väärtuse, tohib aktsepteerida ainult identsustõendeid, mille `acr` väite väärtus on `high`.

Juhul kui autentimispäringus eIDAS autentimistaset `acr_values` parameetri abil ei täpsustatud, peab identsustõendis olev väärtus olema `substantial` või `high`.


#### 5.1.8 Seansi loomine

Identsustõendi eduka kontrollimise järel loob klientrakendus kasutajaga seansi ("logib kasutaja sisse"). Seansi loomine ja pidamine on klientrakenduse kohustus. Kuidas seda teha, ei ole enam autentimisteenuse TARA skoobis.

Märkus. Tavaliselt peetakse veebirakendusega seanssi küpsises hoitava seansitõendi (_session token_) abil. Seansitõend võib olla juhusõneline (_opaque_) või veebitõend (JWT). Vt lähemalt [Seansihaldus](Seansihaldus). Identsustõend ei sobi otseselt seansitõendiks, sest identsustõendi kehtivusaeg väljendab tõendi väljastamise perioodi, mitte seansi kehtivusperioodi. Küll aga saab klientrakendus seansitõendi koostada identsustõendi põhjal, valides seansi sobiva kehtivusaja. 

### 5.2 Võltspäringuründe vastane kaitse

Klientrakenduses tuleb rakendada võltspäringuründe (_cross-site request forgery_, CSRF) vastaseid kaitsemeetmeid. Seda tehakse turvakoodide `state` ja `nonce` abil. `state` kasutamine on kohustuslik; `nonce` kasutamine on vabatahtlik. Kirjeldame `state` kasutamise protseduuri, kasutades klientrakenduses seatud küpsist (sellisel juhul ei pea klientrakendus saadetud state parameetri väärtust ise meeles pidama).

Turvakoodi `state` kasutatakse autentimispäringule järgneva tagasisuunamispäringu võltsimise vastu. Klientrakenduses tuleb teostada järgmised sammud:

1 Genereerida juhusõne, näiteks pikkusega 16 tärki: `XoD2LIie4KZRgmyc` (tähistame `R`).

2 Arvutada juhusõnest `R` räsi `H = hash(R)`, näiteks SHA256 räsialgoritmiga ja teisendades tulemuse Base64 vormingusse: `vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`.

3 Seada vahetult enne autentimispäringu tegemist klientrakenduse domeenile küpsis, mille väärtuseks juhusõne `R`. 

`Set-Cookie ETEENUS=XoD2LIie4KZRgmyc; HttpOnly`,

kus `ETEENUS` on vabalt valitud küpsisenimi. Küpsisele tuleb rakendada atribuuti `HttpOnly`.

4 Seada TARA autentimispäringu tegemisel p 2 arvutatud räsi parameetri `state` väärtuseks:

`GET ... state=vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`

Parameetri `state` pikkus peab olema minimaalselt 8 tähemärki.

Tagasisuunamispäringu töötlemisel peab klientrakendus tegema järgmist:

5 Võtab päringuga tuleva küpsise `ETEENUS` väärtuse (tagasisuunamispäringuga saadetakse kasutaja kaks asja: kasutaja brauserist juhusõne küpsisena ja juhusõnest arvutatud räsiväärtus `state` parameetris).

6 Arvutab küpsise väärtusest räsi ja teisendab base64 kodeeringusse.

7 Kontrollib, et räsi ühtib tagasisuunamispäringus tagasipeegeldatava `state` väärtusega.

Tagasisuunamispäringut tohib aktsepteerida ainult ülalkirjeldatud kontrolli õnnestumisel.

Kirjeldatud protseduuris on võtmetähtsusega väärtuse `state` sidumine sessiooniga. Seda tehakse küpsise abil. (See on autentimise ajutine sessioon. Töösessiooni moodustab klientrakendus pärast autentimise edukat lõpuleviimist).

Täiendav teave: OpenID Connect protokollis kahjuks ei ole teema selgelt esitatud. Mõningast teavet saab soovi korral mitteametlikust dokumendist [OpenID Connect Basic Client Implementer's Guide 1.0](https://openid.net/specs/openid-connect-basic-1_0.html), jaotis "2.1.1.1 Request Parameters".

Soovi korral võite veel tutvuda ründe (ja kaitse) detailse seletusega: [Võltspäringurünne ja kaitse selle vastu](Volts).  

### 5.3 Logimine

Logimine peab võimaldama rekonstrueerida TARA ja klientrakenduse suhtluse käigu TARA iga kasutuse jaoks. Selleks tuleb nii TARA kui ka klientrakenduse poolel logida kõik päringud ja päringute vastused: [autentimispäring](#41-autentimisp%C3%A4ring), [tagasisuunamispäring](#42-tagasisuunamisp%C3%A4ring) ja [identsustõendipäring](#43-identsust%C3%B5endip%C3%A4ring). Kuna edastatavad andmemahud ei ole suured, siis tuleb logida nii URL kui ka identsustõend täielikul kujul. Logide säilitamistähtaja määramisel arvestada klientrakenduse olulisust. Orientiiriks pakume 1..7 aastat. Probleemide lahendamiseks pöördumisel palume esitada väljavõte logist (mis päringud TARA poole saadeti? mis saadi vastuseks?).
 
## 6 Otspunktid ja aegumisajad

6.1 Testteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | [https://tara-test.ria.ee/.well-known/openid-configuration](https://tara-test.ria.ee/.well-known/openid-configuration) <br>[https://tara-test.ria.ee/oidc/.well-known/openid-configuration](https://tara-test.ria.ee/oidc/.well-known/openid-configuration) |
| teenuse avalik allkirjastamisvõti | [https://tara-test.ria.ee/oidc/jwks](https://tara-test.ria.ee/oidc/jwks) |
| klientrakenduse registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | [https://tara-test.ria.ee/oidc/authorize](https://tara-test.ria.ee/oidc/authorize) | 
| tõendiväljastus (_token_) | [https://tara-test.ria.ee/oidc/token](https://tara-test.ria.ee/oidc/token) | 

6.2 Toodanguteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | [https://tara.ria.ee/.well-known/openid-configuration](https://tara.ria.ee/.well-known/openid-configuration) <br>[https://tara.ria.ee/oidc/.well-known/openid-configuration](https://tara.ria.ee/oidc/.well-known/openid-configuration) |
| teenuse avalik allkirjastamisvõti | [https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks) |
| klientrakenduse registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | [https://tara.ria.ee/oidc/authorize](https://tara.ria.ee/oidc/authorize) | 
| tõendiväljastus (_token_) | [https://tara.ria.ee/oidc/token](https://tara.ria.ee/oidc/token) | 

6.3 Aegumisajad (_timeout_)

| aegumisaeg  | väärtus  | märkus
|---------------------|----------|
| TARA seansi aegumisag | 30 min | TARA server loob autentimise läbiviimiseks TARAsse saabuva kasutajaga oma seansi. Kui kasutaja TARA esilehel midagi ei tee, siis 30 min järel seanss aegub. TARA seanssi ei tohi segi ajada autentimise järel klientrakenduse ja kasutaja vahel loodava seansiga. |
| SSL/TLS kätluse aegumisaeg | 25 s | Omab tähtsust ID-kaardiga autentimisel. Kasutaja peab 25 s jooksul jõudma PIN1 sisestada. Selle aja ületamisel turvalisuse kaalutlustel autentimine katkestatakse. | 
| OAuth volituskoodi aegumisaeg  | 30 s | Klientrakendus peab volituskoodi (_authorization code_) abil identsustõendi välja lunastama 30 s jooksul. |
| identsustõendi (ja OAuth juurdepääsutõendi (_access token_)) aegumisaeg | 40 s | Identsustõendis on märgitud tõendi aegumise aeg. Turvalisuse kaalutlustel on tõendi kehtivuse periood seatud lühikeseks (40 sekundit). Klientrakendus ei tohi aegunud tõendit kasutada. Märgime, et identsustõend ei sobi klientrakenduse ja kasutaja vahelise seansi tõendiks. Kui klientrakendus soovib veebitõendi (JWT) vormingus seansitõendit kasutada, siis peaks ta looma identsustõendi alusel uue tõendi. |


## 7 Soovitusi liidestajale

TARA-ga liidestamine on lihtne. Siiski on vaja töid kavandada ja hoolikalt teostada.

Liidestuja peab erilist tähelepanu pöörama, et kõik protokollikohased kontrollid saaksid tehtud - turvaelemendi `state` ja kui kasutatakse, siis ka `nonce` kontroll, identsustõendi kontroll jm. Vt [ID token validation](http://openid.net/specs/openid-connect-core-1_0.html#ImplicitIDTValidation) [Core].

Liidestamise protsess näeb välja järgmine.

Asutus peaks välja selgitama, kas ja millistes oma e-teenustes soovib TARA kasutada. Selleks tuleks tutvuda TARA [ärikirjeldusega](Arikirjeldus), teenustaseme leppega (SLA-ga), käesoleva [tehnilise kirjeldusega](TehnilineKirjeldus). Vajadusel pidada nõu RIA-ga, `help@ria.ee`.

Seejärel kavandada ja teostada teenuse kasutamiseks vajalik arendustöö - klientrakenduse täiendamine OpenID Connect protokolli kohase klientkomponendiga, sh testimine. Hinnanguline töömaht: kogenud arendajal u 2 päeva; kui OpenID Connect-i pole varem teinud, siis 2 nädalat. Aluseks käesolev [tehniline kirjeldus](TehnilineKirjeldus)

Arenduse valmides tuleb liidest testida. Selleks kasutatakse TARA testteenust. Asutus esitab taotluse testteenusega liitumiseks. Taotluse võib esitada juba enne arenduse algust. Taotluses teatab asutus:

- teenuse, millega soovitakse liituda (test- või toodanguteenus)
- kinnituse, et liituja on välja arendanud omapoolse liidese ja seda TARA testteenuse vastu testinud (toodanguteenusega liitumise puhul)
- e-teenuse või -teenused, mille kasutajaid soovitakse TARA abil autentida
- kasutajate arvu prognoosi
- kohustumuse kasutada teenust eesmärgipäraselt. Sh testteenust kasutada ainult testimiseks, mitte toodangus autentimiseks
- nõustumuse teenustasemega (SLA-ga)
- klientrakenduse identifikaatori ettepanek -`client_id`, OpenID Connect protokolli kohaselt
- klientrakenduse testversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt. Lubatud on ainult HTTPS protokoll. URL-i fragmendi osa (`#` sümbol ja sellele järgnev osa) kasutamine ei ole lubatud.
- autentimismeetod või meetodid, mida soovitakse kasutada
- klientrakenduse haldaja kontaktandmed (e-post, telefon, isikukood).

Taotlus esitatakse ja edasine suhtlus teenuse haldamisel käib läbi RIA kasutajatoe, `help@ria.ee`. Vt lähemalt [RIA autentimisteenuste lehel](https://www.ria.ee/et/riigi-infosusteem/eid/partnerile.html#tara).

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse salasõna `client_secret`. Salasõna on ette nähtud identsustõendi päringute allkirjastamiseks
- avab asutuse klientrakenduse testversioonile juurdepääsu testteenusele.

Järgneb liidestuse testimine. RIA abistab siin võimalike probleemide lahendamisel. Vaata lähemalt [testimise juhiseid](https://e-gov.github.io/TARA-Doku/Testing).

Tasub pilk heita [abi peatükile](Abi).

Liitumine TARA toodanguteenusega. Eduka testimise järel asutus esitab taotluse toodanguteenuse avamiseks klientrakendusele. Taotluses näidatakse klientrakenduse toodanguversiooni tagasisuunamis-URL (`redirect_uri`), OpenID Connect protokolli kohaselt jm andmed

RIA, rahuldades taotluse, väljastab asutusele klientrakenduse toodanguversiooni salasõna `client_secret` ja avab asutuse klientrakenduse toodanguversioonile juurdepääsu toodanguteenusele.

## 8 eIDAS autentimistasemed

eIDAS autentimistase on [eIDAS määruse](https://eur-lex.europa.eu/legal-content/ET/TXT/HTML/?uri=CELEX:32014R0910&from=EN) kohaselt autentimisvahendile määratud usaldusväärtuse tase (kõrge, märkimisväärne, madal), mis on määratud rakendusmääruse [(EL) 2015/1502](https://eur-lex.europa.eu/legal-content/ET/TXT/HTML/?uri=CELEX:32015R1502&from=EN) sätete alusel. Taseme määramisel hinnatakse autentimisvahendi kasutuse erinevaid aspekte - identiteedi väljastamise alused, lahenduse haldus, tehniline lahendus ja organisatoorsed protsessid (loe rohkem [siit](https://www.ria.ee/sites/default/files/content-editors/EID/eidas-autentimistasemed.pdf)). 

TARA-s kasutatavate siseriiklike autentimisvahendite autentimistasemed kinnitab RIA. Autentimistaseme määramisel tuginetakse eIDAS rakendusmääruses toodud autentimistasemete nõuetele. 

Siseriiklikele autentimismeetoditele on TARA-s määratud järgmised autentimistasemed: 

| Autentimismeetod | Autentimistase | 
|---------------|-------------|
| ID-kaart | [kõrge](https://ec.europa.eu/digital-building-blocks/wikis/display/EIDCOMMUNITY/Estonia) | 
| Mobiil-ID | [kõrge](https://ec.europa.eu/digital-building-blocks/wikis/display/EIDCOMMUNITY/Estonia) | 
| Smart-ID | [kõrge](https://www.ria.ee/media/586/download) <sup>1</sup> |

<sup>1</sup>NB! Tase kehtib vaid Eesti isikukoodiga isikutele, kellele on Smart-ID konto. Tuleb arvestada, et mitteresidendid (Eesti e-residendid) pole eristatavad residentidest.

Välisriigi autentimismeetodite autentimistasemed on määratud vastava välisriigi enda poolt ja neist teavitatakse teisi liikmesriike vastavalt [eIDAS määruse](https://eur-lex.europa.eu/legal-content/ET/TXT/HTML/?uri=CELEX:32015R1502&from=EN) nõuetele. Nimekirja kõigist ülepiiriliselt kasutatavatest autentimismeetoditest, millest riigid on teisi liikmesriike teavitanud, leiab [siit](https://ec.europa.eu/cefdigital/wiki/display/EIDCOMMUNITY/Overview+of+pre-notified+and+notified+eID+schemes+under+eIDAS).

## 9 Erasektori asutuse erisused

Erasektori asutuste liidestumisprotsessid ja autentimisvood on avaliku sektori asutustega identsed.

Võrreldes avaliku sektori asutustega on erasektori asutuste jaoks autentimisel lubatud kohustuslik skoop `openid` koos piiriülese eIDAS-autentimise skoopidega `eidas`, `eidasonly`, `eidas:country:xx`. Vt ka [`scope` parameetri kohta autentimispäringus.](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#41-autentimisp%C3%A4ring)

`eidas:country:xx` skoop töötab üksnes koos `eidasonly` autentimisvalikuga.

`eidas:country:xx` skoop võimaldab TARAga liidestujal jätta vahele TARA kasutajaliidese kuva ning suunata autentija vahetult skoobis määratud riigi autentimisteenusesse.

Erasektori asutustele on hetkel toetatud autentimiseks järgnevad riigid:

| Riik     | Riigikood |
|----------|-----------|
| Belgia   | BE        |
| Portugal | PT        |
| Rootsi   | SE        |

NB! Riigi Infosüsteemi Amet ei taga teiste riikide autentimisteenuste toimimist. Erasektori autentimise võimalus sõltub eIDAS liikmesriikidest ning võib vastavalt liikmesriikide tegevusele muutuda.

## Muutelugu

| Versioon, kuupäev | Muudatus |
|-----------------|--------------|
| 1.24, 09.05.2023   | Formaadi ja kirjavigade parandused. Autentimisvahendite nimetuste ühtlustamine, EU eID kui autentimismeetod (piiriülene autentimine eIDAS taristus). |
| 1.23, 29.03.2023   | Formaadi ja kirjavigade parandused. Autentimisvahendite nimetuste ühtlustamine, EU eID kui autentimisvahend (piiriülene autentimine eIDAS taristus). |
| 1.22, 29.03.2023   | Lisatud viide Riigi SSO teenusele (GovSSO). Parendatud sõnastust. |
| 1.21, 10.11.2022   | Täpsustatud "5.1.2 Otspunktide TLS ühenduse kontrollimine" peatükis juhiseid. |
| 1.20, 16.09.2022   | Lisatud peatükk "9 Erasektori asutuse erisused". |
| 1.19, 28.10.2021   | Identsustõendi kehtivusaeg muudetud 10-lt minutilt 40 sekundile.<br>Lisatud [standardijärgse teenusteabe otspunkti tee](https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderConfig) `/.well-known/openid-configuration`. Senine tee `/oidc/.well-known/openid-configuration` jääb tagasiühilduvuseks alles. |
| 1.18, 26.05.2021   | Täpsustatud päringutes tundmatute väljade ja väärtuste lubatavust. Täpsustatud nõudeid `state` parameetrile ning tagasisuunamis-URL-ile. |
| 1.17, 19.03.2021   | Identsustõendi allkirja kontrollimise täpsustused. |
| 1.16, 29.12.2020   | `banklink` skoobi parameetri eemaldamine. |
| 1.15, 01.10.2020   | `sub` väite täpsustus - eidas identifikaatori pikkusepiirang. |
| 1.14, 04.08.2020   | Lisandus valikuline skoop `phone`. Täiendatud `acr_values` parameetri ja autentimistasemete käsitlust. |
| 1.13, 26.06.2020   | Täiendatud kasutajapoolset autentimise katkestamise käsitlust. Lisandus veakood `user_cancel`.  |
| 1.12, 27.04.2020   | Parandus ja täpsustus võltspäringuründe vastase kaitse kirjelduses. |
| 1.11, 29.01.2020   | Täpsustatud piiriülese autentimise kirjeldust. |
| 1.10, 16.01.2020   | Täpsustatud identsustõendi vormingu kirjeldust. |
| 1.9, 21.11.2019   | Lisatud skoobid `eidas:country:xx`. |
| 1.8, 20.05.2019   | Täpsustatud identsustõendi kontrolle `acr` ja `amr` väidete osas. |
| 1.7, 07.05.2019   | Täpsustatud autentimisprotsessiga seotud olulised aegumisajad. |
| 1.6, 02.04.2019   | Täpsustatud atribuutide tagastamist ülepiirilise autentimise korral. Täpsustatud väidete `state` ja `nonce` kirjeldusi identsustõendis. |
| 1.5, 21.03.2019   | Täpsemalt kirjeldatud identsustõendi allkirja kontrollimist |
| 1.4, 18.03.2019   | Täpsustatud tagasisuunamispäringu kirjeldust vea korral. |
| 1.3, 21.02.2019   | Lisatud kasutajainfopäringu kirjeldus. |
| 1.2, 01.02.2019   | Autentimismeetodite valikuline kasutus `scope` parameetri abil. |
| 1.1, 29.11.2018   | Täpsustused autentimispäringu parameetri osas (`redirect_uri`). |
| 1.0, 03.10.2018   | Eemaldatud Danske pank autentimismeetodite toe koosseisust |
| 0.9, 18.09.2018   | Eemaldatud mobiilinumber identsustõendi koosseisust |
| 0.8, 18.06.2018   | Täiendused seoses Smart-ID toega. |
| 0.7, 24.05.2018   | Täiendused seoses pangalinkide toega. |
| 0.6, 22.04.2018   | Täiendatud autentimisvoo tehnilist kirjeldust. Struktuuri parendusi |
| 0.5, 16.04.2018   | Translitereerimise täpsustused; võltspäringu vastane kaitse üksikasjalikumalt kirjeldatud; täpsemalt kirjeldatud identsustõendi kontrollimine; lisatud skoop `eidasonly` |
| 0.4, 30.01.2018   | Translitereerimise täiendused piiriülese autentimise korral (eIDAS) |
| 0.3, 30.01.2018   | Lisatud piiriülene autentimine (eIDAS) |
| 0.2, 28.11.2017   | Lisatud ID-kaardiga autentimine |
| 0.1, 10.10.2017   | Mobiil-ID-ga autentimine. |