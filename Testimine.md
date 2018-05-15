---
permalink: Testimine
---

# Testimine

TARA testkeskkond asub aadressil: [https://tara-test.ria.ee/](https://tara-test.ria.ee/). TARA testkeskkond on liidestatud vastu test-OCSP ja test-DigiDocService teenust.
{: .note}

- TOC
{:toc}

## Sissejuhatus

TARA teenuse testimise eelduseks on TARA testteenusega liitumine (vt [https://www.ria.ee/ee/autentimisteenused.html](https://www.ria.ee/ee/autentimisteenused.html).
Liitumise järel on võimalik teenust koheselt testida, kasutades liitumise järel saadetud kredentsiaale.

## Põhivoo testplaan

Testplaani täitmiseks on tarvilik eelnevalt tutvuda päringu moodustamise spetsifikatsiooniga: [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus). 
Päringute katsetamiseks ja *debugimiseks*:
	1. Saab autentimispäringut teha GET päringuga läbi brauseri 
	2. Saab identsustõendi päringut teha **cURL-i** töövahendiga (vt peatükk "Näidispäringud (testimiseks)")
Mobiil-ID ja ID-kaardiga autentimisel tuleb lähtuda siin väljatoodud juhendist: [https://www.id.ee/index.php?id=30303](https://www.id.ee/index.php?id=30303), lühidalt on juhend resümeeritud peatükis "Testimine testnumbrite ja ID-kaardiga"


| Testsamm      | Oodatav tulemus|
| ------------- |:-------------:|
| 1. Tee autentimispäring (HTTP GET päring) otspunkti `https://tara-test.ria.ee/oidc/authorize` vastu |  Brauseris avaneb TARA testkeskkonna sisselogimise lehekülg |     
| 2. Alusta autentimist m-ID-ga. Kasuta selleks test-mobiilinumbreid või enda üleslaaditud mobiiliandmeid | Autentimine viiakse läbi ning brauser suunatakse liitumistaotluses väljatoodud naasmisaadressile (*redirect* URL-ile). Päringuga antakse kaasa väärtustatud *code* parameeter |
| 3. Moodusta identsustõendipäring (HTTP POST päring) otspunkti `https://tara-test.ria.ee/oidc/token vastu`| Päringule tagastatakse base64 kodeeritud identsustõend |

## Näidispäringud (testimiseks)
TARA liidestumise põhivoog seisneb autoriseerimiskoodi küsimises, selle tagastamises klientrakendusele ja seejärel identsustõendi küsimises. Juhul kui on paigaldatud proksiserver (klientrakenduse rollis), mis on avalikult kättesaadav ja registreeritud TARAs tagasisuunamisaadressina, on võimalik teenust testida ka käepäraste vahenditega.

Autentimispäringut on lihtne teha läbi brauseri, lisades kõik kohustuslikud parameetrid [https://tara-test.ria.ee/oidc/authorize](https://tara-test.ria.ee/oidc/authorize) otspunkti lõppu järgmisel kujul (näidispäring):

`https://tara-test.ria.ee/oidc/authorize?redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback&scope=openid&state=hkMVY7vjuN7xyLl5&response_type=code&client_id=58e7ba35aab5b4f1671a`

Identsustõendit tuleb küsida vahetult peale autentimispäringu lõpetamist (s.t läbida tuleb TARAs autentimine ID-kaardi / m-ID-ga), kasutades saadud autoriseerimiskoodi 30 sekundi jooksul. Identsustõendipäringu moodustamisel tuleb hoolikalt jälgida päringu moodustamise instruktsioone siin: [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#43-identsust%C3%B5endip%C3%A4ring](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#43-identsust%C3%B5endip%C3%A4ring). Päringut saab cURL tööriistaga esitada nõnda (näidispäring):

`curl -H "Authorization: Basic b3BlbklkRGVtbzI6c2VjcmV0" -d "grant_type=authorization_code&code=OC-19-5RYWZQpN2HN5dNx4uckK0KI6oKXUZpIoO1D&redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback" -X POST https://tara-test.ria.ee/oidc/token`

## Testimine testnumbrite ja ID-kaardiga
TARA testkeskkond on suunatud vastu test-OCSP ja test-DigiDocService teenust. See tähendab, et TARAs tuleb autentimise läbiviimiseks rakendada testnumbreid ja test ID-kaarte või teha oma mobiilinumber ja ID-kaardi sertifikaadid kättesaadavaks ka testteenuse andmebaasis. Lähemalt võimalustest:
1. Testnumbrid on kättesaadavad siit: [https://github.com/SK-EID/dds-documentation/wiki/Test-number-for-automated-testing-in-DEMO](https://github.com/SK-EID/dds-documentation/wiki/Test-number-for-automated-testing-in-DEMO). Rakendada ainult Eesti (EE) testnumbreid ja isikukoode.
2. Test ID-kaardi peab tellima SK-lt: [https://www.sk.ee/teenused/testkaardid/](https://www.sk.ee/teenused/testkaardid/). Juhul kui on võimalus enda ID-kaarti kasutada, ei ole test-kaardi tellimine vajalik.
3. Isikliku mobiilinumbri saab laadida üles siin: [https://demo.sk.ee/MIDCertsReg/](https://demo.sk.ee/MIDCertsReg/). Selleks järgida lehel paiknevat juhendit. Peale isikliku numbri üleslaadimist on võimalik TARA testkeskkonda siseneda oma mobiilinumbri ja isikukoodiga.
4. Isikliku ID-kaardi sertifikaadid saab laadida üles siin: [https://demo.sk.ee/upload_cert/](https://demo.sk.ee/upload_cert/). Selleks järgida lehel paiknevat juhendit. Peale sertifikaadi üleslaadimist test andmebaasi saab isikliku ID-kaardiga siseneda TARAsse.

## Täiendavad soovitused
Põhivoo töölesaamine on ainult üks osa TARA teenusega liidestumisel. Erilist tähelepanu tuleb pöörata turvatoimingute kontrollimisele (vt [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#5-turvatoimingud](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#5-turvatoimingud)).
Peale identsustõendi kättesaamist on vaja täiendavalt testida funktsionaalsuseid, mis rakendavad seda identsustõendit. TARA liidestuse järel on vaja veenduda, et identsustõend tagastatakse Teie klientrakendusele kõikides brauserite ja nende versioonide kombinatsioonides, mida soovite rakendada. 
Nõu küsimiseks ja võimaliku TARA vea raporteerimiseks palun pöörduda meilitsi [help@ria.ee](help@ria.ee) poole.

## Muutelugu

| Versioon, kuupäev | Muudatus |
|-----------------|--------------|
| 0.1, 15.05.2018   | Esimene versioon |
