---
permalink: TehnilineKirjeldus
---

Mõned autentimismeetodid võivad olla veel arenduses või kasutatavad ainult testkeskkonnas. Vt [teenuse seisund](index).
{: .note}

# Tehniline kirjeldus
{: .no_toc}
v 0.8, 18.06.2018

- TOC
{:toc}

## 1 Ülevaade

Käesolev dokument määratleb autentimisteenuse TARA tehnilised omadused ja annab soovitusi klientrakenduse e-teenusega liidestamiseks.

Autentimisteenus TARA on Riigi Infosüsteemi Ameti poolt pakutav teenus, millega asutus saab oma e-teenusesse lisada erinevate autentimismeetodite toe:

- mobiil-ID
- ID-kaart
- piiriülese (eIDAS-)autentimise tugi
- Smart-ID
- Coop pank
- LHV pank
- Luminor pank
- SEB pank
- Swedbank

Käesolev tehniline kirjeldus on suunatud TARA liidestajatele (arendajatele). Lugejalt eeldame HTTP protokolli tundmist. Kasulik, kuid mitte vajalik on  OpenID Connect või OAuth 2.0 kogemus. Lugeja peab olema valmis vajadusel hankima lisateavet OpenID Connect protokolli originaaltekstist [Core].

Tehnilises kirjelduses on püütud järgida ühtset terminoloogiat. Sõnaseletusi leiab [sonastikust](Sonastik) ja [viidatud](Viited) materjalides. Arvestama peab, et OpenID Connect, OAuth 2.0 jm mõistesüsteemid ei ole ideaalselt ühtlustatud. Näiteks, TARAga liidestuvat, e-teenust pakkuvat asutuse infosüsteemi nimetame siin "klientrakenduseks". OAuth ja mõneski muus kontekstis on klientrakendus aga "teenusepakkuja" (_service provider_).

### 1.1 OpenID Connect

Autentimisteenus TARA põhineb OpenID Connect protokollil ([Viited](Viited), [Core]), mis omakorda tugineb OAuth 2.0 protokollile. OpenID Connect ja OAuth 2.0 on ulatuslikud, paljude võimalustega standardid.

TARA-s on nimetatud protokollidest valitud TARA kasutusjuhtudele vajalikud kasutusvood ja omadused ning tehtud mõned kohandused. Peamised valikud ja kohandused OpenID Connect täisprotokolliga võrreldes on järgmised:

- Teenus toetab volituskoodi voogu(_authorization code flow_). Volituskoodi voogu peetakse kõige turvalisemaks ja sellisena on avalike teenuste jaoks sobiv.
- Kogu teave autenditud kasutaja kohta edastatakse rakendusele identsustõendis (_ID token_). OAuth 2.0 ligipääsutõendit (_access token_) ja UserInfo otspunkti kaudu kasutaja atribuutide andmist ei toetata.
- Rakendusele edastatakse ka eIDAS autentimistase, kui see on teada (`acr` väites).
- Teenus toetab kasutajaliidese keele-eelistuse andmist autentimispäringus (`locale` parameetriga (kuni juuli 2019 lõpp) või `ui_locales` (alates jaan 2019 lõpp)).
- Autentimismeetodi valib kasutaja autentimisteenuses.
- Piiriülene autentimine, vastavalt eIDAS tehnilisele spetsifikatsioonile.
- Allkirjavõtme otspunktis esitatakse üksainus võti. Dünaamilist võtmevahetust (_key rollover_) praegu ei toetata.
- Klientrakenduse dünaamilist registreerimist ei toetata. Klientrakenduse registreerimine toimub RIA-s eraldi protseduuriga.
- Ühekordset sisselogimist (SSO) ja seansihaldust (_session management_) praegu ei toetata.

TARA edasiarendamisel - mis toimub lähtudes TARA kasutajate vajadustest ja võimalustest neid kvaliteetselt rahuldada, hoides teenuse lihtsa ja fokusseerituna - ei ole võimatu, et võimalused laienevad.

### 1.2 Siseriiklik ja piiriülene autentimine

TARA võimaldab nii siseriiklikku kui ka piiriülest autentimist. See tähendab, et autentida saab nii eestlase (Eesti e-identimissüsteemi - ID-kaardi, mobiil-ID jms kasutaja) kui ka välismaalase (EL teise liikmesriigi e-identimissüsteemi kasutaja).

eIDASe kontekstis teostab TARA kasutusvood "Eestlase autentimine Eesti e-teenuses" ja "Eesti e-teenust kasutava välismaalase autentimine" (joonis 1).

<p style='text-align:center;'><img src='img/YLEVAADE.PNG' style='width:600px'></p>

Joonis 1. Siseriiklik ja piiriülene autentimine 

## 2 Autentimisprotsess kasutaja vaatest

1 Kasutaja on e-teenust osutavas klientrakenduses.

- kasutaja võib olla nii eestlane kui ka välismaalane
- kasutajale esitatakse kuva, millel on nupp "Logi sisse" vms
- kasutaja vajutab "Logi sisse".

