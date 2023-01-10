---
permalink: Abi
---

Küsimuste korral, täiendava teabe või abi saamiseks palume pöörduda kasutajatoe poole `help@ria.ee`.

Kui pöördute liidestamisel või TARA kasutamisel tekkinud tehnilise probleemiga, siis palume valmis panna väljavõte klientrakenduse logist. Tõrkepõhjuse väljaselgitamiseks vajame teavet, millised päring(ud) TARAsse saadeti ja mida vastuseks saadi.

Ärge saatke kasutajatoe poole pöördumises teile väljastatud klientrakenduse salasõna!
{: .adv}

Enne tehnilise küsimusega pöördumist tasub heita pilk korduma kippuvate küsimuste rubriiki.

Samuti oleme tänulikud teenuse täiendusettepaneku eest.


# Korduma kippuvad küsimused
{: .no_toc}

Hea TARA-ga liidestuja! Siit leiad valiku küsimusi, mis teistel on tekkinud - ja vastused.

- TOC
{:toc}

## Miks ei ole soovitatav TARA otse liidestada _native_ mobiilirakendusega (ehk nn _native_ mobiiliäpiga)?

TARA ei toeta avalikke klientsüsteeme ning teenus on mõeldud eelkõige vaid e-teenustele kus on võimalik liitumisel väljastatud saladust [turvaliselt hoiustada](https://tools.ietf.org/html/rfc6819#section-5.3.3).

Mobiilirakendust võib aga lugeda avalikuks kliendiks kuna staatilise saladuse hoidmine mobiilirakendustes on raskendatud. Olenemata platvormist on saladust võimalik rakendusest erinevate tehnikatega välja lugeda või pöördprojekteerida. 
 
## Millist kaitset pakub TARA IT-kuritegude vastu?

_PIN1 äraarvamisrünne._ Ründaja võib üritada ära arvata PIN1-te. Kaitse äraarvamisründe vastu on sisse ehitatud ID-kaarti ja mobiil-ID-sse. Mõlemas autentimismeetodis peab kasutaja sisestama PIN-i. Kolme valestisisestamise korral kiibil olev sert lukustub. Märgime, et ründaja peab enne olema saanud oma valdusse ohvri ID-kaardi või nutiseadme. TARA siin täiendavat kaitsekihti ei paku.

_mobiil-ID kelmusrünne._ Ründaja võib alustada mobiil-ID autentimist, sisestades ohvri mobiilinumbri ja isikukoodi. Kui ründaja ei tea ohvri mobiilinumbrit või isikukoodi, siis ta võib üritada neid ära arvata proovimise teel. Ohvri mobiiltelefonil avaneb PIN1 küsimise dialoog. Kui ohver nüüd ei saa aru, mis toimub, tähelepanematusest või arusaamatusest sisestab PIN1, on ründaja ohvrina klientrakenduses autenditud. Kaitsemeetmeks on eelkõige inimese enda tähelepanelikkus: kui nutiseadmele ilmub PIN1 küsimise dialoog, siis peab alati kontrollima nii PIN1 küsijat (TARA puhul "RIA") kui ka kontrollkoodi.

_Tülitamisrünne._ Ründaja võib alustada mobiil-ID autentimist, sisestades ohvri mobiilinumbri ja isikukoodi - või seda ära arvates - ka lihtsalt ohvri tülitamise eesmärgil. Teoreetilise kaitsemeetmena võib mõelda arvepidamist ühelt IP-lt võetud autentimisürituste kohta, kas TARA või klientrakenduse tasandil, ja teenuse piiramist ürituste piirarvu saavutamisel. See oleks tehniliselt keerukas ja võiks hakata takistama legaalseid autentimisi. Seepärast seda tehtud ei ole.  

_Teenusetakistusrünne._ Ründaja võib, võimalik, et üritusi automeerides, algatada suurel arvul autentimisi, eesmärgiga klientrakenduse ja/või TARA ülekoormamise teel takistada teenuse osutamist. Osaliseks kaitsemeetmeks oleks robotiluku (_Captcha_) kasutamine. Poolt ja vastu argumente kaaludes TARAs robotilukku siiski praegu ei kasutata.

Need on ainult mõned võimalikest rünnetest. Kaitsemeetmeid on TARA-sse sisse ehitatud nii protokolli (vt [Tehniline kirjeldus](TehnilineKirjeldus)) kui ka selle teostuse tasandil. TARA käitamisel rakendatakse mitmesuguseid tehnilisi ja organisatsioonilisi kaitsemeetmeid isikuandmete kaitseks (vt [Andmekaitsetingimused](IsikuandmeTP)) TARA tarkvara on läbinud turvatestimise. Turvaolukorda jälgitakse pidevalt. Kokkuvõttes võib öelda, et TARA on turvaline.

## Kui saate /oidc/token otspunktile tehtud päringu (identsustõendi küsimine) vastuseks veateate `401 unauthorized`.

Kontrollige:
- HTTP autoriseerimispäis (_authorization_) on korrektselt vormistatud
- autoriseerimispäises esitatud kasutajanimi ja parool on õigesti kirjutatud.

Vajadusel uue parooli saamiseks pöörduge teenusehalduri poole.

## Kas on võimalik kasutada ühes klientrakenduses mitut redirect-URL-i?

Jah on. Oluline on, et autentimise alustamise päringus oleks kaasa pandud see redirect-URL millele soovitakse kasutaja tagsisuunamist autentimise õnnestumisel. Kõik redirect-URL väärtused peavad olema registreeritud TARA teenuses.

## Miks on autentimispäringus üldse vaja redirect-URL-i näidata?

TARA põhineb OpenID Connect protokollil, mis näeb ette võimalust, et klientrakendusega seotakse mitu tagasipöördumisaadressi. agasipöördumisaadressi eelregistreerimine on vajalik, et vältida ründeid, kus kasutaja suunatakse ründaja soovitud lehele.

## Kas TARA toetab _single sign-on (SSO)_ lahendust?

TARA ei toeta keskset ühekordset sisselogimist. Antud funktsionaalsust pakub Riigi SSO teenus (GovSSO) mille tehniline info on leitav [siit.](https://e-gov.github.io/GOVSSO/)

## Kas TARA-l on olemas _health endpoint_ millega saaks monitoorida klientrakendust?

Hetkel _health_ otspunkti väljapoole ei paku. SK teenuste tervise detailsem monitoorimine on küll kavas ja esimesel võimalusel teavitame sellest klientidele.

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

## Mida arvestada liitumisavalduses toodud klientrakenduse lühinimetuse valimisel?
  
Klientrakenduse lühinimetus on mõeldud kasutajale kuvamiseks mobiilseadmes. Lühinimetuse mobiilseadmes kuvamise eesmärgiks on autentimisprotsessi käigus lõppkasutaja täiendav informeerimine autentimise algatanud infosüsteemist, et muuta autentimise protsess läbipaistvamaks. 

Lühinimetuse valimisel tuleks arvestada järgmiste asjaoludega:

1. Klientrakenduse lühinimetuse pikkus on piiratud. <br/><br/>Lühinimetuse maksimaalne lubatud pikkus sõltub nimetuses kasutatavatest tähemärkidest. Kui piirdutakse standardsete [GSM märgistiku](https://en.wikipedia.org/wiki/GSM_03.38) sümbolitega võib lühinimetus olla maksimaalselt 40 tähemärki. Juhul kui kasutatakse GSM-7 väliseid sümboleid, võib lühinimetus olla maksimaalselt 20 tähemärki.

2. Klientrakenduse lühinimetus peaks olema kasutajale arusaadav ja seostatav autentimist algatava infosüsteemiga. <br/><br/>Kui klientrakenduse nimetuse maksimaalne pikkus ei ületa ülaltoodud piirangut võib sama nimetust kasutada ka lühinimetuse puhul. Muul juhul võib klientrakenduse lühinimetuse tuletada infosüsteemi täispikast nimetusest. Näiteid klientrakenduse lühinimetustest:`Eesti.ee`, `E-MTA`, `eKool`.

## Autentimisdialoog avaneb, teen autentimise läbi, aga siis tuleb veateade `Teenusele ligipääs suletud`.

Kontrolli, et klientrakendus kasutab TARA poole pöördumisel õiget `clientid`-d ja tagasuunamis-URL-i (`redirecturi`). `clientid` tuleb anda autentimispäringus. Tagasisuunamis-URL tuleb anda nii autentimis- kui ka identsustõendi küsimise päringus. Need väärtused peavad täpselt vastama RIA-s registreeritutele. Kas oled neid väärtusi muutnud? Kas sul on mitu klientrakendust? Võib-olla nende väärtused on segamini läinud? Ülekontrollimiseks, milline `clientid` ja `redirecturi` on registreeritud, võib pöörduda RIA teenusehalduri poole.

## TARA ütleb, et `Required+scope+&lt;openid&gt;+not+provided`.

Põhjus - nagu ka teiste päringuparameetrite probleemide puhul - võib olla URL-kodeerimises. Skoobi `eidasonly` kasutamisel tuleb see saata koos skoobiga `openid`. Skoobid tuleb eraldada tühikuga (URL-encoded kujul: `openid%20eidasonly`). Saatmisel jälgida, et tühik URL-kodeeritakse, aga mitte rohkem, kui üks kord. 

## Mul ei ole tagasipöördumis-URL-is nõutud domeeni? Kas on lihtsam viis TARA testimiseks?

Testkeskkonnas võib tagasipöördumis-URL-ks valida ka `localhost`-i sisaldava URL-i. Sellisel juhul suunab TARA kasutaja pärast autentimist tagasi kasutaja masinas töötavale rakendusele. Toodangus `localhost`-i kasutada ei tohi.

## Kas kõik spetsifikatsioonis nõutu tuleb teostada?

Jah, tegu on turvaprotokolliga, millest mittevajalik on juba eemaldatud. Kõik nõuded tuleb täita, sh testimisse puutuv. Mittekohustuslikud asjad, nt `nonce` kasutamine on, on selgelt markeeritud.

## Kas veebilehitsejas küpsiste (cookies) salvestamine peab olema lubatud?

Jah, TARA kasutamiseks peab olema küpsiste salvestamine lubatud. Vastasel juhul võib tekkida veateade "Teie sessiooni ei leitud! Sessioon aegus või on küpsiste kasutamine Teie brauseris piiratud".

## TARA hakkab ID-kaardiga sisse logimise kuval kontrollima kasutaja veebisirvikus Web eID laienduse olemasolu.

TARA hakkab ID-kaardiga sisse logimise kuval kontrollima kasutaja veebisirvikus Web eID laienduse olemasolu.
1) Kui kasutaja arvutis on Web eID veebisirvikulaiendus töökorras, siis ühtegi lisateadet ei kuvata.
2) Kui kasutaja arvutis on Web eID veebisirvikulaiendus puudu, siis kuvatakse hoiatusteade, mis ei takista ID-kaardiga autentimise kasutamist:
![tara-web-eid-tekst1a](https://user-images.githubusercontent.com/98874684/211541695-86b23df0-5245-425a-9b77-a4026788adba.png)


Praegu jätkub TARA-s ID-kaardiga autentimine senise lahendusega (TLS-i kliendisertifikaadipõhiselt), kuid 2023. aasta kevadel on planeeritud TARA-s selle vahetamine uue Web eID lahenduse vastu.

Kasutajal peaks olema ajakohased Web eID komponendid (Web eID versioon 2) kui ta on paigaldanud või uuendanud ID-tarkvara pärast 2022. märtsi.

## Mida tähendab sõna "TARA"?

Vt: Joh. V. Veski, [Sõna "tara" tähenduse asjus](https://dea.digar.ee/cgi-bin/dea?a=d&d=uuseesti19361028.2.57), "Uus Eesti", 1936 (Rahvusraamatukogu DIGARi Eesti artiklid)

<p>&nbsp;</p>

Kui küsimustest ei saanud abi, siis pöördu kasutajatoe poole: help@ria.ee.
{: .adv}
