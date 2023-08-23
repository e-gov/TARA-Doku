---
permalink: Volts
---

## Võltspäringurünne ja kaitse selle vastu

Näitame autentimisteenuse [TARA](https://github.com/e-gov/TARA-Doku) näitel OpenID Connect (aga samuti OAuth 2.0) protokollis võimalikku võltspäringurünnet (_cross-site request forgery_, CSRF) ja kaitset selle vastu. 

Joonisel on esitatud: 1) autentimisteenus TARA `tara.ria.ee`; 2) autententimisteenust TARA kasutav e-teenus (serveri osa); 3) ründaja sirvik (punane); 4) ohvri sirvik; 5) ründaja (punane); 6) ohver.

<p style='text-align:center;'><img src='img/Volts.PNG' style='width:400px'></p>

TL;DR Võltspäringutõke tuleb siduda sessiooniga, muidu pole sellest kasu.

***Normaalprotsess***

Näitame kõigepealt, kuidas ründaja saab normaalse autentimisprotsessi läbi teha. (Hiljem, ründamisel, ta katkestab protsessi ja "söödab" oma vahetulemused ette ohvrile). 

**1a** Kasutaja vajutab nupule "Logi sisse" vms. Selle toimel saadab sirvija serverisse päringu

`HTTP GET https://asutus.ee/Login`.

**1b** Server saadab päringule vastuseks ümbersuunamiskorralduse (_redirect_) autentimisteenusesse. HTTP vastuse staatusekood on 302. Vastuses on OpenID Connect protokolli kohased elemendid, nende seas võltspäringutõke `state`.

```
302 Found
Location: https://tara.ria.ee/authorize?
redirect_uri=https%3A%2F%2Fasutus.ee%2F
Callback&scope=openid&
state=hkMVY7vjuN7xyLl5&response_type=code&
client_id=asutus
```

**2a** Kasutaja sirvik teeb päringu ümbersuunamis-URL-le:

```
HTTP GET https://tara.ria.ee/authorize?
redirect_uri=https%3A%2F%2Fasutus.ee%2F
Callback&scope=openid&
state=hkMVY7vjuN7xyLl5&response_type=code&
client_id=asutus
```

**2b** Kasutaja autentimise järel saadab autentimisteenus sirvikust tulnud päringule vastuse. Vastuses sisaldub ümbersuunamiskorraldus, volituskood `code` ja võltspäringutõke `state`. 

```
302 Found
Location: https://asutus.ee/Callback?
code=71ed5797c3d957817d31&
state=hkMVY7vjuN7xyLl5
```

**3a** Sirvija täidab ümbersuunamiskorralduse, tehes päringu:

```
HTTP GET https://asutus.ee/Callback?
code=71ed5797c3d957817d31&
state=hkMVY7vjuN7xyLl5
```

**3b** Server teeb nüüd päringu autentimisteenuse otspunkti `/Token` ja saab volituskoodi vastu identsustõendi (_ID token_). Identsustõendis on autenditud kasutaja isikukood, nimi jm autentimisandmed. Server saadab sirvikusse vastuse, milles on seansiidentifikaatori või fikseerib autentimise tulemuse muul viisil. 

Sellega on autentimisprotsess lõppenud.

***Rünne***

Ründaja laeb klientrakenduse oma sirvikusse. Vajutab "Logi sisse" ja teeb autentimisteenuses läbi autentimise. Püüab kinni autentimisteenuse vastuse 2b ja tõkestab selles sisalduva ümbersuunamiskorralduse täitmist. See tähendab, et päring 3a sirvikust välja ei lähegi.

Selle asemel moodustab võltspärigu ja pettuse abil paneb ohvri sirviku võltspäringut serverile saatma. Näiteks saates ohvrile näiliselt süütu veebilehe aadressi; veebilehel on aga võltspäringut käivitav peidetud element

```
<img src="https://asutus.ee/Callback?
code=71ed5797c3d957817d31&state=hkMVY7vjuN7xyLl5">
```

Kui selline päring jõuab serverisse ja server ei suuda aru saada, et 1a ja 3a tulevad erinevatest sirvikutest (aga kust ta peaks aru saama?), siis ta pahaaimamatult täidab päringu. Tulemusena logitakse ründaja ohvri sirvikus klientrakendusse sisse.

***Kaitse***

Võltspäringuründe vastu pakub kaitset tõkestav element `state`. Kuid `state` peab olema sirvijaga seotud. Korrektne kaitseprotseduur on järgmine:

Vastuse 1b saatmisel paneb server kaasa küpsise, milles sisaldub seansiidentifikaator (_session ID_):

```
Set-Cookie: SessionID=2h5ft6; HttpOnly; Secure
``` 

Seansiidentifikaator (näites `2h5ft6`) on juhusõne. Tõkestava elemendi `state` moodustab sirvik, arvutades seansiidentifikaatori räsi: `state = hash(2h5ft6)`.

Nii seotakse `state` konkreetse sirvikuga. Kuid seansiidentifikaator ei ole räsi põhjal arvutatav.

Sirvik talletab saadud küpsise. Autentimisteenusest tagasipöördumisel paneb sirvik küpsise päringusse 3a kaasa.

Server saab päringust 3a kätte nii tõkestava elemendi `state` kui ka küpsises hoitud seansiidentifikaatori. Server PEAB kontrollima, et saadud `state = hash(2h5ft6)`.

Kui selline kaitse on rakendatud, siis ründaja võib oma sirvikus küll seansiküpsise kätte saada, kuid tal ei ole lihtsat võimalust küpsise paigaldamiseks ohvri arvutisse. Kuna ohvri arvutis vajalikku küpsist ei ole (või on seal teine küpsis, teise seansiidentifikaatoriga), siis võltspäringu saatmine ei õnnestu - eeldusel, et klientrakenduses on `state` kontroll õigesti teostatud.