2 Klientrakendus suunab kasutaja TARA-teenusesse (sirviku ümbersuunamiskorralduse abil)

- ümbersuunamis-URL-is on autentimispäring.

3 Kasutajale avaneb autentimismeetodi valiku kuva.  Siin võib kasutaja:

- valida mobiil-ID-ga autentimise (samm 4)
- valida ID-kaardiga autentimise (samm 5)
- valida piiriülese (eIDAS-) autentimise (samm 6)
  - sh riigi, mille eID-d ta kasutab (valib õige "lipukese")
- valida pangalingiga autentimise (samm 7)
- valida Smart-ID'ga autentimise (samm 8)
- pöörduda tagasi klientrakendusse.

4 Mobiil-ID-ga autentimine

- kasutaja sisestab mobiilinumbri ja isikukoodi
- kasutaja mobiilseadmele kuvatakse kontrollkood
- kinnituse ootamine
- eduka autentimise korral edasi samm 9, vea korral samm 10.

5 ID-kaardiga autentimine

- algab kasutajale teabe kuvamisega autentimisserdi kohta
- kasutaja kinnitab serdivaliku
- kasutaja sisestab PIN1-koodi
- eduka autentimise korral edasi samm 9, vea korral samm 10.

6 Piiriülene (eIDAS-) autentimine

- kasutaja valib sihtriigi
- kasutaja suunatakse läbi eIDAS taristu välisriigi autentimisteenusesse
- kasutaja autendib end välisriigi autentimisvahendiga
- eduka autentimise korral (ning kui välisriigi autentimisvahendi tase on piisav) edasi samm 9
- vea korral samm 10.

7 Pangalingiga autentimine

- kasutaja valib panga
- kasutaja suunatakse panga autentimisteenusesse
- kasutaja autendib end valitud autentimisvahendiga
- eduka autentimise korral edasi samm 9
- vea korral samm 10.

8 Smart-ID'ga autentimine

- kasutaja sisestab Eesti isikukoodi
- kasutaja mobiilseadmele kuvatakse kontrollkood
- kinnituse ootamine
- eduka autentimise korral edasi samm 9, vea korral samm 10.

9 Autenditud kasutaja

- suunatakse tagasi klientrakendusse
- klientrakendus pärib TARA serverilt identsustõendi
- identsustõend (_identity token_) on allkirjastatud tõend eduka autentimise kohta
- identsustõendis sisalduvad autentimisel tuvastatud kasutaja andmed (atribuudid)
- klientrakendus annab kasutajale asjakohasel viisil teada, et sisselogimine õnnestus.

10 Veateate lehelt

- saab kasutaja minna tagasi autentimismeetodi valikusse
- ja seal kas üritada uuesti, võimalik, et teise autentimismeetodiga
- või katkestada autentimise ja minna tagasi klientrakendusse.

11 Lisaks on kasutajal võimalik:

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

Joonis 3. Tagasipöördumispäring

Kasutaja valib autentimismeetodi. Valik edastatakse TARA serverikomponendile HTTP päringuga **3a**.

Järgneb autentimisdialoog, vastavalt kasutaja poolt valitud autentimismeetodile. Autentimisdialoogis võidakse sirviku ja TARA serverikomponendi vahel vahetada mitmeid sõnumeid ja teha mitmeid ümbersuunamisi. Näiteks piiriülese autentimise korral saadetakse kasutaja mitmete ümbersuunamistega välisriigi autentimisteenusesse.
Neid päringuid ja vastuseid tähistame joonisel "...".

Autentimisdialoog jõuab lõpule ja kasutaja on vaja suunata tagasi klientrakendusse.

TARA serverikomponent saadab sirvikusse HTTP päringuvastuse **3b**, milles on ümbersuunamiskorraldus kasutaja tagasisuunamiseks klientrakendusse. 

Sirvik täidab ümbersuunamiskorralduse **3b**, saates klientrakenduse serverikomponendile HTTP päringu **4a** (tagasipöördumispäringu). 

Tagasipöördumispäringus sisaldub autentimise tulemus (isik tuvastati või mitte). Tagasipöördumispäring on täpsemalt kirjeldatud eraldi jaotises allpool.

TARA roll võiks sellega lõppeda. OpenID Connect otsevoo (_implicit flow_) puhul lõpebki. Kuid TARA-s on kasutusel otsevoost mõneti turvalisemaks peetav volituskoodi voog (_authorization flow_). Volituskoodi voo korral ei saada autentimisteenus tagasipöördumispäringus autentimise tulemust täielikult, vaid ainult volituskoodi (_authorization token_).

Volituskood lunastatakse autenditud isiku isikukoodi, nime jm isikuandmete vastu eraldi päringu tegemisega TARA serverikomponendi poole (joonis 4).   

<p style='text-align:center;'><img src='img/VOOG-03.PNG' style='width:400px'></p>

Joonis 4. Identsustõendipäring

Klientrakenduse serverikomponent saadab TARA serverikomponendile identsustõendipäringu **5a**. Identsustõendipäringus esitab klientrakendus tagasipöördumispäringus saadud volituskoodi. Klientrakendus tõendab oma ehtsust, lisades päringusse salasõna (_client secret_). Identsustõendipäring on nn _backend_-päring - see ei käi läbi sirviku.

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
state=hkMVY7vjuN7xyLl5&|
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Autentimispäringu elemendid:

