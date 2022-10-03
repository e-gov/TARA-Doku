---
permalink: Testimine
---

# Testimine
{: .no_toc}

TARA testkeskkond asub aadressil: [https://tara-test.ria.ee/](https://tara-test.ria.ee/). TARA testkeskkond on liidestatud vastu OCSP, Mobiil-ID, Smart-ID ja eIDAS testteenuseid.
{: .note}

- TOC
{:toc}

## Sissejuhatus

TARA teenuse testimise eelduseks on liitumine TARA testkeskkonnaga (vt [Liitumine](Liitumine)). Liitumise järel on võimalik teenust koheselt testida, kasutades liitumise järel saadetud kredentsiaale.

## Põhivoo testplaan

Testplaani täitmiseks on tarvilik eelnevalt tutvuda [tehnilise kirjeldusega](TehnilineKirjeldus).

Päringute katsetamiseks ja silumiseks saab: <br>

- autentimispäringut teha sirvikust
- identsustõendi päringut teha töövahendiga cURL (vt jaotis "Näidispäringud (testimiseks)").

Mobiil-ID, Smart-ID ja ID-kaardi kasutamiseks autentimisel tuleb lähtuda [juhendist](https://www.id.ee/artikkel/teenuste-testimine/).

| Testsamm                                                                                                  |                                                                                    Oodatav tulemus                                                                                    |
|-----------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| 1. Tee autentimispäring (HTTP GET päring) otspunkti `https://tara-test.ria.ee/oidc/authorize` vastu       |                                                               Sirvikus avaneb TARA testkeskkonna sisselogimise lehekülg                                                               | 
| 2. Alusta autentimist. Kasuta autentimisvahendite jaoks mõeldud testnumbreid või test ID-kaarti           | Autentimine viiakse läbi ja sirvik suunatakse liitumistaotluses väljatoodud tagasisuunamisaadressile (*redirect_uri* URL-ile). Päringuga antakse kaasa väärtustatud *code* parameeter |
| 3. Moodusta identsustõendipäring (HTTP POST päring) otspunkti `https://tara-test.ria.ee/oidc/token` vastu |                                                                Päringule tagastatakse Base64 kodeeritud identsustõend                                                                 |

## Näidispäringud (testimiseks)

TARA liidestumise põhivoog seisneb autoriseerimiskoodi küsimises, selle tagastamises klientrakendusele ja seejärel identsustõendi küsimises. Juhul, kui on paigaldatud proksiserver (klientrakenduse rollis), mis on avalikult kättesaadav ja registreeritud TARAs tagasisuunamisaadressina, on võimalik teenust testida ka brauseri vahendusel.

Autentimispäringut on lihtne teha sirvikust, lisades kõik kohustuslikud parameetrid otspunkti [https://tara-test.ria.ee/oidc/authorize](https://tara-test.ria.ee/oidc/authorize) lõppu järgmisel kujul (näidispäring, loetavuse huvides on URL jagatud mitmele reale):

````
https://tara-test.ria.ee/oidc/authorize?
redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback&
scope=openid&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a`
````

Identsustõendit tuleb küsida vahetult peale autentimispäringu lõpetamist (s.t TARAs tuleb läbida autentimine ID-kaardi, Mobiil-ID või Smart-ID'ga), kasutades saadud autoriseerimiskoodi, 30 sekundi jooksul. Identsustõendipäringu moodustamisel tuleb hoolikalt jälgida päringu moodustamise juhiseid: [tehniline kirjeldus](TehnilineKirjeldus). Päringut saab tööriistaga cURL esitada nii (näidispäring, loetavuse huvides on parameeter `-d` jagatud mitmele reale):

````
curl \
  -H "Authorization: Basic b3BlbklkRGVtbzI6c2VjcmV0"
	-d "grant_type=authorization_code&
	   code=OC-19-5RYWZQpN2HN5dNx4uckK0KI6oKXUZpIoO1D&
	   redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback" \
	-X POST https://tara-test.ria.ee/oidc/token
````

## Testimine ID-kaardi, testnumbrite ja eIDAS'ega

### Kasutajad edukaks autentimiseks

1. Mobiil-ID telefoninumber: `00000766`, isikukood: `60001019906`
2. Smart-ID isikukood: `30303039914`.
3. eIDAS riik Tšehhi: vali ümbersuunamise kuvalt `Testovací profily` ning vali autentimiseks mõni testisik

### ID-kaart

TARA testkeskkond on suunatud vastu OCSP testteenust. See tähendab, et TARAs tuleb autentimiseks rakendada test ID-kaarti või laadida üles enda ID-kaardi autentimissertifikaat testandmebaasi:

1. Test ID-kaardi peab [tellima SK-lt](https://www.sk.ee/teenused/testkaardid/). Juhul kui on võimalus enda ID-kaarti kasutada, ei ole testkaardi tellimine vajalik.

2. Isikliku ID-kaardi kasutamiseks testteenuses peab oma autentimissertifikaadi [üles laadima](https://demo.sk.ee/upload_cert/). Selleks järgida lehel paiknevat juhendit. Peale sertifikaadi üleslaadimist testandmebaasi saab isikliku ID-kaardiga siseneda TARAsse.

### Mobiil-ID

TARA testkeskkond on suunatud vastu Mobiil-ID demo-keskkonda. Kasutamiseks on avalikud testnumbrid:

1. Testnumbrid on kättesaadavad [siit](https://github.com/SK-EID/MID/wiki/Test-number-for-automated-testing-in-DEMO). Rakendada ainult Eesti (EE) testnumbreid ja isikukoode.

### Smart-ID

TARA testkeskkond on suunatud vastu Smart-ID demo keskkonda. Kasutamiseks on kaks varianti:

1. Paigaldada oma seadmesse Smart-ID demo rakendus ja [registreerida demo konto](https://github.com/SK-EID/smart-id-documentation/wiki/Smart-ID-demo#getting-started).

2. Kasutada [testisikuid](https://github.com/SK-EID/smart-id-documentation/wiki/Environment-technical-parameters#test-accounts-for-automated-testing).

### eIDAS

TARA testkeskkond on suunatud eIDAS testkeskkonna vastu. Igal riigil on testimiseks oma lahendus. Kõik riigid ei ole veel liidestusega valmis ning neil võivad puududa avalikud testkasutajad. Täpsema info saamiseks pöörduge `help@ria.ee`. Testimiseks saab kasutada:  

1. Rootsi - vali autentimiseks `Sweden Connect Reference IdP`. Vali autentimiseks kasutatav testisik ja eIDAS autentimistase. Vajuta `Authenticate` ja seejärel `Approve`.
2. Tšehhi - vali `Testing Profiles` ja `Test Profile` või `Test Profile High`. Vali autentimiseks testisik - vaikimisi valitud `Noskova1`. Vajuta `Log in`.

## Täiendavad soovitused

Põhivoo töölesaamine on ainult üks osa TARA teenusega liidestumisel. Erilist tähelepanu tuleb pöörata [turvatoimingute kontrollimisele](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#5-turvatoimingud).

Peale identsustõendi kättesaamist on vaja täiendavalt testida funktsionaalsuseid, mis rakendavad seda identsustõendit. TARA liidestuse järel on vaja veenduda, et identsustõend tagastatakse Teie klientrakendusele kõikide sirvikute ja nende versioonide kombinatsioonides, mida soovite rakendada. 

Nõu küsimiseks ja võimaliku TARA vea raporteerimiseks palume pöörduda meilitsi [help@ria.ee](help@ria.ee) poole.

## Muutelugu

| Versioon, kuupäev | Muudatus                               |
|-------------------|----------------------------------------|
| 0.8, 30.09.2022   | Andmete ajakohastamine                 |
| 0.7, 29.03.2021   | eIDAS testandmete uuendus              |
| 0.6, 29.12.2020   | Pangalingi info uuendus                |
| 0.5, 12.12.2020   | Mobiil-ID ja pangalinkide info uuendus |
| 0.4, 11.09.2020   | Mobiil-ID info uuendus                 |
| 0.3, 05.09.2018   | Norra testandmed                       |
| 0.2, 28.06.2018   | Pangalinkide ja Smart-ID info          |
| 0.1, 15.05.2018   | Esimene versioon                       |
