---
permalink: QaStrateegia
---

## Sissejuhatus

Selle dokumendi eesmärk on anda üldine ülevaade TARA projekti tarkvara arenduse kvaliteedi tagamise protsessist, tegevustest ja vahenditest.
Tegemist on "elusa" dokumendiga mida uuendatakse vastavalt projekti edenemisele. 

### Selles dokumendis ei käsitleta järgnevat
* Turvatestimist
* Jõudlustestimist
* Paigalduse testimist
* Tootestamist

## Keskkonnad ja infrastruktuur

Visioon on kasutada kahte erinevat keskkonda:<br>
**Tsentraalne** - RIA taristusse paigaldatud eIDAS node, eIDAS klient ja TARA autentimisteenus. Tara autentimisteenus paigaldatakse automaatselt kasutades Jenkinsit. Peamine eesmärk on teostada süsteemitestimist RIA IT profiilile vastavas keskkonnas, läbides muuhulgas kõik automaattestid.<br>
**Lokaalne** - arendaja arvutis üles seatav eIDAS node, eIDAS klient ja TARA autentimisteenus (kasutab Dockerit). Peamine eesmärk on hõlbustada arendust, probleemide uurimist ja koodi silumist.

## Arendusprotsess
Toodet arendatakse avatud koodiga arenduse põhimõtteid järgides kasutades GitHubi koodihoidlat. Kasutatakse kahte peamist haru:
* Master - tootestusvalmis kood koos vastava dokumentatsiooniga
* Develop - Arendatav kood

<img src='img/ArendusProtsess.png' style='width: 600px;'>
Joonis 1. Arendusprotsess

Arendusprotsessiks kasutatakse SCRUM-il põhinevat iteratiivset protsessi. Protsessil on järgmised olulised elemendid:

  * Toote arendust hallatakse läbi töönimekirjade (product backlog)
  * Kasutatakse kahe nädala pikkuseid sprinte
  * Toimuvad sprindi planeerimise ja lõpetamise koosolekud
  * Sprinte hallatakse läbi JIRA (sprint backlog)
  * Toimuvad hommikused meeskonna koosolekud
  * Toimuvad iganädalased projekti koosolekud lahtiste küsimuste arutamiseks ja sprintide planeerimiseks ning lõpetamiseks
  * Toimuvad retrospektiivid vähemalt korra kahe kuu jooksul
  * Töö lõpetamise tingimus (Definition of done) - arendustöö on läbinud koodi ülevaatuse, omab piisavat ühiktestide kaetust, piisavat dokumentatsiooni ning on läbinud nii arenduse kui vastuvõtu testimise. Piisava dokumentatsiooni põhikriteerium on, et “master” harus paiknevat koodi on alati võimalik kaasasolevat ehitus- ja paigaldusjuhendit järgides edukalt paigaldada RIA süsteemiadministraatorite poolt (kontroll teostatakse iga kord, kui toode tarnitakse testkeskkonda). “Done” staatusesse võib töid tõsta üksnes RIA osapool. 

### Jira töövoog

Jira töövoo olekute kirjeldused:

  * Sprindi töönimekiri (To Do) - Antud sprindis teostatavad tööd
  * Töös olevad ülesanded (In Progress) - Hetkel töös olevad ülesanded
  * Ülevaatusel (In Review) - tehtud töö on ülevaatusel
  * Testimises (Test) - tehtud töö on arendaja poolsel testimisel
  * Vastuvõtu testimises (In Acceptance Test) -  tehtud töö on vastuvõtu testimisel
  * Lõpetatud (Done) - töö täidab lõpetamise tingimusi ning loetakse valminuks

### Jira tööülesannete nõuded vastavalt töö olekule

|Olek| Nõuded |
|-----|--------|
|Toote töönimekiri (product backlog)|Toote töönimekirja võib sisestada tööülesandeid pealkirja tasemel mis kirjeldab ülesande peamist olemust. Enne sprindi planeerimist peab olema toote töönimekiri detailiseeritud tasemel mis võimaldab määrata tööde prioriteete ning anda ajahinnanguid.|
|Sprindi töönimekiri (To Do - sprint backlog)|Sprindi töönimekirjas olevad tööülesanded peavad omama ajahinnangut ning piisavalt detailset kirjeldust võimaldamaks teostada ülesande analüüsi.|
|Töös olevad ülesanded (In progress)|Töö käigus kirjeldatakse nõuded ning valitud lahendus. Kirjeldus peab olema piisavalt detailne võimaldamaks koodi ülevaatusel ja testimisel hinnata valminud töö vastavaust nõuetele.|
|Ülevaatusel (In Review)|Ülevaatuse käigus parandatakse/täiendatakse ka tööülesande kirjeldust.|
|Testimises (Test)|Testimise käigus kirjeldatakse mida ja kuidas testiti. Hinnatakse tööülesande kirjeldust vastuvõtutestimise teostamiseks.|
|Vastuvõtu testimises (In Acceptance Test)|Märgitakse töö vastuvõetuks või tuuakse puuduste põhjendused|
|Lõpetatud (Done)|Töö vastab nõuetele|

## Kvaliteedi tagamise tegevused tarkvara arendusel

**Arendusprotsessi parendamine**