| URL-i element          | kohustuslik | näide                       |  selgitus     |
|------------------------|:---------- :|-----------------------------|---------------|
| protokoll, host ja tee (_path_) | jah | `https://tara.ria.ee/oidc/authorize` | `/authorize` on TARA-teenuse OpenID Connect-kohane autentimisotspunkt (termin 'autoriseerimine' pärineb alusprotokollist OAuth 2.0). |
| `redirect_uri` | jah | `redirect_uri=https%3A%2F%2F` `eteenus.asutus.ee%2Ftagasi` | Tagasipöördumis-URL. Tagasipöördumis-URL-i valib asutus ise. Tagasipöördumis-URL võib sisaldada _query_-osa. <br><br>Vajadusel kasutada [URLi kodeerimist](https://en.wikipedia.org/wiki/Percent-encoding). <br><br>URI-i [fragmendi osa](https://tools.ietf.org/html/rfc3986#section-3.5) (`#` märk ja sellele järgnev osa) kasutamine [ei ole lubatud](https://tools.ietf.org/html/rfc6749#section-3.1.2). |
| `scope` | jah | `scope=openid`<br><br>`scope=openid%20eidasonly` | Autentimise skoop.<br><br>`openid` on kohustuslik (seda nõuab OpenID Connect protokoll).<br><br>Skoobiga `eidasonly` saab nõuda, et kasutajale näidatakse ainult välisriikide autentimismeetodeid.<br><br><span class='arenduses'>(arenduses)</span> Skoobiga `email` saab nõuda, et identsustõendis väljastatakse kasutaja e-postiaadress. Vt jaotis 4.1.2 E-postiaadressi küsimine.<br><br>Piiriülesel autentimisel saab kasutada lisaskoope täiendavate isikuandmete pärimiseks (vt allpool).<br><br>Mitme skoobi kasutamisel tuleb skoobid eraldada tühikutega. Tühik esitatakse seejuures URL-kodeeringus (`%20`) ([RFC 3986](https://www.ietf.org/rfc/rfc3986.txt)). |
| `state` | jah | `state=hkMVY7vjuN7xyLl5` | Võltspäringuründe (_cross-site request forgery_, CSRF) vastane turvakood. `state` moodustamise ja kontrollimise kohta vt lähemalt jaotis "Võltspäringuründe vastane kaitse". |
| `response_type` | jah | `response_type=code` | Määrab autentimise tulemuse serverile edastamise viisi. Toetatud on volituskoodi viis (OpenID Connect protokolli _authorization flow_), selle tähiseks on väärtus `code`. |
| `client_id` | jah | `client_id=58e7...` | Rakenduse identifikaator. Rakenduse identifikaatori annab RIA asutusele klientrakenduse registreerimisel autentimisteenuse kasutajaks. |
| `locale` | ei | `locale=et` | Kasutajaliidese keele valik. Toetatakse keeli `et`, `en`, `ru`. Vaikimisi on kasutajaliides eesti keeles. Kasutaja saab keelt ise valida. <span class='arenduses'>Märkus. Parameetrit toetatakse kuni juuli 2019 lõpuni.</span> |
| `ui_locales` | ei | `ui_locales=et` | Kasutajaliidese keele valik. Toetatakse keeli `et`, `en`, `ru`. Vaikimisi on kasutajaliides eesti keeles. Kasutaja saab keelt ise valida. <span class='arenduses'>Märkus. Parameetrit toetatakse alates jaan 2019 lõpust.</span> |
| `nonce` | ei | `fsdsfwrerhtry3qeewq` | Taasesitusründeid vältida aitav unikaalne parameeter, vastavalt protokollile ([Viited](Viited), [Core], jaotis 3.1.2.1. Authentication Request). Parameeter `nonce` ei ole kohustuslik. |
| `acr_values` | ei | `acr_values=substantial` | Minimaalne nõutav autentimistase vastavalt eIDAS tasemetele. Parameeter rakendub ainult juhul kui kasutatakse piiriülest autentimist. Teiste autentimismeetodite korral parameetrit ignoreeritakse. Lubatud määrata üks väärtus järgmisest loetelust: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Kui määramata, siis vaikimisi `substantial` (märkimisväärne). |

#### 4.1.1 Atribuutide küsimine välismaalase kohta

Välismaalase autentimisel suunab TARA välismaalase tema koduriigi autentimisteenusesse. Sealt tulev vastus sisaldab suuremat või väiksemat hulka atribuute kasutaja kohta (nt perekonnanimi, aadress, sugu jne).

Euroopa Komisjoni määrusega on riigid kokku leppinud, et teise riigi autentimisteenus on alati kohustatud väljastama füüsilise isiku kohta neli atribuuti: 1) eesnimi; 2)  perekonnanimi; 3) sünniaeg; 4) isikukood vm identifikaator. Juriidilise isiku kohta väljastatakse alati kaks atribuuti: 1) registrikood vm identifikaator; 2)  juriidilise isiku nimi. Need on nn **kohustuslikud atribuudid**.

