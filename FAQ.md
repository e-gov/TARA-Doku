---
permalink: FAQ
---

# FAQ
{: .no_toc}

- TOC
{:toc}

## Kas on kavas pakkuda ka `userinfo` otspunkti?

Userinfo otspunkt on mõeldud autenditud kasutaja kohta andmete väljastamiseks pääsutõendi (_access token_) alusel. TARAs väljastame autenditud kasutajate kohta minimaalse andmekomplekti (isikukood, ees- ja perekonnanimi). Selle väljastame kohe identsustõendis, sest nii on kõige lihtsam. Mööname, et "karbitarkvaradega" liidestujatele pakub huvi userinfo toetus. Hakkame seda pakkuma TARA uues versioonis, kuid tähtaega ei saa veel öelda, sest projekt on ettevalmistamise järgus.

## Kas autentimist saab teisele rakendusele edasi anda?

Vt [siit](Feder).

## Teatud juhtudel saab võõra ID-kaardiga sisse logida. See ei ole soovitav käitumine.

ID-kaardiga autentimisel küsitakse kasutajalt alati PIN1 koodi.

Kasutajamugavuse suurendamiseks sirvikud puhverdavad PIN1 koodi. See tähendab, et kasutajalt küsitakse PIN1-te üks korda, edaspidi töö käigus aga enam ei küsita (kasutajale mugav). Oht on aga selles, et kui kasutaja lahkub ilma sirvikut sulgemata ja arvutile pääseb ligi teine kasutaja, siis uus kasutaja võib ilma PIN1-te teadmata- puhverdamise tõttu seda temalt ei küsita - eelmise kasutaja nime all e-teenusesse sisse.

See on tõsine oht. PIN1 küsimine toimub kasutaja arvutis. TARA poolelt ei saa sirviku käitumist täielikult juhtida. Ohu vältimiseks peab kasutaja turvaliselt tegutsema:

"Veebilehitsejad võivad puhverdada (ajutiselt salvestada) aktiivse ID-kaardi sessiooni käigus sisestatud PIN1 ehk isikutuvastamise koodi. Selle tulemusel võib õnnestuda erinevatesse e-teenustesse sisselogimine ilma PIN1-koodi korduvalt sisestamata. PIN1-koodi „puhverdamist“ saab vältida, kui järgida järgmist kolme põhimõtet:

-	kui kasutasid ID-kaarti internetis mõnes e-teenuses, siis teenuse kasutamise lõppemisel vajuta kindlasti „Välju“, „Logi  välja“ või „Sulge“ nupule;
-	pärast ID-kaardi elektroonilise kasutamise lõppu võta ID-kaart alati kaardilugejast välja;
-	sulge kindlasti kõik veebilehitseja (Internet Explorer, Mozilla Firefox, Chrome, Safari) aknad kui eelnev on tehtud."

Allikas: www.id.ee, "Olulised turvanõuded ID-kaardi kasutamiseks".

Täiendava meetmena on hea võõrast inimest oma isiklikku arvutisse üldse mitte lubada.

Samuti pakuvad sirvikud privaatsirvimise võimalust (private browsing). ID-kaardi kasutamisel sirviku privaatsirvimisaknas on suurem kindlus, et puhvrid ja kasutusajalugu tühjendatakse.

## Millised on nõuded `clientid`-le?

`clientid` on autentimisteenust TARA kasutava rakenduse avalik identifikaator. Internetis on küll [soovitusi](https://www.oauth.com/oauth2-servers/client-registration/client-id-secret/) valida `clientid` juhuslikult - siis on seda raskem ära arvata ja ründeid konstrueerida. Avalikus e-teenuses ei ole `clientid` peitmine siiski võimalik. Seetõttu soovitame `clientid` valida sisukana, s.t `clientid` peaks andma aimu rakendusest ja asutusest, samuti kas kasutatakse test- või toodangukeskkonda. Teenuse kasutajale on `clientid` nähtamatu. Kuna `clientid` edastatakse autentimispäringus, URL-i koosseisus, siis on lihtsam piirduda ladina tähtedega. Täpitähed on lubatud, kuid arvestada, et autentimispäringu URL-is edastatakse need [URL encoded](https://www.url-encode-decode.com/) kujul. Miinus, punkt ja allkriips on lubatud. Kaldkriipsu korral arvestada, et URL encoded kujul on see `%2F`. `clientid` on tõstutundlik.

## Autentimisdialoog avaneb, teen autentimise läbi, aga siis tuleb veateade `Teenusele ligipääs suletud`.

Kontrolli, et klientrakendus kasutab TARA poole pöördumisel õiget `clientid`-d ja tagasuunamis-URL-i (`redirecturi`). `clientid` tuleb anda autentimispäringus. Tagasisuunamis-URL tuleb anda nii autentimis- kui ka identsustõendi küsimise päringus. Need väärtused peavad täpselt vastama RIA-s registreeritutele. Kas oled neid väärtusi muutnud? Kas sul on mitu klientrakendust? Võib-olla nende väärtused on segamini läinud? Ülekontrollimiseks, milline `clientid` ja `redirecturi` on registreeritud, võib pöörduda RIA teenusehalduri poole.

## OAuth2 teek üritab `jwks` otspunktist võtme võtmeidentifikaatori abil kätte saada, kuid see ei õnnestu.

Teenuse avaliku allkirjastamisvõtme otspunkt ([https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks)) pakub praegu ühtainust võtit. See tuleb võtta ja kasutada. TARA edasiarendamisel (2018 lõpp - 2019 algus) lisame dünaamilise võtmevahetuse (key rollover). Siis saab võtit pärida võtmeidentifikaatoriga.

## TARA ütleb, et `Required+scope+&lt;openid&gt;+not+provided`.

Põhjus - nagu ka teiste päringuparameetrite probleemide puhul - võib olla URL-kodeerimises. Skoobi `eidasonly` kasutamisel tuleb see saata koos skoobiga `openid`. Skoobid tuleb eraldada tühikuga (URL-encoded kujul: `openid%20eidasonly`). Saatmisel jälgida, et tühik URL-kodeeritakse, aga mitte rohkem, kui üks kord. 

## Mul ei ole tagasipöördumis-URL-is nõutud domeeni? Kas on lihtsam viis TARA testimiseks?

Testkeskkonnas võib tagasipöördumis-URL-ks valida ka `localhost`-i sisaldava URL-i. Sellisel juhul suunab TARA kasutaja pärast autentimist tagasi kasutaja masinas töötavale rakendusele. Toodangus `localhost`-i kasutada ei tohi.

## Kas kõik spetsifikatsioonis nõutu tuleb teostada?

Jah, tegu on turvaprotokolliga, millest mittevajalik on juba eemaldatud. Kõik nõuded tuleb täita, sh testimisse puutuv. Mittekohustuslikud asjad, nt `nonce` kasutamine on, on selgelt markeeritud.

## Mida tähendab sõna "TARA"?

Vt: Joh. V. Veski, [Sõna "tara" tähenduse asjus](https://dea.digar.ee/cgi-bin/dea?a=d&d=uuseesti19361028.2.57), "Uus Eesti", 1936 (Rahvusarhiivi Digar-kogu)

<p>&nbsp;</p>

Kui küsimustest ei saanud abi, siis pöördu kasutajatoe poole: help@ria.ee.
{: .adv}