Arendusprotsessi jälgitakse pidevalt ning kohandatakse vastavalt muutunud olukorrale. Oluliseks tagasisidestamiseks on retrospektiivid, kuid probleemidest tuleb teavitada koheselt.

**Ühiktestid**

Uus funktsionaalsus peab olema kaetud ühiktestidega, piisav kaetus on antud funktsionaalsuse arendaja vastutada. Kaetuse määraks on 75% olulistest koodiridadest. Kood peab kompileeruma ning kõik ühiktestid tuleb läbida vigadetta, enne kui koodi võib lisada repositooriumi. Ühiktestide koodi arendatakse ning hallatakse samadest tööpõhimõtetest lähtudes (koodi stiil, parimad praktikad, ...) nagu tootekoodi.

**Staatiline testimine ja koodi ülevaatused**

Staatilise analüüsi vahendina kasutatakse tsentraalses testkeskonnas SonarCube-i mis on liidestatud Jenkinsini töövooga. Lokaalselt kasutavad arendajad SonarLinti.

Kõik muudatused (kaasa arvatud ühiktestide muudatused) vaadatakse üle teise arendaja poolt. Ülevaatus peab jälgima koodi vastavust ka [mittefunktsionaalsetele nõuetele](https://e-gov.github.io/MFN/). Ülevaatusteks kasutatakse GitHubi "pull request" meetodit. Kõik koodi parendamise kommentaarid lisatakse GitHubi. Kood peab läbima ülevaatuse enne testimise algust. 

## Testimine

### Testimise visioon

Eesmärk on automatiseerida testimise funktsionaalsust määral mis annab parimat kasu ajalises ning korduvkasutatavuse mõttes. Ühekordsed tegevused testitakse käsitsi, v.a kui nende automatiseerimine ei ole võimalik samaväärse ajakuluga.

### Testimise protsess
Testiplaani loomisel tuleb analüüsida testide jaotust ühik- ja integratsioonitestide vahel. Kuna tegu on keeruka süsteemiga mis koosneb mitmest välisest liidestusest mis ei ole meie kontrolli all, siis võib olla kiirem osad testid realiseerida ühiktestide tasemel.

<img src='img/AutomaatneTestiprotsess.png' style='width: 600px;'>
Joonis 2. Automaatsete testide protsess tsentraalses testkeskkonnas.

eIDAS autentimise automaatsed testid arendatakse kahes etapis:

**1 etapp - eIDAS kliendi testid**
Testitakse kliendi liidestatust Eesti eIDAS node-iga. SAML liidestuse peamine testimise etapp. Antud testid peavad tagama kasutatavate SAML komponentide õige toimimise. Testid peavad olema pidevalt kaasajastatud ka järgnevates etappides, kuna tagavad SAML liidese korrektse töö ning antud teste teistes etappides ei dubleerita.

**2 etapp - OpenID Connect testid**
Testitakse TARA võimekust konverteerida SAML liidesest tulevat infot OpenID Connect liidesesse. Eeldab SAML liidestuse korrektsust, mida testitakse esimeses etapis.

<img src='img/TestimiseEtapid.png' style='width: 600px;'>
Joonis 3. eIDAS autentimise automaatsete testide etapid tsentraalses testkeskkonnas.

<img src='img/SiseriiklikudAutentimisvahendid.png' style='width: 600px;'>
Joonis 4. TARA autentimisteenusele pangalinkide ning Smart-ID toe lisamise testimine.
 
### Veahaldus
Leitud vead raporteeritakse Jira veahaldussüsteemis ja nad läbivad sama elutsükli mis tööülesanded. Vead mis leitakse konkreetse tööülesande testimisel raporteeritakse kommentaarina tööülesande juures ning suunatakse tagasi arenduse etappi.

Vea raporteerimisel peavad olema kirjeldatud vähemalt järgmised elemendid:
* **Sammud vea kordamiseks** - Eeltingimused, sammud, ajalised piirangud, kui võimalik siis viide testijuhule või autotestile, 
* **Eeldatav tulemus** - Viide spetsifikatsioonile, standardile, ...
* **Tegelik tulemus** - Tulemuse kirjeldus
* **Lisad** - Vearaportile tuleb kaasata võimalikud abimaterjalid, logid, päringud, vastused, pildid, ...

Tähelepanu tuleb juhtida asjaolule, et avatud lähtekoodiga arenduse korral võidakse vigu raporteerida ka läbi GitHubi ning nendele tuleb reageerida.

### Testimise tasemed

**Integratsiooni testimine**

Eesmärk:  testida erinevate komponentide liidestamist/koostööd.

<img src='img/Integratsioonitestid.png' style='width: 600px;'>
Joonis 5. Integrarsioonitestid

**Süsteemi testimine**

Eesmärk: kasutuslugudes ja end-to-end stsenaariumite toimimises vigade leidmine.

<img src='img/Systeemitestid.png' style='width: 600px;'>
Joonis 6. Süsteemitestid

**Vastuvõtu testimine**

Eesmärk:  kontrollida toote või teenuse vastavust püstitatud nõuetele ja planeeritud otstarbele, st vastavust vastuvõtukriteeriumitele.

**Regressiooni testimine**

Eesmärk: minimiseerida riski, et arendusega otseselt mitte seotud funktsionaalsustesse on  tekkinud tahtmatuid mõjutusi.

Regressioonitestimine viiakse läbi vastavalt regressiooninimekirjale.