<span class='arenduses'>(arenduses)</span> Lisaks võib klientrakendus pärida täiendavaid atribuute (nn **mittekohustuslikud atribuudid**). Selleks tuleb autentimispäringut (TARA-sse suunamise URL-i) täiendada parameetri `scope` väärtust soovitud atribuutide nimedega.

Näide atribuutide küsimisest autentimispäringus:

`scope=openid%20eidas:legal_person_identifier%20eidas:legal_address%20eidas:lei`

Küsitakse atribuute `legal_person_identifier`, `legal_address` ja `lei`.

Klientrakendus ei pea kohustuslikke atribuute pärima. TARA hoolitseb, et kohustuslike atribuutide päring läheb välismaalase välisriigi autentimisteenusesse suunamisel temaga kaasa.

Loetelu võimalikest atribuutidest on identsustõendit käsitlevas jaotises.

Klientrakendus ei tohi küsida rohkem atribuute kui e-teenuse osutamiseks vaja läheb. See nõue tuleneb isikuandmete kaitse seadusest (isikuandmete töötlemise minimaalsuse põhimõte). 

Klientrakendus peab ka arvestama, et eIDAS-taristus autentimisel küsitakse kasutajalt nõusolekut isikuandmete saatmiseks teise riigi e-teenusele.

#### 4.1.2 E-postiaadressi küsimine

<span class='arenduses'>(arenduses)</span> Skoobiga `email` saab nõuda, et identsustõendis väljastatakse kasutaja e-postiaadress. See võimalus on suunatud asutustele, kelle klientrakendus nõuab kasutaja autentimisel e-postiaadressi kindlakstegemist. Skoop `email` tuleb lisada põhiskoobile `openid`. Identsustõendis väljastatakse väited (_claims_) `email` ja `email_verified`. Näiteks:

```
"sub": "EE60001019906",
"email": "60001019906@eesti.ee",
"email_verified": "false"
```

Väite `email` väärtus genereeritakse kasutaja isikukoodist, lisades sellele domeeninime `@eesti.ee`. E-postiaadress väljastatakse ainult juhul, kui kasutaja autenditakse Eesti isikukoodiga. Klientrakenduses tuleb kindlasti arvestada, et kasutaja ei tarvitse olla oma e-posti suunanud - s.t sellel aadressil saadetud kiri ei tarvitse kasutajani jõuda.

Väite `email_verified` väärtus on alati `false`. See tähendab, et TARA ei kontrolli ega väljasta teavet, kas kasutaja on oma eesti.ee e-postiaadressi suunanud või mitte. (Vastav funktsionaalsus võib lisanduda tulevikus).

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

**Veateade tagasisuunamispäringus.** Kui TARA ei suutnud autentimispäringut töödelda - päring kas oli vigane või tekkis muu viga, siis saadab TARA tagasisuunamispäringus veateate (URL-i parameeter `error`) ja veakirjelduse (URL-i parameeter `error_description`). Tagastatakse ka `state`, kuid volituskoodi (`code`) ei saadeta. Nt:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
error=invalid_scope&
error_description=Required+scope+%3Copenid%3E+not+provided.+
TARA+do+not+allow+this+request+to+be+processed&
state=qnYY56Ra8QF7IUzqvw+PPLzMKoHtQkuUWbV/wcrkvdU=
````

Klientrakenduses tuleks kontrollida, kas saadeti veateade.

**Autentimise katkestamine**. Kasutaja võib e-teenusesse tagasi pöörduda ka ilma autentimismeetodit valimata ja autentimist läbi tegemata (link "Tagasi teenusepakkuja juurde"). See võimalus on mõeldud juhuks, kui kasutaja vajutas klientrakenduses "Logi sisse", kuid tegelikult ei soovi sisse logida. Teenusega liitumise taotluses peab asutus RIA-le teada andma URL-i, kuhu kasutaja "Tagasi teenuspakkuja juurde" vajutamisel suunatakse. NB! OpenID Connect protokolli kohane tagasisuunamis-URL ja siin nimetatud URL on erineva tähendusega.

### 4.3 Identsustõendipäring

Identsustõendipäring on HTTP POST päring, millega klientrakendus pärib TARA serverilt identsustõendi (_ID token_).

Identsustõendipäringu näide (HTTP POST päringu keha on loetavuse huvides jagatud mitmele reale):

````
POST /token HTTP/1.1
Host: tara.ria.ee/oidc/token
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

TARA server kontrollib, et identsustõendit küsib õige rakendus, seejärel väljastab päringu vastuses (_HTTP response body_) identsustõendi.

Päringu vastus on JSON-struktuur, milles on neli elementi (vt järgnev tabel). 

