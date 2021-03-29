---
permalink: Testimine
---

# Testimine
{: .no_toc}

TARA testkeskkond asub aadressil: [https://tara-test.ria.ee/](https://tara-test.ria.ee/). TARA testkeskkond on liidestatud vastu OCSP ja Mobiil-ID (mID) API testteenust.
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

Mobiil-ID ja ID-kaardiga autentimisel tuleb lähtuda juhendist: [https://www.id.ee/artikkel/teenuste-testimine/](https://www.id.ee/artikkel/teenuste-testimine/).

| Testsamm      | Oodatav tulemus|
| ------------- |:-------------:|
| 1. Tee autentimispäring (HTTP GET päring) otspunkti `https://tara-test.ria.ee/oidc/authorize` vastu | Sirvikus avaneb TARA testkeskkonna sisselogimise lehekülg | 
| 2. Alusta autentimist m-ID-ga. Kasuta selleks test-mobiilinumbreid või enda üles laaditud mobiiliandmeid | Autentimine viiakse läbi ja sirvik suunatakse liitumistaotluses väljatoodud tagasisuunamisaadressile (*redirect* URL-ile). Päringuga antakse kaasa väärtustatud *code* parameeter |
| 3. Moodusta identsustõendipäring (HTTP POST päring) otspunkti `https://tara-test.ria.ee/oidc/token` vastu | Päringule tagastatakse Base64 kodeeritud identsustõend |

## Näidispäringud (testimiseks)

TARA liidestumise põhivoog seisneb autoriseerimiskoodi küsimises, selle tagastamises klientrakendusele ja seejärel identsustõendi küsimises. Juhul, kui on paigaldatud proksiserver (klientrakenduse rollis), mis on avalikult kättesaadav ja registreeritud TARAs tagasisuunamisaadressina, on võimalik teenust testida ka käepäraste vahenditega.

Autentimispäringut on lihtne teha sirvikust, lisades kõik kohustuslikud parameetrid otspunkti [https://tara-test.ria.ee/oidc/authorize](https://tara-test.ria.ee/oidc/authorize) lõppu järgmisel kujul (näidispäring, loetavuse huvides on URL jagatud mitmele reale):

````
https://tara-test.ria.ee/oidc/authorize?redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback&scope=openid&state=hkMVY7vjuN7xyLl5&response_type=code&client_id=58e7ba35aab5b4f1671a`
````

Identsustõendit tuleb küsida vahetult peale autentimispäringu lõpetamist (s.t läbida tuleb TARAs autentimine ID-kaardi või m-ID-ga), kasutades saadud autoriseerimiskoodi, 30 sekundi jooksul. Identsustõendipäringu moodustamisel tuleb hoolikalt jälgida päringu moodustamise juhiseid: [tehniline kirjeldus](TehnilineKirjeldus). Päringut saab tööriistaga cURL esitada nii (näidispäring, loetavuse huvides on parameeter `-d` jagatud mitmele reale):

````
curl \
  -H "Authorization: Basic b3BlbklkRGVtbzI6c2VjcmV0"
	-d "grant_type=authorization_code&
	   code=OC-19-5RYWZQpN2HN5dNx4uckK0KI6oKXUZpIoO1D&redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback" \
	-X POST https://tara-test.ria.ee/oidc/token
````

## Testimine testnumbrite ja ID-kaardiga

### Kasutajad edukaks autentimiseks

1. Mobiil-ID telefoninumber: `00000766`, isikukood: `60001019906`
2. eIDAS riik Norra, vali autentimiseks profiil `MINID`
3. Smart-ID isikukood: `10101010005`.

### ID-kaart ja Mobiil-ID

TARA testkeskkond on suunatud vastu OCSP ja Mobiil-ID testteenust. See tähendab, et TARAs tuleb autentimise läbiviimiseks rakendada testnumbreid ja test ID-kaarte. Alates 09.12.2020 ei ole enam test teenuses võimalik kasutada isiklikku Mobiil-ID kontot. Isikliku ID kaardi kasutus on endiselt võimalik. Lähemalt võimalustest:

1. Testnumbrid on kättesaadavad siit: [https://github.com/SK-EID/MID/wiki/Test-number-for-automated-testing-in-DEMO](https://github.com/SK-EID/MID/wiki/Test-number-for-automated-testing-in-DEMO). Rakendada ainult Eesti (EE) testnumbreid ja isikukoode.

2. Test ID-kaardi peab tellima SK-lt: [https://www.sk.ee/teenused/testkaardid/](https://www.sk.ee/teenused/testkaardid/). Juhul kui on võimalus enda ID-kaarti kasutada, ei ole test-kaardi tellimine vajalik.

3. Isikliku ID-kaardi sertifikaadid saab laadida üles siin: [https://demo.sk.ee/upload_cert/](https://demo.sk.ee/upload_cert/). Selleks järgida lehel paiknevat juhendit. Peale sertifikaadi üleslaadimist test andmebaasi saab isikliku ID-kaardiga siseneda TARAsse.

### eIDAS

TARA testkeskkond on suunatud eIDAS testkeskkonna vastu. Igal riigil on testimiseks oma lahendus, kas parool või kaheastmeline autentimine. Kõik riigid ei ole veel liidestusega valmis, täpsema info saamiseks pöörduge `help@ria.ee`. Testimiseks saab kasutada:  

- Rootsi - vali autentimiseks `Test IdP`, Select the person to authenticate as: `Ulla Alm (198611062384)`, Select assurance level for the authentication: `http://id.elegnamnden.se/loa/1.0/eidas-nf-high`. Vajuta `Authenticate`, seejärel `Approve`.
- Norra - vali autentimiseks `MINID`, personal ID number: `51109599720`, password: `password01`, PIN: `12345`. Vajuta `Continue`.

### Smart-ID

TARA testkeskkond on suunatud vastu Smart-ID demo keskkonda. Kasutamiseks on kaks varianti:

- paigaldada oma seadmesse Smart-ID demo rakendus ja registreeri demo konto: [https://github.com/SK-EID/smart-id-documentation/wiki/Smart-ID-demo#getting-started](https://github.com/SK-EID/smart-id-documentation/wiki/Smart-ID-demo#getting-started)
- kasutada testisikut: [https://github.com/SK-EID/smart-id-documentation/wiki/Environment-technical-parameters#test-accounts-for-automated-testing](https://github.com/SK-EID/smart-id-documentation/wiki/Environment-technical-parameters#test-accounts-for-automated-testing).

## Täiendavad soovitused

Põhivoo töölesaamine on ainult üks osa TARA teenusega liidestumisel. Erilist tähelepanu tuleb pöörata turvatoimingute kontrollimisele (vt [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#5-turvatoimingud](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#5-turvatoimingud)).

Peale identsustõendi kättesaamist on vaja täiendavalt testida funktsionaalsuseid, mis rakendavad seda identsustõendit. TARA liidestuse järel on vaja veenduda, et identsustõend tagastatakse Teie klientrakendusele kõikide sirvikute ja nende versioonide kombinatsioonides, mida soovite rakendada. 

Nõu küsimiseks ja võimaliku TARA vea raporteerimiseks palume pöörduda meilitsi [help@ria.ee](help@ria.ee) poole.

## Muutelugu

| Versioon, kuupäev | Muudatus |
|-----------------|--------------|
| 0.7, 29.03.2021   | eIDAS testandmete update |
| 0.6, 29.12.2020   | Pangalingi info update |
| 0.5, 12.12.2020   | Mobiil-ID ja pangalinkide info uuendus |
| 0.4, 11.09.2020   | Mobiil-ID info update |
| 0.3, 05.09.2018   | Norra testandmed |
| 0.2, 28.06.2018   | Pangalinkide ja Smart-ID info |
| 0.1, 15.05.2018   | Esimene versioon |
