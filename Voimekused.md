---
permalink: Voimekused
---

# eIDAS/TARA võimekuste ajaplaan

<img src='img/PLAAN-1.PNG' style='width: 800px;'>

Teenuseportfellis: &#128273;<br>
TP-95 RIA eIDAS konnektorteenus<br>
TP-93 Eesti autentimisteenus<br>
TP-192 RIA autentimisteenus<br>
TP-210 TARA (RIA autentimisteenus) testkeskkond<br>

Projektiportfellis: &#128273;<br>
APP-300 RIA eIDAS konnektorteenus<br>
APP-237 RIA autentimisteenuse (TARA) lisaarendused<br>
APP-330  Autentimisteenuse turvauuring<br>
APP-333 Autentimisteenuse turvatestimine<br>
APP-325 (ECDSA) DD4J muudatused kaardirakenduse ühilduvuseks e-teenustes ja keskse autentimisteenuse loomine<br>
APP-309 Eesti autentimisteenuse pisiarendused

Repod:

| teenus | dokumentatsioon | kood |
|--------|-----------------|------|
| RIA konnektorteenus | [eIDAS-Connector](https://e-gov.github.io/eIDAS-Connector/) | [eIDAS-Client](https://github.com/e-gov/eIDAS-Client) |
| TARA teenus (RIA autentimisteenus) | [TARA-Doku](https://e-gov.github.io/TARA-Doku/) | [TARA-Server](https://github.com/e-gov/TARA-Server)<br> [TARA-Client](https://github.com/e-gov/TARA-Client)<br> [TARA-Management](https://github.com/e-gov/TARA-Management) |

| nr | võimekus | selgitus | staatus |
|----|----------|----------|---------|
| 1  | __RIA konnektorteenus__ | TP-95 |         |
| 1.1  | teenus testkeskkonnas |       | OK |
| 1.2  | [liidestamisjuhend](https://e-gov.github.io/eIDAS-Connector/Liidestamisjuhend) | üldine juhend liidestujatele  | OK  |
| 1.3  | [liidese spetsifikatsioon](https://e-gov.github.io/eIDAS-Connector/Spetsifikatsioon) | täpne kirjeldus liidestujatele | alustamisel (APP-237) |
| 1.4  | [eIDAS klient](https://github.com/e-gov/eIDAS-Client)            | näiterakendus, mis teostab liidese RIA eIDAS konnektorteenusega, vastavalt spetsifikatsioonile (vt p 1.2) | alustamisel (APP-237) |
| 1.5  | teenus toodangukeskkonnas |     |    |
| 2    | __Eesti autentimisteenus__ | TP-93 |     |
| 2.1  | teenus testkeskkonnas   | võimaldab autentida välisriigi e-teenust kasutavat eestlast | töös (APP-309) |
| 2.2  | teenus toodangukeskkonnas |   |     |
| 3    | __TARA teenus (RIA autentimisteenus)__ | TP-192, TP-210 |  |
| 3.1  | m-ID ja ID-kaardiga autentimine (testkeskkonnas) | OK  | APP-325 |
| 3.2  | turvalisus kontrollitud (m-ID ja ID-kaart) | Turvatestimine teostatud | hankimisel (APP-333) |
| 3.3  | m-ID ja ID-kaardiga autentimine (toodangukeskkonnas) |   | APP-325 |
| 3.4  | eIDAS autentimine (testkeskkonnas) | võimaldab autentida Eesti e-teenust kasutavat välismaalast | alustamisel (APP-325) |
| 3.5  | eIDAS autentimine (toodangukeskkonnas) |   | alustamisel (APP-325) |
| 3.6  | SSO suhtes selgus | Autentimisteenuse turvauuring läbi viidud | ettevalmistamisel (APP-330) |