| element | selgitus |
|:-------:|----------|
| `access_token` | OAuth 2.0 pääsutõend. Pääsutõendit TARA-s ei kasutata (kuna kõik autenditud isikut kirjeldavad andmed väljastatakse identsustõendis; `userinfo` otspunkti TARA ei toeta) |
| `token_type` | Väärtusega `bearer`. OAuth 2.0 pääsutõendi tüüp. TARA-s ei kasutata |
| `expires_in` | OAuth 2.0 pääsutõendi aegumiskestus. TARA-s ei kasutata |
| `id_token` | identsustõend, Base64 vormingus | 

TARA-s kasutatakse neist neljast elemendist viimast - identsustõendit. Identsustõend on TARA poolt väljastatav tõend autentimise fakti kohta.

Näide:

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

Identsustõendis esitatakse järgmised väited (_claims_).

| identsustõendi element (väide) | näiteväärtus, selgitus     |
|:-----------------------|---------------------|
| `jti` (_JSON Token Identifier_) | `0c597356... ` - identsustõendi identifikaator |
| `iss` (_Issuer_)       | `https://tara.ria.ee` - tõendi väljastaja (TARA-teenus); testteenuse puhul `https://tara-test.ria.ee` |
| `aud` (_Audience_)     | `TARA-Demo` - autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `exp` (_Expires_) | `1530295852` - tõendi aegumisaeg, Unix _epoch_ vormingus |
| `iat` (_Issued At_) | `1530267052` - tõendi väljaandmisaeg, Unix _epoch_ vormingus |
| `nbf` (_Not Before_)   | `1530266752` - tõendi kehtivuse algusaeg, Unix _epoch_ vormingus |
| `sub` (_Subject_)      | `EE60001019906` - autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator) koos kodaniku riigikoodi eesliitega (riigikoodid vastavalt ISO 3166-1 alpha-2 standardile) |
| `profile_attributes`   | autenditud isikut kirjeldavad andmed, sh eIDAS atribuudid (vt ka allpool täiendavate andmete küsimise ja isiku esindamise kohta) |
| `profile_attributes`<br>`.date_of_birth` | `2000-01-01` - autenditud kasutaja sünnikuupäev ISO_8601 formaadis. Tagastatakse ainult eIDAS autentimisel |
| `profile_attributes`<br>`.given_name` | `MARY ÄNN` - autenditud kasutaja eesnimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `profile_attributes`<br>`.family_name` | `O’CONNEŽ-ŠUSLIK` - autenditud kasutaja perekonnanimi (testnimi, valitud täpitähtede jm eritärkide sisalduvuse pärast) |
| `profile_attributes`<br>`_translit` | Sisaldab JSON objekti ladina tähestikus profiiliatribuutidest (vt allpool translitereerimine.). Väärtustatud ainult eIDAS autentimisel |
| `amr` (_Authentication Method Reference_) | `mID` - kasutaja autentimiseks kasutatud autentimismeetod. Võimalikud väärtused: `mID` - mobiil-ID, `idcard` - Eesti ID-kaart, `eIDAS` - piiriülene, `banklink` - pangalink, `smartid` - Smart-ID  |
| `state` | `abcdefghijklmnop` - turvaelement  |
| `nonce` | `qrstuvwxyzabcdef` - turvaelement |
| `acr` (_Authentication Context Class Reference_) | `high` - autentimistase, vastavalt eIDAS tasemetele. Võimalikud väärtused: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Elementi ei kasutata, kui autentimistase ei kohaldu või pole teada |
| `at_hash` | `X0MVjwrmMQs/IBzfU2osvw==` - pääsutõendi räsi. TARA-s ei kasutata |
| `email` | `60001019906@eesti.ee` - <span class='arenduses'>(arenduses)</span> kasutaja e-postiaadress. Genereeritakse isikukoodist, lisades sellele domeeninime `eesti.ee`. Väljastatakse ainult  Eesti isikukoodiga kasutaja autentimisel. |
| `email_verified` | `false` - <span class='arenduses'>(arenduses)</span> tähendab, et e-postiaadressi kuulumine kasutajale on tuvastatud. TARA väljastab alati väärtuse `false`. See tähendab, et TARA ei kontrolli ega väljasta teavet, kas kasutaja on oma eesti.ee e-postiaadressi suunanud või mitte. |

#### 4.3.2 Mittekohustuslikud atribuudid (välismaalase autentimisel)

<span style=arenduses>Arenduses</span> Järgnevad atribuudid esitatakse identsustõendis ainult siis, kui  autenditud isik on välismaalane ja klientrakendus on atribuute  autentimispäringu `scope` parameetris taotlenud.

Füüsiline isik

| identsustõendi element (`profile_attributes.` + ) | scope väärtus | väljastamine kohustuslik |  eIDAS atribuut |
|-----------------|-----------------|:------------------------:|----------------|
| `birth_name` | `eidas:birth_name`  | ei | `BirthName` |
| `place_of_birth` | `eidas:place_of_birth` | ei | `PlaceOfBirth` |
| `current_address` | `eidas:current_address` | ei | `CurrentAddress` |
| `gender` | `eidas:gender` | ei | `Gender` |

Juriidiline isik

