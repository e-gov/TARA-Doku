---
permalink: Arikirjeldus
---

# Riigi autentimisteenus (TARA)

Riigi autentimisteenus (TARA) on Riigi Infosüsteemi Ameti poolt pakutav teenus, millega asutus saab oma e-teenusesse lisada nii siseriiklike kui ka Euroopa Liidu piiriüleste autentimismeetodite toe.

Teenust osutatakse kõigile valitsussektori asutustele vastavalt Rahandusministeeriumi [kodulehel»](https://www.fin.ee/riik-ja-omavalitsused-planeeringud/riigihaldus) toodud tabelile "Avaliku sektori asutused haldusalade kaupa" (v.a Muu avalik sektor).

Siseriiklikest autentimismeetoditest pakume autentimist järgmiste vahenditega:

- mobiil-ID (ainult Eesti isikukoodiga kasutajad)
- ID-kaart
- smart-ID (ainult Eesti isikukoodiga kasutajad) *Palume pöörata e-teenuste osutajatel tähelepanu asjaolule, et Smart-IDga isiku tuvastamisel ei ole võimalik eristada Eesti residente mitte residentidest (näiteks nagu seda võimaldab e-residentide sertifikaat).*

Samuti pakume piiriülest autentimist [Euroopa Liidu teavitatud eID vahenditega](https://ec.europa.eu/cefdigital/wiki/display/EIDCOMMUNITY/Overview+of+pre-notified+and+notified+eID+schemes+under+eIDAS) läbi eIDAS-Node taristu. Käesolevalt on TARAs toetatud järgmiste riikide teavitatud eID vahendid:

- Saksamaa: National Identity Card; Electronic Residence Permit
- Itaalia: Carta di Identità elettronica (CIE); Aruba PEC SpA; Namirial SpA; InfoCert SpA; In.Te.S.A. SpA; Poste Italiane SpA; Register.it SpA; Sielte SpA; Telecom Italia Trust Technologies S.r.l.; Lepida SpA
- Hispaania: Documento Nacional de Identidad electrónico (DNIe)
- Belgia: Belgian Citizen eCard; Foreigner eCard; itsme® mobile App
- Luksemburg: Luxembourg eID card
- Horvaatia: Personal Identity Card (eOI)
- Portugal: Portuguese national identity card (eID card); Digital Mobile Key
- Läti: eID karte; eParaksts karte; eParaksts karte+; eParaksts
- Leedu: Lithuanian National Identity card (eID / ATK)
- Holland: eHerkenning; DigiD Substantieel; DigiD Hoog
- Tšehhi: CZ eID card
- Slovakkia: Slovak Citizen eCard; Foreigner eCard
- Taani: NemID

Eesti riiklike ja piiriüleselt tunnustatud eID vahenditega (ID-kaart, mobiil-ID) on tagatud juurdepääs järgmiste Euroopa Liidu riikide e-teenustele: Austria, Belgia, Hispaania, Holland, Horvaatia, Itaalia, Kreeka, Leedu, Luksemburg, Läti, Malta, Norra, Poola, Portugal, Rootsi, Saksamaa, Slovakkia, Sloveenia, Soome, Taani, Tšehhi.

## Kellele?

Valitsussektori asutustele, kes soovivad:
- oma e-teenustes pakkuda kasutajatele laia valikut autentimismeetodeid, ise neid meetodeid teostamata.
- lisada oma e-teenusele piiriülese autentimise toe.

## Kes Riigi autentimisteenust kasutavad?

Riigi autentimisteenusega on liitunud 46 asutust 166 infosüsteemiga, sh Riigiportaal eesti.ee, e-Maksuamet/e-Toll, Haridusportaal edu.ee, RIHA, ePRIA, Ehitisregister, e-Rahvastikuregister, Teeregister, e-Töötukassa, eToetus, SKAIS, Riiklik patsiendiportaal, EIS, ETIS jt.

## Tehnilised tingimused?

E-teenus liidestatakse autentimisteenusega OpenID Connect protokolli kohaselt. Vt lähemalt: [Tehniline kirjeldus](TehnilineKirjeldus).

RIA pakub alates augustist 2022 Riigi SSO teenust (GovSSO), mille dokumentatsiooniga saab tutvuda [GovSSO GitHub](https://e-gov.github.io/GOVSSO) lehel.

## Kuidas liituda?

Asutusel tuleb:

1 välja selgitada, kas ja millistes e-teenustes RIA autentimisteenust tahab kasutada<br>

2 kavandada ja tellida liidestamistöö

- autentimiskomponendi täiendamine OpenID Connect-ga või väljavahetamine
- hinnanguline töömaht:
  - kogenud arendajal u 2 päeva
  - kui OpenID Connect-i pole varem teinud, siis 2 nädalat
  - kui on vaja välja vahetada olemasolev (mitte OpenID Connect põhine) autentimislahendus (mis tõenäoliselt on tihedalt seotud seansi- ja pääsuhaldusega), siis tasub arvestada vähemalt 1 inimkuuga.

3 teostada arendus<br>

4 esitada RIA-le taotlus teenusega liitumiseks<br>

- seejuures esitada kasutajate arvu prognoos
- RIA registreerib teie rakenduse teenuse kliendiks ja avab teile testteenuse.

5 testida liidest RIA testteenuse vastu

- RIA abistab võimalike probleemide lahendamisel

6 eduka testimise järel taodelda ühendamist toodanguteenusega

- RIA avab toodanguteenuse.

## Millal?

Testteenus on avatud 2017. a sügisest.

Teenus on toodangukeskkonnas avatud 2018. a märtsist.

## Kuidas teenus välja näeb?

<img src='img/KUVA-04.png' width='500'>

## Soovitused Riigi autentimisteenuse integreerimiseks kliendi teenuses

- kui teenuses on kasutuses üksnes Riigi autentimisteenus (TARA), kasutada viidet “Logi sisse” koos paigutusega veebilehe paremal üleval servas

<img src='img/riigiportaal.png' width='500'>

- ainult eIDAS-liidestuse korral kasutada Riigi autentimisteenusele (TARA) suunamiseks viidet “EL riigi eID” / “Other EU country” või kasutada [logo](https://github.com/e-gov/TARA-Login/blob/master/disain/assets/eu_citizen_login_btn_190x50.svg)

<img src='img/eu_citizen_login_btn_190x50_rgb.png' width='150'> 

- kui teenuses on kasutusel Riigi autentimisteenuse (TARA) kõrval ka teisi autentimisvahendeid, kasutada viitena Riigi autentimisteenuse [logo](https://github.com/e-gov/TARA-Login/blob/master/disain/assets/tara_logo.svg) koos selgitusega “Sisene Riigi autentimisteenuse kaudu” või “Sisene läbi Riigi autentimisteenuse”.

<img src='img/tara-logo-et.png' width='150'>
  
<img src='img/tara.png' width='500'>


## Rohkem teavet?

Kontakt: [help@ria.ee](mailto:help@ria.ee).

Kui pöördute liidestamisel või liidestatud klientrakenduses TARA kasutamise tehnilise probleemiga, siis palume valmis panna väljavõte klientrakenduse logist. Tõrkepõhjuse väljaselgitamiseks vajame teavet, mis päring(ud) TARAsse saadeti ja mis vastuseks saadi.

Samuti tasub heita pilk [korduma kippuvate küsimuste rubriiki](Abi).

[Tehniline kirjeldus](TehnilineKirjeldus) (liidese arendajale).
