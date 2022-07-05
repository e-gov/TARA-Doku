---
permalink: HA
---
# [ARHIVEERITUD]

&#9888; Dokumenti ei ajakohastata. Palume siinsele teabele mitte tugineda.

# Käideldavuse analüüs
{: .no_toc}

- TOC
{:toc}

## 1 Ülevaade

Käesolev dokument kirjeldab tehnilisi lahendusi ja protseduurilisi meetmeid, mida TARAs rakendatakse käideldavuseesmärkide saavutamiseks.

Käsitlus hõlmab kahte teenust: 1) autentimisteenus TARA; 2) eIDAS konnektorteenus.

Analüüs hõlmab nii tõrkeid kui ka tarkvara hooldustöödest tingitud katkestusi. Kirjeldame teadaolevad tõrgete klassid, analüüsime nende mõju ja kavandame tehnilised ning protseduurilised meetmed tõrgete kõrvaldamiseks.

Käsitlus ei hõlma taristuspetsiifilisi ega turvameetmeid. Need kirjeldatakse eraldi, piiratud juurdepääsuga dokumendis.

Käideldavus laiemas mõttes võib hõlmata ka: 1) teenindusaega (läbilaskevõimet ja latentsust); 2) kasutajale esitatavate veateadete arusaadavust.

## 2 Toetatav äriprotsess

Toetatav äriprotsess on klientrakendusest tulnud kasutaja autentimine (autentimisprotsess). Ühe kasutaja teenindamine (**teenindusseanss**) kestab u 1-2 minutit. 

 Autentimine jaguneb:

- siseriiklik autentimine
  - ID-kaardiga
  - m-ID-ga
  - Smart-ID-ga
  - pangalingiga
- piiriülene autentimine

## 3 Käideldavuseesmärgid

Kasutajate ootused TARA käideldavusele on kõrged. Üldiseks eesmärgiks on tagada teenuse kõrgkäideldavus (_high availability_), ISKE käideldavuse turvaosaklassile H vastavalt. 

## 4 Komponendid

TARA ja eIDAS konnektorteenuse osutamiseks kasutatakse järgmisi komponente:

komponent | autentimisteenus TARA | eIDAS konnektorteenus
TARA-Server | jah | 
TARA andmebaas | jah |
TARA haldusvahend | jah |
eIDAS-Client | jah |
eIDAS Node | jah | jah
TARA-Stat | jah | 

Märkus. Komponentide kohta lähemalt vt TARA [arhitektuurikirjeldusest](Arhitektuurikirjeldus)

## 5 Sõltuvus teistest teenustest

### 5.1 Välisteenused

Autentimisteenus TARA kasutab välisteenuseid (nimetus, teenuseosutaja):
1) kehtivuskinnitusteenus (OCSP), SK ID Solutions AS
2) MID REST API, SK ID Solutions AS
3) Smart-ID, SK ID Solutions AS
4) pangalink, vastav pank

### 5.2 Siseteenused

TARA sõltub eelkõige:

1) virtuaalmasinate keskkonnast
2) koormusjaoturist
3) logisüsteemist.

Täielik loetelu on piiratud juurdepääsuga dokumendis.

## 6 Tõrked

**Teenuse tõrge** on olukord, kus äriprotsessi, käesoleval juhul kasutaja autentimist ei saa ettenähtud viisil läbi viia: teenus kas ei ole üldse kättesaadav, liiga aeglane, "hangub", annab vale tulemuse jne. Teiste sõnadega, olukord, kus süsteem peaks olema valmis kasutaja teenindamiseks, kuid ei ole seda.

**Komponendi tõrge** on olukord, kus komponent ei tööta (või ei ole valmis töötama) ettenähtud viisil.

**Välis- v siseteenuse tõrge** on olukord, kus teenuse töötamiseks vajalik välis- või siseteenus ei tööta ettenähtud viisil.

## 7 Meetmed

### 7.1 Komponentide paigaldamine eraldi masinatesse

Kõik p 4 nimetatud komponendid paigaldatakse eraldi virtuaalmasinatesse.

### 7.2 Mitmes instantsis paigaldamine ja tõrkesiire

TARA-Server paigaldatakse vähemalt kahes instantsis, mille vahel organiseeritakse automaatne tõrkesiire (_failover_). Tõrkesiirdeks kasutatakse tõrkesiirde võimekusega koormusjaoturit. Koormusjaotur seadistatakse _sticky bit_ põhimõtte kohaselt. Sellega tagatakse, et ühe instantsi tõrke korral suunatakse uued kasutajad toimivasse instantsi. Tõrkuva instantsi teenindusseansid katkevad (kasutajatele antakse teade "Tehniline viga. Proovi veidi aja pärast uuesti").

TARA andmebaasis (PostgreSQL) hoitakse klientrakenduste andmeid. See on suhteliselt staatiline teave (uusi klientrakendusi lisandub harva). Andmebaasi kasutavad TARA-Server ja TARA haldusrakendus. TARA-Server loeb klientrakenduste teabe oma puhvrisse (puhvri uuendamise sagedus on seadistatav. TARA andmebaasi puhul on mõistlik [VAJAB ANALÜÜSI]

TARA haldusvahend on Java rakendus, millega hallatakse klientrakenduste andmeid. Haldusvahend kasutab TARA andmebaasi, nii lugemis- kui ka kirjutamisrežiimis. TARA haldusvahendi mitmes instantsis paigaldamise, veel enam aga automaatse tõrkesiire järele ei ole suurt vajadust.

eIDAS-Client on komponent, mida kasutatakse ainult piiriülese autentimise korral. eIDAS-Client tarkvaral ei ole praegu tõrkesiirde võimekust. Tõrkesiirde võimekuse loomine on kavandatud (tehniliselt Hazelcast-i abil). Arvestades piiriüleste autentimiste prognoositavat suhteliselt väikest arvu, ei ole automaatse tõrkesiirde teostamine eIDAS-Client-is õigustatud, vähemalt mitte esimeses järjekorras.

eIDAS Node tarkvara on mitmes instantsis paigaldatav. Teenindusseansside olekuteavet sünkroonitakse instantside vahel Hazelcast-i abil (vajab seadistamist).  

TARA-Stat on TARA kasutusstatistika kogumise ja esitamise komponent. Komponendi koosseisus on Java veebirakendus ja MongoDB andmebaas. Need paigaldakse ühes instantsis, ühte masinasse. TARA-Stat-i tõrge ei mõjuta teenuse osutamist kasutajale.

### 7.3 Välisteenuste tõrked ja katkestused

[ANALÜÜSIDA]

### 7.4 Hooldustööd

Hooldustööd, mis nõuavad teenuses katkestust, kavandatakse ja kommunikeeritakse [MÄÄRATLEDA TÄPSEMALT]

## Kirjandus

[CAS High Availability Guide](https://apereo.github.io/cas/4.2.x/planning/High-Availability-Guide.html).