| identsustõendi element (`profile_attributes.` + ) | scope väärtus | väljastamine kohustuslik |  eIDAS atribuut |
|-----------------|-----------------|:------------------------:|----------------|
| `legal_person_identifier` | `eidas:legal_person_identifier` | jah | `LegalPersonIdentifier` |
| `legal_name` | `eidas:legal_name` | jah         | `LegalName` |

| identsustõendi element (`profile_attributes.` + ) | scope väärtus | väljastamine kohustuslik | eIDAS atribuut |
|-----------------|-----------------|:------------------------:|----------------|
| `legal_address` | `eidas:legal_address` | ei | `LegalAddress` |
| `vat_registration` | `eidas:vat_registration` | ei | `VATRegistration` |
| `tax_reference` | `eidas:tax_reference`  | ei |  `TaxReference` |
| `LEI` | `eidas:lei`  | ei |  `LEI` |
| `EORI` | `eidas:eori`  | ei |  `EORI` |
| `SEED` | `eidas:seed`  | ei |  `SEED` |
| `SIC` | `eidas:sic` | ei |  `SIC` |
| `D-2012-17-EUIdentifier` | `eidas:d-2012-17-eu_identifier`  | ei | `D-2012-17-EUIdentifier ` |

Küsida ei saa, kuid võidakse väljastada:

Füüsilise isiku esindaja atribuudid

väli identsustõendis | eIDAS atribuut
---------------------|----------------
`representative_birth_name` | `RepresentativeBirthName`
`representative_current_address` | `RepresentativeCurrentAddress`
`representative_family_name` | `RepresentativeFamilyName`
`representative_first_name` | `RepresentativeFirstName`
`representative_date_of_birth` | `RepresentativeDateOfBirth`
`representative_gender` | `RepresentativeGender`
`representative_person_identifier` | `RepresentativePersonIdentifier`
`representative_place_of_birth` | `RepresentativePlaceOfBirth`

Juriidilise isiku esindaja atribuudid

väli identsustõendis<br>(`profile_attributes` all) | eIDAS atribuut
---------------------|----------------
`representative_d-2012-17-eu_identifier` | `RepresentativeD-2012-17-EUIdentifier`
`representative_eori` | `RepresentativeEORI`
`representative_lei` | `RepresentativeLEI`
`representative_legal_address` | `RepresentativeLegalAddress`
`representative_legal_name` | `RepresentativeLegalName`
`representative_legal_address` | `RepresentativeLegalAddress`
`representative_legal_person_identifier` | `RepresentativeLegalPersonIdentifier`
`representative_seed` | `RepresentativeSEED`
`representative_sic` | `RepresentativeSIC`
`representative_tax_reference` | `RepresentativeTaxReference`
`representative_vat_registration` | `RepresentativeVATRegistration`

eIDAS atribuudi nimele vastava täpsema kirjelduse leiab eIDAS  atribuutide profiilist [Viited](Viited), [eIDAS SAML Attribute Profile v1.1-2].

#### 4.3.3 Translitereerimine

Kõik eelpool toodud eIDAS spetsiifilised identsustõendi väärtused peavad olema esitatud originaalkujul, kuid sellele lisaks võivad sihtriigid soovi korral esitada väärtusi ka translitereeritud kujul. Juhul kui välisriik otsustab saata ka ladina tähestikku teisendatud kuju, esitatakse antud atribuudi nime ja väärtuse paarid ka `profile_attributes_translit` blokis.

Näide identsustõendis profiilielementide translitereerimisest (isiku eesnimi ja perenimi on esitatud kreeka ja ladina tähestikus):
````json
{
   ...
   
   "profile_attributes":{
      "given_name":"Αλέξανδρος",
      "family_name":"Ωνάσης",      
      "date_of_birth":"1981-01-12"
   },
   "profile_attributes_translit":{
      "given_name":"Alexander",
      "family_name":"Onassis"
   }
   
   ...
}
````

Identsustõend võib sisaldada muid OpenID Connect protokolli kohaseid välju, kuid neid teenuses ei kasutata. 

Kui identsustõendit ei pärita `5` minuti jooksul, siis identsustõend aegub ja autentimisprotsessi tuleb korrata.

## 5 Turvatoimingud

### 5.1 Identsustõendi kontrollimine

Klientrakendus peab identsustõendit kontrollima.
{: .adv}

1\. Allkirja kontrollimine. Identsustõend on autentimisteenuse TARA poolt allkirjastatud. Allkiri JWT standardile. Allkirjaalgoritmina kasutab TARA `RS256`. Klientrakendus peab suutma vähemalt selle algoritmiga antud allkirja kontrollida. (Märkus. Teostada on otstarbekas standardse JWT teegiga, mis toetaks kõiki JWT algoritme. TARA allkirjaalgoritmi vajadus on põhimõtteliselt võimalik - kui `RS256`-s peaks avastatama turvanõrkus)

2\. Tõendi väljaandja kontrollimine. Identsustõendi elemendi `iss` väärtus peab olema `https://tara-test.ria.ee` (TARA testkeskkonna puhul) või `https://tara.ria.ee` (TARA toodangukeskkonna puhul).

