---
permalink: /
---

<img src='img/ee_cef_0.png' style='width:400px'>

<img src='img/LOVID.png' style='width: 80px;'>

12.12.2018 Protokollimuudatus. TARA kasutajaliideses keele määramiseks tuleb praegu autentimispäringus kasutada parameetrit `locale` (vt [Tehniline kirjeldus](TehnilineKirjeldus#41-autentimisp%C3%A4ring)). OpenID Connect protokolliga parema vastavuse tagamiseks teeme muudatuse: keelt hakkab määrama parameeter `ui_locales` (vt [OpenID Connect spetsifikatsioon](https://openid.net/specs/openid-connect-core-1_0.html#AuthRequest)). Muudatuse üleminekuperiood on kuus kuud: `ui_locales` saab kasutatavaks jaanuari 2019 lõpus;  `locale` kasutuse lülitame välja juuli 2019 lõpus. Üleminekuperioodil on paralleelselt kasutatavad mõlemad parameetrid.
{: .adv}

14.09.2018 Täpsustasime [andmekaitsetingimusi](Isikuandmed). Eraelu paremaks kaitseks loobume mobiilinumbri väljastamisest identsustõendis.
{: .adv}

07.09.2018 TARA toodangukeskkonda paigaldati täna Coop pangalink. Testkeskkonda jäid testimiseks alles SEB ja Luminori pangalingid.
{: .adv}

31.08.2018 TARA toodangukeskkonda paigaldatakse uus versioon 04.09.2018. Uue versiooni käigus lisatakse pangalinkidega autentimise võimekus. Esialgselt plaanitud skoobist jääb välja Danske bank. Lisatakse järgnevate pankade pangalingid: Swedbank, SEB, LHV ja Luminor.
{: .adv}

30.08.2018 Täiendasime liitumistaotluse vormi: palume anda ka kasutajate arvu prognoos ja autentimismeetodid, mida soovite kasutada.
{: .adv}

29.08.2018 Lisame teenusele uue ID-kaardi toe. Uusi ID-kaarte hakatakse väljastama 2019. a ja need nõuavad ID-kaardiga autentimise erinevat seadistust. TARA kasutajatele ei muutu midagi.
{: .adv}

# Autentimisteenus TARA

TARA on teenus, millega asutus saab oma e-teenuses autentida ID-kaardi, mobiil-ID ja pangalinkide kasutaja ning alates 2018 sügisest ka välisriigi kasutaja.

TARA on [eelistatud viis](https://e-gov.github.io/eIDAS-Connector/Valik) piiriülese eIDAS-autentimise lisamiseks asutuse e-teenustele.

TARA teenust pakub Riigi Infosüsteemi Amet. 

Ametlikku teavet TARA ja RIA muude teenuste kohta vt [RIA autentimisteenuste lehelt](https://www.ria.ee/et/riigi-infosusteem/eid/partnerile.html#tara). 

Käesoleva lehe teave on mõeldud kolmele sihtrühmale. Asutuse äriüksuse juht saab siinse teabe alusel hinnata TARA sobivust, liidestustööde mahtu ja teha otsuse TARA kasutuselevõtuks. Liidese arendaja leiab siit kogu vajaliku tehnilise teabe asutuse e-teenuste liidestamiseks TARA-ga. Lehel on (eraldatult) ka teavet, mis on seotud TARA edasiarendamisega.

## Ärikasutajale

[Ärikirjeldus](Arikirjeldus) · <a href='https://www.ria.ee/et/riigi-infosusteem/eid/partnerile.html#tara'>Liitumine</a>

[Andmekaitsetingimused](Isikuandmed)

<a href='https://e-gov.github.io/TARA-Doku/files/TARA-tutvustus.pdf' target='_new'>eIDAS autentimise lisamine e-teenusele</a>, esitlus eID infopäeval 31.10.2017<br>
<a href='https://e-gov.github.io/TARA-Doku/files/PiiriyleneAutentimine.pdf' target='_new'>Piiriülene autentimine</a>, esitlus eID infopäeval 15.11.2017<br> 

## Liidese arendajale

[Tehniline kirjeldus](TehnilineKirjeldus)

[Testimine](Testimine)

[Näiterakendused](Naited)

[Sõnastik](Sonastik) · [Viited](Viited) · [FAQ](FAQ)

## TARA teenuse arendajale

[QA strateegia](QaStrateegia)

[Kood ja arendaja dokumentatsioon](Arendajale)
