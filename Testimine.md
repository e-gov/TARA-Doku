---
permalink: Testimine
---

# Testimine

TARA testkeskkond asub aadressil: https://tara-test.ria.ee/. TARA testkeskkond on liidestatud vastu test-OCSP ja test-DigiDocService teenust.
{: .note}

## Sissejuhatus

TARA teenuse testimise eelduseks on TARA testteenusega liitumine (vt https://www.ria.ee/ee/autentimisteenused.html).
Liitumise järel on võimalik teenust koheselt testida, kasutades liitumise järel saadetud kredentsiaale.

## Testplaan

Testplaani täitmiseks on tarvilik eelnevalt tutvuda päringu moodustamise spetsifikatsiooniga: https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus. 
Päringute katsetamiseks ja ka *debugimiseks* saab kasutada **cURL-i** töövahendi näidispäringuid (vt peatükk "cURL näidispäringud").
Mobiil-ID ja ID-kaardiga autentimisel tuleb lähtuda siin väljatoodud juhendist: https://www.id.ee/index.php?id=30303, lühidalt on juhend resümeeritud peatükis "Testimine testnumbrite ja ID-kaardiga"


| Testsamm      | Oodatav tulemus|
| ------------- |:-------------:|
| 1. Tee autentimispäring (HTTP GET päring) otspunkti https://tara-test.ria.ee/oidc/authorize vastu |  Brauseris avaneb TARA testkeskkonna sisselogimise lehekülg |     
| 2. Alusta autentimist m-ID-ga. Kasuta selleks test-mobiilinumbreid või enda üleslaaditud mobiiliandmeid | Autentimine viiakse läbi ning brauser suunatakse liitumistaotluses väljatoodud naasmisaadressile (*redirect* URL-ile). Päringuga antakse kaasa väärtustatud *code* parameeter |
| 3.  | -     |

## cURL näidispäringud

## Testimine testnumbrite ja ID-kaardiga