3\. Tõendi adressaadi kontrollimine. Klientrakendus peab kontrollima, et saadud tõend on välja antud just temale. Selleks veenduda, et identsustõendi elemendi `aud` väärtus ühtib klientrakendusele registreerimisel väljaantud kliendinimega (_Client ID_).

4\. Tõendi ajalise kehtivuse kontrollimine. Kontrollitakse kolme identsustõendis sisalduva elemendi abil - `iat`, `nbf`, `exp`. Klientrakendus kasutab kontrollimisel oma kellaaega. Kontrollida tuleks, et: 

1) "not before" ajamoment on kätte jõudnud:

`nbf < jooksev_aeg - kellade_lubatud_erinevus` 

2) "expired" ajamoment ei ole kätte jõudnud:

`exp > jooksev_aeg + kellade_lubatud_erinevus`.

`kellade_lubatud_erinevus` väärtus valida ise. Need kontrollid on vajaliku rünnete ja sassiminekute vältimiseks.

TARA põhimõte on, et identsustõendile tuleb järgi tulla kohe, 5 minuti jooksul. Selle aja ületamisel identsustõendit ei väljastatagi.

Idensustõendi eduka kontrollimise järel loob klientrakendus kasutajaga seansi ("logib kasutaja sisse"). Seansi loomine ja pidamine on klientrakenduse kohustus. Kuidas seda teha, ei ole enam autentimisteenuse TARA skoobis.

### 5.2 Võltspäringuründe vastane kaitse

Klientrakenduses tuleb rakendada võltspäringuründe (_cross-site request forgery_, CSRF) vastaseid kaitsemeetmeid. Seda tehakse turvakoodide `state` ja `nonce` abil. `state` kasutamine on kohustuslik; `nonce` kasutamine on vabatahtlik. Kirjeldame `state` kasutamise protseduuri.

Turvakoodi `state` kasutatakse autentimispäringule järgneva tagasipöördumispäringu võltsimise vastu. Klientrakenduses tuleb teostada järgmised sammud:

1 Genereerida juhusõne, näiteks pikkusega 16 tärki: `XoD2LIie4KZRgmyc` (tähistame `R`).

2 Arvutada juhusõnest `R` räsi `H = hash(R)`, näiteks SHA256 räsialgoritmiga ja teisendades tulemuse Base64 vormingusse: `vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`.

3 Lisada autentimispäringule küpsise panemise korraldus, näiteks:

`Set-Cookie ETEENUS=XoD2LIie4KZRgmyc; HttpOnly`,

kus `ETEENUS` on vabalt valitud küpsisenimi. Küpsisele tuleb rakendada atribuuti `HttpOnly`.

4 Seada p 2 arvutatud räsi parameetri `state` väärtuseks:

`GET ... state=vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`

Niisiis, autentimispäringuga saadetakse kaks asja: juhusõne küpsisesse panemiseks ja juhusõnest arvutatud räsiväärtus `state` parameetris. Klientrakendus ei pea juhusõne ega räsiväärtust meeles pidama.

Tagasipöördumispäringu töötlemisel peab klientrakendus tegema järgmist:

5 Võtab päringuga tuleva küpsise `ETEENUS` väärtuse

6 Arvutab küpsise väärtusest räsi

7 Kontrollib, et räsi ühtib tagasipöördumispäringus tagasipeegeldatava `state` väärtusega.

Tagasipöördumispäringut tohib aktsepteerida ainult ülalkirjeldatud kontrolli õnnestumisel.

Kirjeldatud protseduuris on võtmetähtsusega väärtuse `state` sidumine sessiooniga. Seda tehakse küpsise abil. (See on autentimise ajutine sessioon.  Töösessiooni moodustab klientrakendus pärast autentimise edukat lõpuleviimist).

