---
permalink: FAQ
---

# FAQ

1 _Teatud juhtudel saab võõra ID-kaardiga sisse logida. See ei ole soovitav käitumine._

ID-kaardiga autentimisel küsitakse kasutajalt alati PIN1 koodi.

Kasutajamugavuse suurendamiseks kasutatakse sirvikutes PIN1 puhverdamist. See tähendab, et kasutajalt töö käigus PIN1-te enam ei küsita. Oht on selles, et kui kasutaja lahkub ja tuleb uus kasutaja, siis uus kasutaja võib ilma PIN1-te teadmata (puhverdamise tõttu seda temalt ei küsitagi) eelmise kasutaja nime all e-teenusesse sisse. See on väga tõsine oht. PIN1 küsimine toimub kasutaja arvutis. TARA poolelt ei saa sirviku käitumist täielikult juhtida. Ohu vältimiseks tuleb alati järgida soovitusi:

"Veebilehitsejad võivad puhverdada (ajutiselt salvestada) aktiivse ID-kaardi sessiooni käigus sisestatud PIN1 ehk isikutuvastamise koodi. Selle tulemusel võib õnnestuda erinevatesse e-teenustesse sisselogimine ilma PIN1-koodi korduvalt sisestamata. PIN1-koodi „puhverdamist“ saab vältida, kui järgida järgmist kolme põhimõtet:  
•	kui kasutasid ID-kaarti internetis mõnes e-teenuses, siis teenuse kasutamise lõppemisel vajuta kindlasti „Välju“, „Logi  välja“ või „Sulge“ nupule;
•	pärast ID-kaardi elektroonilise kasutamise lõppu võta ID-kaart alati kaardilugejast välja;
•	sulge kindlasti kõik veebilehitseja (Internet Explorer, Mozilla Firefox, Chrome, Safari) aknad kui eelnev on tehtud. 
"
Allikas: www.id.ee, "Olulised turvanõuded ID-kaardi kasutamiseks".

Täiendava meetmena on hea võõrast inimest oma isiklikku arvutisse üldse mitte lubada. Samuti pakuvad sirvikud privaatsirvimise võimalust (_private browsing_). ID-kaardi kasutamisel sirviku privaatsirvimisaknas on suurem kindlus, et puhvrid ja kasutusajalugu tühjendatakse.

2 _Millised on nõuded client_id-le?_

`client_id` on autentimisteenust TARA kasutava rakenduse avalik identifikaator. Internetis on küll [soovitusi](https://www.oauth.com/oauth2-servers/client-registration/client-id-secret/) valida `client_id` juhuslikult - siis on seda raskem ära arvata ja ründeid konstrueerida. Avalikus e-teenuses ei ole `client_id` peitmine siiski võimalik. Seetõttu soovitame `client_id` valida sisukana, s.t `client_id` peaks andma aimu rakendusest ja asutusest, samuti kas kasutatakse test- või toodangukeskkonda. Teenuse kasutajale on `client_id` nähtamatu. Kuna `client_id` edastatakse autentimispäringus, URL-i koosseisus, siis on lihtsam piirduda ladina tähtedega. Täpitähed on lubatud, kuid arvestada, et autentimispäringu URL-is edastatakse need [URL encoded](https://www.url-encode-decode.com/) kujul. Miinus, punkt ja allkriips on lubatud. Kaldkriipsu korral arvestada, et _URL encoded_ kujul on see `%2F`. `client_id` on tõstutundlik.

3 _Autentimisdialoog avaneb, teen autentimise läbi, aga siis tuleb veateade `Teenusele ligipääs suletud`._

Kontrolli, et klientrakendus kasutab TARA poole pöördumisel õiget `client_id`-d ja tagasuunamis-URL-i (`redirect_uri`). `client_id` tuleb anda autentimispäringus. Tagasisuunamis-URL tuleb anda nii autentimis- kui ka identsustõendi küsimise päringus. Need väärtused peavad täpselt vastama RIA-s registreeritutele. Kas oled neid väärtusi muutnud? Kas sul on mitu klientrakendust? Võib-olla nende väärtused on segamini läinud? Ülekontrollimiseks, milline `client_id` ja `redirect_uri` on registreeritud, võib pöörduda RIA teenusehalduri poole.

4 _OAuth2 teek üritab jwks otspunktist võtme võtmeidentifikaatori abil kätte saada, kuid see ei õnnestu._

Teenuse avaliku allkirjastamisvõtme otspunkt ([https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks)) pakub praegu ühtainust võtit. See tuleb võtta ja kasutada. TARA edasiarendamisel (2018 lõpp - 2019 algus) lisame dünaamilise võtmevahetuse (_key rollover_). Siis saab võtit pärida võtmeidentifikaatoriga.

5 _TARA ütleb, et Required+scope+&lt;openid&gt;+not+provided._

Põhjus - nagu ka teiste päringuparameetrite probleemide puhul - võib olla URL-kodeerimises. Skoobi `eidasonly` kasutamisel tuleb see saata koos skoobiga `openid`. Skoobid tuleb eraldada tühikuga (URL-encoded kujul: `openid%20eidasonly`). Saatmisel jälgida, et tühik URL-kodeeritakse, aga mitte rohkem, kui üks kord. 

6 _Mul ei ole tagasipöördumis-URL-is nõutud domeeni? Kas on lihtsam viis TARA testimiseks?_

Testkeskkonnas võib tagasipöördumis-URL-ks valida ka `localhost`-i sisaldava URL-i. Sellisel juhul suunab TARA kasutaja pärast autentimist tagasi kasutaja masinas töötavale rakendusele. Toodangus `localhost`-i kasutada ei tohi.

7 _Kas kõik spetsifikatsioonis nõutu tuleb teostada?_

Jah, tegu on turvaprotokolliga, millest mittevajalik on juba eemaldatud. Kõik nõuded tuleb täita, sh testimisse puutuv. Mittekohustuslikud asjad, nt `nonce` kasutamine on, on selgelt markeeritud.

8 _Mida tähendab sõna "TARA"?_

Vt: Joh. V. Veski, [Sõna "tara" tähenduse asjus](https://dea.digar.ee/cgi-bin/dea?a=d&d=uuseesti19361028.2.57), "Uus Eesti", 1936 (Rahvusarhiivi Digar-kogu)

<p>&nbsp;</p>

Kui küsimustest ei saanud abi, siis pöördu kasutajatoe poole: help@ria.ee.
{: .adv}
