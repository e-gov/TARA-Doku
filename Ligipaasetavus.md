---
permalink: Ligipaasetavus
---
[English](https://e-gov.github.io/TARA-Doku/Accessibility) | [Русский](https://e-gov.github.io/TARA-Doku/Dostupnost)

# Ligipääsetavuse teatis
Käesolev ligipääsetavuse teatis on koostatud [Riigi autentimisteenuse](https://www.ria.ee/riigi-infosusteem/elektrooniline-identiteet-ja-usaldusteenused/kesksed-autentimisteenused#tara) kohta.

Riigi autentimisteenus on vastavuses avaliku teabe seaduse §32 alusel kehtestatud ligipääsetavusnõuetega, välja arvatud järgnevatel juhtudel:

- Klaviatuuri kasutades ei saa seansi aegumise hüpikakent sulgeda Esc klahvi ega ristiga, vaid üksnes "Sain aru" nupu vajutamisega. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.2.1.1.
- Vajutades "Jätka" nuppu vigase sisendi korral, ei ole visuaalselt arusaadav, kus fookus ekraanil asub. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.2.4.3.
- Sattudes teistkordselt samale vahelehele, ei loe ekraaniluger automaatselt ette vahelehe sisu. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.2.4.3.
- "Jätka" nupu fokuseerimine ei ole visuaalselt hästi tajutav. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.2.4.7.
- EU eID vaates puudub riigivaliku menüül eraldi sellekohane silt. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.3.3.2.
- Ebakorrektse isikukoodi vealeht ei anna kasutajale soovitusi vea parandamiseks. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.3.3.3.

Järgnevatele puudustele rakendub ebaproportsionaalse koormuse erand:

- Riigi autentimisteenuses ei rakendu tekstivahe lõigendviit (*text spacing bookmarklet*). Lõigendviite kasutamiseks peaks teenuses sisu turvapoliitika (CSP) esmalt vastava brauserilaiendiga välja lülitama. Teenuse pakkuja ei soovita CSP väljalülitamist, sest tegemist on veebiturvalisuse seadega. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.1.4.12.
- Riigi autentimisteenuse seansi pikkus on seotud autentiva infosüsteemi seansi pikkusega ning sellest tulenevalt ei ole võimalik Riigi autentimisteenuses seanssi pikendada. Kasutaja peab olema võimeline viima autentimise lõpuni 30 minutiga või alustama autentimist uuesti. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 9.2.2.1.
- Riigi autentimisteenuses on defineeritud spetsiifiline kirjastiil ja sellest tulenevalt ei saa brauseri seadetest kirjastiili muuta. Kirjastiili muutmiseks on tarvis kasutada eraldi brauserilaiendit, näiteks Font Changer. Puudujäägi tõttu on mittevastav ligipääsetavusnõue 11.7.

## Klaviatuuriga navigeerimine ja andmete sisestamine
Riigi autentimisteenuses on võimalik navigeerida ja infot sisestada klaviatuuriga.

Navigatsioon toimub tabeldusklahvi (tab) abil. Iga vajutusega liigub fookus järgmisele elemendile. Fookuses oleva valiku aktiveerimiseks tuleb vajutada klaviatuuril sisestusklahvi (enter).

Esimesed kaks linki, mis sellisel viisil navigeerides fookusesse tulevad, on “Liigu edasi põhisisu juurde” ja “Ligipääsetavus”.

“Liigu edasi põhisisu juurde” jätab vahele ligipääsetavuse teatise lingi, keele valiku ja teenuse teated ning hüppab lehe põhisisu ehk autentimismeetodite valikute juurde. “Ligipääsetavus” viitab sellele samale lehele, millel praegu oled.

Andmesisestusväljadel on võimalik kasutada automaatse täitmise (autocomplete) funktsionaalsust. Rippmenüüs saab orienteeruda noolklahvide abil.

## Sisu suurendamine
### Veebibrauser
Sisu suurendamiseks soovitame esmalt kasutada veebibrauserile sisseehitatud funktsionaalsust.

Kõikides populaarsetes veebibrauserites on võimalik lehte suurendada ja vähendada, kui hoida all Ctrl klahvi (macOS operatsioonisüsteemis Cmd klahvi) ja samal vajutada ajal kas + või - klahvi. Teine mugav võimalus on kasutada hiirt: hoides all Ctrl klahvi ja samal ajal liigutades hiire kerimisrulli. Tagasi normaalsuurusesse saab, kui vajutada samaaegselt Ctrl ja 0 klahvile.

Sõltuvalt brauseriakna mõõtmetest muutub rakendus sisu suurendamisel üle teatud astme automaatselt mobiilvaatele.

### Veebibrauseri laiendid
Veebibrauserite jaoks on olemas suurendamist võimaldavad laiendid, mis täiendavad veebibrauseri olemasolevat funktsionaalsust. Näiteks Firefoxi jaoks Zoom Page WE, mis lubab suurendada nii kogu lehte kui ka ainult teksti.
Chrome'i jaoks Zoom for Google Chrome.

### Eraldiseisvad rakendused
Kõik enamlevinud operatsioonisüsteemid sisaldavad rakendusi ekraanil esitatava sisu suurendamiseks.

Windowsis tuleb Magnifier rakenduse avamiseks vajutada klaviatuuril Windowsi logoga klahvi samaaegselt plussklahviga. Sama klahvikombinatsiooniga saab ekraanil olevat sisu suurendada. Sisu suuruse vähendamiseks peab samaaegselt vajutama Windowsi logo klahvi ja miinusklahvi.

MacOS arvutitel tuleb suurendamise kasutamiseks navigeerida järgnevalt: Apple menüü > System Preferences > Accessibility (või Universal Access) > Zoom.

## Ekraanilugeja kasutamine
Ekraanilugeja on rakendus, mis üritab arvutiekraanil kujutatavat interpreteerida ja teistes vormides edasi anda – näiteks helidena, audiokommentaarina. Eelkõige on see abivahend vaegnägijate jaoks.

Riigi autentimisteenuses esitatud sisu on loodud vastavalt ekraanilugejatele arusaadavatele standarditele ja nii, et igat tüüpi visuaalset sisu on võimalik taasesitada ekraanilugejaga. Struktuursete elementide paigutus ja järjekord arvestab ekraanilugeja liikumist ekraanil ja võimaldab infot tarbida loogilises järjekorras.

Valik populaarseid ekraanilugejaid:

- NVDA (Windows, tasuta)
- JAWS (Windows)
- VoiceOver (macOS, tasuta, sisseehitatud)

## Kontaktandmed tagasisidestamiseks
Riigi autentimisteenust haldab Riigi Infosüsteemi Amet. Kui soovite anda tagasisidet ligipääsetavuse kohta või on midagi vajalikku jäänud teie jaoks ligipääsmatuks, siis palun võtke meiega ühendust allpool olevate kontaktide kaudu.
- Veebileht: https://www.ria.ee
- E-post: ria@ria.ee
- Telefon: +372 663 0200

## Nõuete täitmise järelvalve
Avalike teenuste veebide ja rakenduste ligipääsetavuse osas teostab järelevalvet Tarbijakaitse ja Tehnilise Järelevalve Amet.
- Veebileht: https://ttja.ee
- E-post: info@ttja.ee
- Telefon: +372 667 2000

## Info teatise kohta
Käesolev ligipääsetavuse teatis on koostatud 14.11.2024 enesehindamise teel.

## Muutelugu

| Versioon, kuupäev | Muudatus         |
|-------------------|------------------|
| 1.0, 15.12.2023   | Esimene versioon |
| 1.1, 14.11.2024   | Teatise ajakohastamine |