Täiendav teave: OpenID Connect protokollis kahjuks ei ole teema selgelt esitatud. Mõningast teavet saab soovi korral mitteametlikust dokumendist [OpenID Connect Basic Client Implementer's Guide 1.0](https://openid.net/specs/openid-connect-basic-1_0.html), jaotis "2.1.1.1 Request Parameters".

Soovi korral võite veel tutvuda ründe (ja kaitse) detailse seletusega: [Võltspäringurünne ja kaitse selle vastu](Volts).  

### 5.3 Logimine

Logimine peab võimaldama rekonstrueerida TARA ja klientrakenduse suhtluse käigu TARA iga kasutuse jaoks. Selleks tuleb nii TARA kui ka klientrakenduse poolel logida kõik päringud ja päringute vastused: [autentimispäring](#41-autentimisp%C3%A4ring), [tagasisuunamispäring](#42-tagasisuunamisp%C3%A4ring) ja [identsustõendipäring](#43-identsust%C3%B5endip%C3%A4ring). Kuna edastatavad andmemahud ei ole suured, siis tuleb logida nii URL kui ka identsustõend täielikul kujul. Logide säilitamistähtaja määramisel arvestada klientrakenduse olulisust. Orientiiriks pakume 1..7 aastat. Probleemide lahendamiseks pöördumisel palume esitada väljavõte logist (mis päringud TARA poole saadeti? mis saadi vastuseks?).

## 6 Otspunktid

Testteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) |  [https://tara-test.ria.ee/oidc/.well-known/openid-configuration](https://tara-test.ria.ee/oidc/.well-known/openid-configuration) |
| teenuse avalik allkirjastamisvõti | [https://tara-test.ria.ee/oidc/jwks](https://tara-test.ria.ee/oidc/jwks) |
| klientrakenduse registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | [https://tara-test.ria.ee/oidc/authorize](https://tara-test.ria.ee/oidc/authorize) | 
| tõendiväljastus (_token_) | [https://tara-test.ria.ee/oidc/token](https://tara-test.ria.ee/oidc/token) | 

Toodanguteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | [https://tara.ria.ee/oidc/.well-known/openid-configuration](https://tara.ria.ee/oidc/.well-known/openid-configuration) |
| teenuse avalik allkirjastamisvõti | [https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks) |
| klientrakenduse registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | [https://tara.ria.ee/oidc/authorize](https://tara.ria.ee/oidc/authorize) | 
| tõendiväljastus (_token_) | [https://tara.ria.ee/oidc/token](https://tara.ria.ee/oidc/token) | 

## 7 Soovitusi liidestajale

TARA-ga liidestamine on lihtne. Siiski on vaja töid kavandada ja hoolikalt teostada.

Liidestuja peab erilist tähelepanu pöörama, et kõik protokollikohased kontrollid saaksid tehtud - turvaelemendi `state` ja kui kasutatakse, siis ka `nonce` kontroll, identsustõendi kontroll jm. Vt [ID token validation](http://openid.net/specs/openid-connect-core-1_0.html#ImplicitIDTValidation) [Core].

Liidestamise protsess näeb välja järgmine.

Asutus peaks välja selgitama, kas ja millistes oma e-teenustes soovib TARA kasutada. Selleks tuleks tutvuda TARA [ärikirjeldusega](Arikirjeldus), teenustaseme leppega (SLA-ga), käesoleva [tehnilise kirjeldusega](TehnilineKirjeldus). Võib heita pilgu teenuse [teekaardile](Teekaart). Vajadusel pidada nõu RIA-ga, `help@ria.ee`.

Seejärel kavandada ja teostada teenuse kasutamiseks vajalik arendustöö - klientrakenduse täiendamine OpenID Connect protokolli kohase klientkomponendiga, sh testimine. Hinnanguline töömaht: kogenud arendajal u 2 päeva; kui OpenID Connect-i pole varem teinud, siis 2 nädalat. Aluseks käesolev [tehniline kirjeldus](TehnilineKirjeldus)

Arenduse valmides tuleb liidest testida. Selleks kasutatakse TARA testteenust. Asutus esitab taotluse testteenusega liitumiseks. Taotluse võib esitada juba enne arenduse algust. Taotluses teatab asutus:

- teenuse, millega soovitakse liituda (test- või toodanguteenus)
- kinnituse, et liituja on välja arendanud omapoolse liidese ja seda TARA testteenuse vastu testinud (toodanguteenusega liitumise puhul)
- e-teenuse või -teenused, mille kasutajaid soovitakse TARA abil autentida
- kasutajate arvu prognoosi
- kohustumuse kasutada teenust eesmärgipäraselt. Sh testteenust kasutada ainult testimiseks, mitte toodangus autentimiseks
- nõustumuse teenustasemega (SLA-ga)
- klientrakenduse identifikaatori ettepanek -`client_id`, OpenID Connect protokolli kohaselt
- klientrakenduse testversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt
- klientrakenduse testversiooni tagasisuunamis-URL juhuks, kui kasutaja soovib autentimist katkestada
- autentimismeetod või meetodid, mida soovitakse kasutada
- klientrakenduse haldaja kontaktandmed (e-post, telefon, isikukood).

Taotlus esitatakse ja edasine suhtlus teenuse haldamisel käib läbi RIA kasutajatoe, `help@ria.ee`. Vt lähemalt [RIA autentimisteenuste lehel](https://www.ria.ee/et/riigi-infosusteem/eid/partnerile.html#tara).

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse salasõna `client_secret`. Salasõna on ette nähtud identsustõendi päringute allkirjastamiseks
- avab asutuse klientrakenduse testversioonile juurdepääsu testteenusele.

Järgneb liidestuse testimine. RIA abistab siin võimalike probleemide lahendamisel. Testimise kohta vt lähemalt: [Testimine](https://e-gov.github.io/TARA-Doku/Testimine).

Liitumine TARA toodanguteenusega. Eduka testimise järel asutus esitab taotluse toodanguteenuse avamiseks klientrakendusele. Taotluses näidatakse klientrakenduse toodanguversiooni tagasisuunamis-URL (`redirect_uri`), OpenID Connect protokolli kohaselt jm andmed

RIA, rahuldades taotluse, väljastab asutusele klientrakenduse toodanguversiooni salasõna `client_secret` ja avab asutuse klientrakenduse toodanguversioonile juurdepääsu toodanguteenusele.

## Muutelugu

| Versioon, kuupäev | Muudatus |
|-----------------|--------------|
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

