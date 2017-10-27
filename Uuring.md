---
permalink: Uuring
layout: Lihtne
---

Autentimisteenuse turvauuring
{: .pais}

{: .no_toc}

# Lähteülesanne

kavand 0.2, 27.10.2017

- TOC
{:toc}

## 1 Mõisted

_autentimine_, kasutaja isiku tuvastamise toiming (tehniliselt täpsemas tähenduses: isikusamasuse tuvastamine).<br>
_autentimine kui teenus_, arhitektuurimuster, kus rakendus ei autendi kasutajat ise, vaid delegeerib autentimise eraldiseisvale e-teenusele.<br>
_e-teenus_, asutuse poolt kasutajale, sh välismaalasele pakutav e-teenus.<br>
_kasutaja_, e-teenust kasutav füüsiline isik.<br>
_rakendus_, asutuse veebirakendus, mis pakub e-teenust; koosneb kahest osast: 1) kasutaja sirvijasse laetav osa; 2) serveripoolne osa.<br>
_sisselogimine_, (_sing-on_, _sign-in_), toiming, millega kasutaja isik tuvastatakse ja luuakse seanss kasutaja ja rakenduse vahel.<br>
_ühekordne sisselogimine_, (_single sign-on_, _SSO_), arhitektuurimuster, kus kasutaja saab ühe sisselogimisega kasutada mitut e-teenust.<br>
_ühekordne väljalogimine_ (_single sign-off_), arhitektuurimuster, kus kasutaja logitakse ühe väljalogimisega välja mitmest e-teenusest.<br>
_seanss_, ka _sessioon_, ajas piiratud suhe kasutaja ja rakenduse vahel. Võib alata sisselogimisega aga ka lihtsalt sirvijas esimese pöördumisega rakenduse poole; võib lõppeda väljalogimisega, aga ka sirvija sulgemisega või seansi ühepoolse lõpetamisega rakenduse poolt.<br>
_seansihaldus_, ka _seansi pidamine_, seansi loomise, hoidmise, turvamise ja lõpetamise toimingud.<br>

Käesolevas lähteülesandes kasutatakse ka veebilehel [https://e-gov.github.io/TARA-Doku/Sonastik](https://e-gov.github.io/TARA-Doku/Sonastik) määratletud mõisteid.

## 2 Autentimisteenuse ulatus 

Autentimistoiming on protseduur, mis lõpeb _autentimissündmusega_. See on hetk, kus jõutakse selge ja ühemõttelise tulemuseni &mdash; isik kas loetakse tuvastatuks või mitte, kolmandat võimalust ei ole. 

Autentimisteenus võib olla &mdash; ja sageli ongi &mdash; seotud täiendavate teenustega:

1) _autentimisteenuste vahendamise teenus_. Teenus koondab ja teeb rakendusele kättesaadavaks erinevate teenuseosutajate autentimisteenuseid.

2) _kasutaja rollide väljaselgitamise teenus_. Näiteks, autentimisteenusega võib olla ühitatud päring äriregistrisse. Autentimise tulemus siis sisaldab kasutaja esindusõiguste nimekirja.

3) _rolli valimise teenus_. Näiteks autentimisdialoogis valib kasutaja ühtlasi organisatsiooni, kelle nimel ta tegutseb.

4) _kasutajat kirjeldavate tunnuste (atribuutide) väljastamise teenus_. Minimaalsel juhul tekib autentimissündmuses väga väike teabekogum: tuvastatud isiku identifikaator, nimi, autentimise aeg, mõned konteksti kirjeldavad andmed. Tihti soovitakse, et koos autentimisega selgitatakse välja rohkem kasutajaga seotud andmeid. Rakendus võib kasutajaandmeid käia autentimisteenusest isegi hiljem pärimas (nt OpenID Connect protokollis UserInfo otspunkti kaudu).

5) _kasutaja nõusoleku võtmise teenus_. Kasutaja nõusoleku andmine on tihedalt seotud isiku tuvastamisega. Seetõttu teostatakse need funktsionaalsused tihti koos. Kasutaja nõusolek (_user consent_) võib olla antud andmete töötlemiseks või ka muuks. OAuth 2.0 ongi eelkõige kasutaja nõusoleku võtmise protokoll, kus autentimine on vaid nõusoleku võtmise kaasnähtus.

6) _sessioonihalduse teenus_. _Lihtne autentimisteenus_ piirdub ühekordse autentimistoimingu läbiviimisega. Autentimistoimingu tulemuseks on rakendusele väljastatav teave autentimissündmuse kohta (OpenID Connect protokollis _identsustõendi_ vormis). Seansi loomisega ega hoidmisega lihtne autentimisteenus ei tegele. Sessioonihaldust sisaldav autentimisteenus võtab enda kanda ka sessiooni haldamise tegevusi, nt kasutaja perioodiline uuesti autentimine, seansi aegumise jälgimine jms.  

7) _ühekordse sisselogimise teenus_. 

8) _ühekordse väljalogimise teenus_.

Laiemas tähenduses võib kõiki neid teenuseid nimetada autentimisteenusteks. 
Loetelu ei ole lõplik. Lisada võib kasutusstatistika tootmise teenuse jm. Autentimisteenuse kavandamisel peab endale aru andma, kas eesmärk on piiratud funktsionaalsuse teenus või üksteisega seotud teenuste kobarat pakkuv identiteediplatvorm. Viimane nõuab hoopis suuremaid võimekusi ja ressursse nii arenduses kui ka käitluses.

