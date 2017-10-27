---
permalink: Turvaanaluus
---

{% include Issue.html %}

# Turvaanalüüs
{: .no_toc}

- TOC
{:toc}

Käesolev dokument ei käsitle kõiki teenuse osutamisega seotud turbeküsimusi ega konkreetseid rakendatud turvameetmeid. ISKE turvaklassi määramise osale lisame soovitusi ja nõudeid teenusekasutajale oma klientrakenduses rakendamiseks.

## 1	Ülevaade

1.1	TARA on turvaline autentimisteenus, millega asutuse e-teenus saab autentida mobiil-ID kasutaja. TARA teenust pakub Riigi Infosüsteemi Amet. E-teenus kasutab TARA OpenID Connect protokolli kohaselt. Edaspidi TARA võimalused laienevad: täiendavad autentimismeetodid, sh piiriülene eIDAS-autentimine, võimalik, et ka ühekordne sisselogimine (SSO) jm. TARA-teenust osutatakse test- ja toodangteenusena. Käesolev analüüs käsitleb TARA testteenust.

1.2	Teenuse dokumentatsioon koosneb avalikust ja mitteavalikust osast. Avalik dokumentatsiooni tööversioon on avaldatud GitHub-is: https://e-gov.github.io/TARA-Doku/; Avaliku dokumentatsiooni ametlik versioon avaldatakse RIA veebilehel. Mitteavalik dokumentatsioon asub https://confluence.ria.ee/display/TARA/TARA+autentimisteenus.

1.3	Teenuse osutamise õiguslikuks aluseks on RIA põhimääruse § 9 103 (e-identiteedi tarkvara ja piiriülese autentimissüsteemi arendamine ja haldamine).

1.4	Käesolev turvaanalüüs on suunatud ISKE turvaklassi määramisele.

## 2	Käideldavus

2.1	Nõuded käideldavusele on määratud TARA testteenuse SLA-s. Testteenust kasutatakse teenusega liidestuvate klientrakenduste testimiseks. Katkestused häiriksid arendustööd, kuid mõju oleks väike. Turvaosaklass K1 (töökindlus 90%; lubatud summaarne seisak nädalas – ööpäev). Märkus. Toodanguteenuses tõstame turvaosaklassi K2-le.

## 3	Terviklus

3.1	Testteenuses kasutatakse ei tehta tegelikku autentimist toodangteenustele ligipääsu mõttes. Kuid kasutatakse reaalset mobiil-ID teenust. Sellele vaatamata on nõuded terviklusele suhteliselt kõrged, sest testteenuse eesmärk on võimalikult lähedalt imiteerida toodanguteenuse käitlust. Turvaosaklass T2 (info allikas, selle muutmise ja hävitamise fakt peavad olema tuvastatavad; vajalik on info õigsuse, täielikkuse ja ajakohasuse perioodiline kontroll). Märkus. Toodanguteenuses tõstame turvaosaklassi T3-le (nõuab räside turvaaheldamist).

## 4	Konfidentsiaalsus

4.1	Kuna testteenuse eesmärk on võimalikult lähedalt imiteerida toodanguteenuse käitlust, on nõuded konfidentsiaalsusele suhteliselt kõrged. Turvaosaklass S2 (salajane info: info kasutamine on lubatud ainult teatud kindlatele kasutajate gruppidele, juurdepääs teabele on lubatav juurdepääsu taotleva isiku õigustatud huvi korral).

## 5	Koondhinnang

5.1	TARA testteenuse turvaklass on K1T2S2, millest tulenevalt andmekogu turvatase on M.