Tähtis on ka autentimisteenuse ulatus organisatsioonilisel tasandil. Olulised momendid on kas autentimisteenus ületab asutuse piire, kas autentimisteenus on mõeldud kohustuslikuna ja kuidas autentimisteenus suhtestub asutuste olemasolevate autentimislahendustega. Vastavalt eristame:

1) _asutusesisene autentimisteenus_, kus teenuse osutaja ja kasutaja on üks ja sama asutus

2) _asutuseväline autentimisteenus_, kus asutus kasutab, võimalik, et ühe alternatiivina, teise asutuse osutatavat teenust

3) _keskne autentimisteenus_, avaliku sektori ühtne autentimisteenus, mida kõik asutused, võimalik, et vaid väheste eranditega, on kohustatud kasutama.

Kokkuvõte. Autentimisteenust on võimalik osutada nii lihtsa teenusena kui ka osateenustest koosneva laiahaardelise kogumina (platvormina). Oluline küsimus on kas ja millist osateenuste buketti pakkuda, mida erineva ulatusega teenuse pakkumine tähendab, milliseid võimekusi nõuab ja milliseid riske sisaldab &mdash; ja kuidas riskid maandatakse ja võimekused luuakse.

## 3 Riskid

Laiemate võimalustega autentimisteenuse kavandamine nõuab riskianalüüsi. Üldistatud kujul on riskid järgmised:

1) teenuse järele ei ole reaalset vajadust

2) kasutaja ei saa teenusest aru

3) asutuste võimekus (organisatsiooniline, finantsiline, tehniline) teenust kasutusele võtta on madal

4) valitud tehniline kontseptsioon osutub ebaturvaliseks

5) valitud tehniline kontseptsioon osutub mitteteostatavaks

6) valitud tehniline kontseptsioon osutub mittejätkusuutlikuks

7) turul ei leidu kompetentset, huvitatud arendajat

8) turul ei leidu kompetentseid liidestuste arendajaid

9) RIA võimekus teenuse arendust tellida osutub ebapiisavaks

10) RIA võimekus teenust käitada osutub ebapiisavaks

11) teenus kokkuvõttes ei tasu end ära.

## 4 Praegune autentimisteenus

 Keskse autentimisteenuse on loonud paljud riigid [1][2]. Välisriikidest on eeskujuna meile olulisim Soome. Suomi.fi autentimisteenus toetab ühtset sisselogimist [2]. Eestis on keskne autentimisteenus seni puudunud. RIA on 2017. a sügisest aktiivselt arendanud keskset autentimisteenust TARA [3]. Esimeses, juba käivitunud järgus pakub TARA ainult lihtsat, ühekordse sisselogimiseta autentimisteenust. Ühekordse sisselogimisega autentimisteenust on RIA-s kavandatud, vaheaegadega, alates 2015. a. Tulemusena on valminud kavand [6], mis käsitleb küll tehnilist külge, kuid ei sisalda ärivajaduse ega turvalisuse analüüsi.

## 5 Uuringu eesmärk

Uuringu eesmärk on analüüsida Open ID Connect raamistikul baseeruva autentimisteenuse tehnilisi, protseduurilisi ja ärilisi riske erinevatel teenuse ulatuse tasemetel ning leida ja kirjeldada riskide maandamiseks vajalikud meetmed.

## Viited

[1] Lätis on keskne autentimisteenus tihedas kasutuses.<br>
[2] Soomes on loodud keskne Suomi.fi autentimisteenus, mille võtavad kasutusele kõik keskvalitsuse asutused. Vt [https://tunnistaminen.suomi.fi/sivut/info/tietoapalvelusta/](https://tunnistaminen.suomi.fi/sivut/info/tietoapalvelusta/).<br>
[3] Riigi Infosüsteemi Amet (2017) TARA autentimisteenus, [https://e-gov.github.io/TARA-Doku/](https://e-gov.github.io/TARA-Doku/).<br>
[4] TARA autentimisteenus. Teekaart [https://e-gov.github.io/TARA-Doku/Teekaart](https://e-gov.github.io/TARA-Doku/Teekaart).<br>
[5] TARA autentimisteenus. Tehniline kirjeldus [https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus).<br>
[6] Riigi Infosüsteemi Amet. RIA SSO autentimisteenuse kavand. 18 lk. [https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon](https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon).<br>
[7] OpenID Foundation (2017) OpenID Connect Session Management 1.0. Draft 28. [http://openid.net/specs/openid-connect-session-1_0.html](http://openid.net/specs/openid-connect-session-1_0.html).<br>
[8] OpenID Foundation (2017) OpenID Connect Front-Channel Logout 1.0. Draft 02. 
[http://openid.net/specs/openid-connect-frontchannel-1_0.html](http://openid.net/specs/openid-connect-frontchannel-1_0.html).<br>
[9] OpenID Foundation (2017) OpenID Connect Back-Channel Logout 1.0. Draft 04. [http://openid.net/specs/openid-connect-backchannel-1_0.html](http://openid.net/specs/openid-connect-backchannel-1_0.html).<br>
